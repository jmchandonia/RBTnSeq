package rbtnseq;

import java.io.*;
import java.util.*;
import java.net.URL;

import us.kbase.auth.AuthService;
import us.kbase.auth.AuthToken;
import us.kbase.common.service.*;
import us.kbase.workspace.*;
import us.kbase.kbasegenomes.*;
import us.kbase.kbaseassembly.*;

import com.fasterxml.jackson.databind.*;

/**
   This class runs TnSeq, which maps reads to a genome
*/
public class TnSeq {
    protected static File tempDir = new File("/kb/module/work/");

    /**
       creates a workspace client; if token is null, client can
       only read public workspaces.
    */
    public static WorkspaceClient createWsClient(String wsURL,
                                                 AuthToken token) throws Exception {
        WorkspaceClient rv = null;

        if (token==null)
            rv = new WorkspaceClient(new URL(wsURL));
        else
            rv = new WorkspaceClient(new URL(wsURL),token);
        rv.setAuthAllowedForHttp(true);
        return rv;
    }
    

    /**
       dumps a (single end) reads object out in fastQ format.  
    */
    public static File dumpSEReadsFASTQ(String wsURL,
                                        AuthToken token,
                                        File tempDir,
                                        String ws,
                                        String readsRef) throws Exception {
        WorkspaceClient wc = createWsClient(wsURL,token);
        ObjectMapper mapper = new ObjectMapper();

        File fastQFile = File.createTempFile("reads", ".fastq.gz", tempDir);
        fastQFile.delete();

        try {
            us.kbase.kbaseassembly.SingleEndLibrary kasl = wc.getObjects(Arrays.asList(new ObjectIdentity().withRef(ws+"/"+readsRef))).get(0).getData().asClassInstance(us.kbase.kbaseassembly.SingleEndLibrary.class);
            System.out.println("read single-end library as assembly object");
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return fastQFile;
    }
    
    /**
       runs the whole TnSeq pipeline
    */
    public static String run(String wsURL,
                             AuthToken token,
                             TnSeqInput inputParams) throws Exception {
        File readsFile = dumpSEReadsFASTQ(wsURL,
                                          token,
                                          tempDir,
                                          inputParams.getWs(),
                                          inputParams.getInputReadLibrary());
        return "reads file made: "+readsFile.getAbsolutePath();
    }
}
