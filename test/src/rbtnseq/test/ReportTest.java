package rbtnseq.test;

import java.io.*;
import java.net.*;
import java.util.*;

import junit.framework.Assert;

import org.ini4j.Ini;
import org.junit.*;

import rbtnseq.*;
import us.kbase.kbaserbtnseq.*;
import us.kbase.kbasereport.*;
import us.kbase.kbasecollections.*;
import us.kbase.auth.AuthToken;
import us.kbase.common.service.*;
import us.kbase.workspace.*;

import us.kbase.common.service.RpcContext;

public class ReportTest {
    private static AuthToken token = null;
    private static WorkspaceClient wsClient = null;
    private static Map<String, String> config = null;
    private static String wsName = null;
    
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
    }

    private static String getWsName() throws Exception {
        if (wsName == null) {
            long suffix = System.currentTimeMillis();
            wsName = "test_RBTnSeq_" + suffix;
            wsClient.createWorkspace(new CreateWorkspaceParams().withWorkspace(wsName));
        }
        return wsName;
    }

    /**
       helper function to get the reference back when saving an object
    */
    public static String getRefFromObjectInfo(Tuple11<Long, String, String, String, Long, String, Long, String, String, Long, Map<String,String>> info) {
        return info.getE7() + "/" + info.getE1() + "/" + info.getE5();
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
       test making a Report object with no objects created
    */
    @Test
    public void testNoObjects() throws Exception {
        List<String> warnings = new ArrayList<String>();
        warnings.add("Developers need to be able to create reports with warnings/errors, even if no objects were created");

        List<WorkspaceObject> objects = new ArrayList<WorkspaceObject>();
        // don't put any objects in the list.
        
        String reportName = "test_report_"+UUID.randomUUID().toString();
        Report report = new Report()
            .withTextMessage("This should work")
            .withWarnings(warnings)
            .withObjectsCreated(objects);

        List<ProvenanceAction> provenance = new ArrayList<ProvenanceAction>
            (Arrays.asList(new ProvenanceAction()
                           .withDescription("test method")
                           .withService("RBTnSeq")
                           .withServiceVer("0.0.1")
                           .withMethod("testNoObjects")));

        ObjectSaveData reportData = new ObjectSaveData()
            .withType("KBaseReport.Report")
            .withData(new UObject(report))
            .withName(reportName)
            .withHidden(1L)
            .withProvenance(provenance);

        String reportRef = getRefFromObjectInfo(wsClient.saveObjects(new SaveObjectsParams().withWorkspace(getWsName()).withObjects(Arrays.asList(reportData))).get(0));

        Assert.assertNotNull(reportRef);
        System.out.println("test report "+reportRef);
    }
}
