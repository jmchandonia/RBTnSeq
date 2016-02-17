package rbtnseq.test;

import java.io.File;
import java.net.URL;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;

import junit.framework.Assert;

import org.ini4j.Ini;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import rbtnseq.*;
import us.kbase.kbaserbtnseq.*;
import us.kbase.auth.AuthToken;
import us.kbase.common.service.JsonServerSyslog;
import us.kbase.common.service.UObject;
import us.kbase.workspace.CreateWorkspaceParams;
import us.kbase.workspace.ObjectSaveData;
import us.kbase.workspace.SaveObjectsParams;
import us.kbase.workspace.WorkspaceClient;
import us.kbase.workspace.WorkspaceIdentity;

import us.kbase.common.service.RpcContext;

public class RBTnSeqServerTest {
    private static AuthToken token = null;
    private static Map<String, String> config = null;
    private static WorkspaceClient wsClient = null;
    private static String wsName = null;
    private static RBTnSeqServer impl = null;
    
    @BeforeClass
    public static void init() throws Exception {
        token = new AuthToken(System.getenv("KB_AUTH_TOKEN"));
        String configFilePath = System.getenv("KB_DEPLOYMENT_CONFIG");
        File deploy = new File(configFilePath);
        Ini ini = new Ini(deploy);
        config = ini.get("RBTnSeq");
        wsClient = new WorkspaceClient(new URL(config.get("workspace-url")), token);
        wsClient.setAuthAllowedForHttp(true);
        // These lines are necessary because we don't want to start linux syslog bridge service
        JsonServerSyslog.setStaticUseSyslog(false);
        JsonServerSyslog.setStaticMlogFile(new File(config.get("scratch"), "test.log").getAbsolutePath());
        impl = new RBTnSeqServer();
    }
    
    private static String getWsName() throws Exception {
        if (wsName == null) {
            long suffix = System.currentTimeMillis();
            wsName = "test_RBTnSeq_" + suffix;
            wsClient.createWorkspace(new CreateWorkspaceParams().withWorkspace(wsName));
        }
        return wsName;
    }
    
    @AfterClass
    public static void cleanup() {
        if (wsName != null) {
            try {
                wsClient.deleteWorkspace(new WorkspaceIdentity().withWorkspace(wsName));
                System.out.println("Test workspace was deleted");
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    /**
       check that we can read version
    */
    @Test
    public void getVersion() throws Exception {
        String version = impl.version((RpcContext)null);
        System.out.println("service version is "+version);
        Assert.assertNotNull(version);
    }

    /**
       test tnseq with some D. vulgaris reads data
    @Test
    */
    public void testTnSeq() throws Exception {
        TnSeqInput input = new TnSeqInput()
            .withWs("jmc:1449699355207")
            .withInputReadLibrary("GBVT06H_reads")
            .withInputGenome("kb|g.3562")
            .withInputBarcodeModel("model_pKMW7")
            .withInputMinN(10L)
            .withOutputMappedReads("test_mapped_reads")
            .withOutputPool("test_pool");
        TnSeqOutput rv = impl.runTnSeq(input, token, (RpcContext)null);
        Assert.assertNotNull(rv);
        System.out.println("tnseq report "+rv.getReportRef());
    }

    

    /**
       test loading of MappedReads object
    @Test
    public void testParseMappedReads() throws Exception {
        MappedReads reads = TnSeq.parseMappedReads(2,
                                                   null,
                                                   null,
                                                   new File("/kb/module/work/map6575757982934881151.tab"),
                                                   "model_pKMW7");
        Assert.assertNotNull(reads);
        System.out.println("read "+reads.getUniqueReadsByContig().get(0).size()+" mapped unique reads in contig 0 from file");
    }
    */

    /**
       test essential genes
    */
    @Test
    public void testEssentialGenes() throws Exception {
        EssentialGenesInput input = new EssentialGenesInput()
            .withWs("jmc:1449699355207")
            .withInputPool("test_pool")
            .withOutputFeatureSet("test_feature_set");
        EssentialGenesOutput rv = impl.getEssentialGenes(input, token, (RpcContext)null);
        Assert.assertNotNull(rv);
        System.out.println("essential genes report "+rv.getReportRef());
    }
    
    /*    
    @Test
    public void testCountContigs() throws Exception {
        String objName = "contigset.1";
        Map<String, Object> contig = new LinkedHashMap<String, Object>();
        contig.put("id", "1");
        contig.put("length", 10);
        contig.put("md5", "md5");
        contig.put("sequence", "agcttttcat");
        Map<String, Object> obj = new LinkedHashMap<String, Object>();
        obj.put("contigs", Arrays.asList(contig));
        obj.put("id", "id");
        obj.put("md5", "md5");
        obj.put("name", "name");
        obj.put("source", "source");
        obj.put("source_id", "source_id");
        obj.put("type", "type");
        wsClient.saveObjects(new SaveObjectsParams().withWorkspace(getWsName()).withObjects(Arrays.asList(
                new ObjectSaveData().withType("KBaseGenomes.ContigSet").withName(objName).withData(new UObject(obj)))));
        CountContigsResults ret = impl.countContigs(getWsName(), objName, token);
        Assert.assertEquals(1L, (long)ret.getContigCount());
    }
    */
}
