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
    //END_CLASS_HEADER

    public RBTnSeqServer() throws Exception {
        super("RBTnSeq");
        //BEGIN_CONSTRUCTOR
        wsUrl = config.get("workspace-url");
        //END_CONSTRUCTOR
    }

    /**
     * <p>Original spec-file function name: runTnSeq</p>
     * <pre>
     * runs TnSeq part of pipeline
     * </pre>
     * @param   inputParams   instance of type {@link rbtnseq.TnSeqInput TnSeqInput}
     * @return   parameter "report" of String
     */
    @JsonServerMethod(rpc = "RBTnSeq.runTnSeq", async=true)
    public String runTnSeq(TnSeqInput inputParams, AuthToken authPart, RpcContext... jsonRpcContext) throws Exception {
        String returnVal = null;
        //BEGIN runTnSeq
        //END runTnSeq
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
