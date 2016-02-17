package rbtnseq;

import java.io.File;
import us.kbase.auth.AuthToken;
import us.kbase.common.service.JsonServerMethod;
import us.kbase.common.service.JsonServerServlet;
import us.kbase.common.service.JsonServerSyslog;
import us.kbase.common.service.RpcContext;

//BEGIN_HEADER
import java.net.URL;
import java.util.Arrays;

import us.kbase.kbasegenomes.ContigSet;
import us.kbase.workspace.ObjectIdentity;
import us.kbase.workspace.WorkspaceClient;
//END_HEADER

/**
 * <p>Original spec-file module name: RBTnSeq</p>
 * <pre>
 * A KBase module: RBTnSeq
 * This module wraps Morgan Price's RBTnSeq pipeline,
 * available from https://bitbucket.org/berkeleylab/feba
 * </pre>
 */
public class RBTnSeqServer extends JsonServerServlet {
    private static final long serialVersionUID = 1L;

    //BEGIN_CLASS_HEADER
    private final String wsUrl;
    private final String shockUrl;
    //END_CLASS_HEADER

    public RBTnSeqServer() throws Exception {
        super("RBTnSeq");
        //BEGIN_CONSTRUCTOR
        wsUrl = config.get("workspace-url");
        shockUrl = config.get("shock-url");
        //END_CONSTRUCTOR
    }

    /**
     * <p>Original spec-file function name: runTnSeq</p>
     * <pre>
     * runs TnSeq part of pipeline
     * </pre>
     * @param   input   instance of type {@link rbtnseq.TnSeqInput TnSeqInput}
     * @return   parameter "output" of type {@link rbtnseq.TnSeqOutput TnSeqOutput}
     */
    @JsonServerMethod(rpc = "RBTnSeq.runTnSeq", async=true)
    public TnSeqOutput runTnSeq(TnSeqInput input, AuthToken authPart, RpcContext jsonRpcContext) throws Exception {
        TnSeqOutput returnVal = null;
        //BEGIN runTnSeq
        returnVal = TnSeq.run(wsUrl,shockUrl,authPart,input);
        //END runTnSeq
        return returnVal;
    }

    /**
     * <p>Original spec-file function name: makeTnSeqPool</p>
     * <pre>
     * runs TnSeq part of pipeline
     * </pre>
     * @param   input   instance of type {@link rbtnseq.TnSeqPoolInput TnSeqPoolInput}
     * @return   parameter "output" of type {@link rbtnseq.TnSeqOutput TnSeqOutput}
     */
    @JsonServerMethod(rpc = "RBTnSeq.makeTnSeqPool", async=true)
    public TnSeqOutput makeTnSeqPool(TnSeqPoolInput input, AuthToken authPart, RpcContext jsonRpcContext) throws Exception {
        TnSeqOutput returnVal = null;
        //BEGIN makeTnSeqPool
        returnVal = TnSeq.makePool(wsUrl,shockUrl,authPart,input);
        //END makeTnSeqPool
        return returnVal;
    }

    /**
     * <p>Original spec-file function name: getEssentialGenes</p>
     * <pre>
     * gets list of essential (un-hit) genes from a Pool
     * </pre>
     * @param   input   instance of type {@link rbtnseq.EssentialGenesInput EssentialGenesInput}
     * @return   parameter "output" of type {@link rbtnseq.EssentialGenesOutput EssentialGenesOutput}
     */
    @JsonServerMethod(rpc = "RBTnSeq.getEssentialGenes", async=true)
    public EssentialGenesOutput getEssentialGenes(EssentialGenesInput input, AuthToken authPart, RpcContext jsonRpcContext) throws Exception {
        EssentialGenesOutput returnVal = null;
        //BEGIN getEssentialGenes
        returnVal = TnSeq.getEssentialGenes(wsUrl,shockUrl,authPart,input);
        //END getEssentialGenes
        return returnVal;
    }

    /**
     * <p>Original spec-file function name: version</p>
     * <pre>
     * returns version number of service
     * </pre>
     * @return   parameter "version" of String
     */
    @JsonServerMethod(rpc = "RBTnSeq.version", async=true)
    public String version(RpcContext jsonRpcContext) throws Exception {
        String returnVal = null;
        //BEGIN version
        returnVal = "RBTnSeq-0."+serialVersionUID;
        //END version
        return returnVal;
    }

    public static void main(String[] args) throws Exception {
        if (args.length == 1) {
            new RBTnSeqServer().startupServer(Integer.parseInt(args[0]));
        } else if (args.length == 3) {
            JsonServerSyslog.setStaticUseSyslog(false);
            JsonServerSyslog.setStaticMlogFile(args[1] + ".log");
            new RBTnSeqServer().processRpcCall(new File(args[0]), new File(args[1]), args[2]);
        } else {
            System.out.println("Usage: <program> <server_port>");
            System.out.println("   or: <program> <context_json_file> <output_json_file> <token>");
            return;
        }
    }
}
