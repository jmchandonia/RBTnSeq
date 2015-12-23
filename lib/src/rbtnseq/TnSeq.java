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

import static java.lang.ProcessBuilder.Redirect;

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
            Handle h = kasl.getHandle();
            String url = h.getUrl()+"/node/"+h.getId()+"?download";

            // needs authenticated shock download
            ProcessBuilder pb =
                new ProcessBuilder("curl",
                                   "-k",
                                   "-X",
                                   "GET",
                                   url,
                                   "-H",
                                   "\"Authorization: OAuth "+token.toString()+"\"");
            pb.redirectOutput(Redirect.to(fastQFile));
            pb.start().waitFor();
        }
        catch (Exception e) {
            System.out.println("Error reading single-end library as assembly object");
            // should try to read as KBaseFile.SingleEndLibrary here
            e.printStackTrace();
            // fastQFile.delete(); save for debugging
            fastQFile = null;
        }

        return fastQFile;
    }


    /**
       dumps a Genome object out in FEBA directory format; returns
       name of genome directory
    */
    public static File dumpGenomeTab(String wsURL,
                                     AuthToken token,
                                     File tempDir,
                                     String ws,
                                     String genomeRef) throws Exception {
        WorkspaceClient wc = createWsClient(wsURL,token);
        ObjectMapper mapper = new ObjectMapper();

        File genomeDir = File.createTempFile("g", "", tempDir);
        genomeDir.delete();
        genomeDir.mkdir();

        try {
            Genome genome = wc.getObjects(Arrays.asList(new ObjectIdentity().withRef(ws+"/"+genomeRef))).get(0).getData().asClassInstance(Genome.class);
            if (genome==null)
                throw new Exception("Null return error reading genome");

            // make sure we can read contigset also:
            String contigsetRef = genome.getContigsetRef();
            if (contigsetRef == null)
                throw new Exception("ContigSet not defined for this genome");
            ContigSet cs = wc.getObjects(Arrays.asList(new ObjectIdentity().withRef(contigsetRef))).get(0).getData().asClassInstance(ContigSet.class);
            HashMap<String,String> contigSeqs = new HashMap<String,String>();
            for (Contig c : cs.getContigs())
                contigSeqs.put(c.getId(),c.getSequence());
            cs = null;

            // genome.fna = nucleic acid seq for each contig
            List<String> contigs = genome.getContigIds();
            if (contigs==null)
                throw new Exception("No contig IDs defined for this genome");
            int nContigs = contigs.size();
            for (int i=0; i<nContigs; i++) {
                String contigID = contigs.get(i);
                String seq = contigSeqs.get(contigID);
            }
            
        }
        catch (Exception e) {
            System.out.println("Error reading genome");
            e.printStackTrace();
            genomeDir = null;
        }
        
        return genomeDir;
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
