package rbtnseq;

import java.io.*;
import java.util.*;
import java.net.URL;
import java.text.SimpleDateFormat;

import us.kbase.auth.AuthService;
import us.kbase.auth.AuthToken;
import us.kbase.common.service.*;
import us.kbase.workspace.*;
import us.kbase.kbasegenomes.*;
import us.kbase.kbaseassembly.*;
import us.kbase.common.utils.FastaWriter;

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
       dumps a (single end) reads object out in fastQ.gz format.  
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
                new ProcessBuilder("/bin/bash",
                                   "-c",
                                   "/usr/bin/curl -k -X GET "+url+" -H \"Authorization: OAuth "+token.toString()+"\" | /bin/gzip");
            pb.redirectOutput(Redirect.to(fastQFile));
            pb.start().waitFor();
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
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

        File genomeDir = File.createTempFile("genome", "", tempDir);
        genomeDir.delete();
        genomeDir.mkdir();

        try {
            // first get genome object
            List<ObjectIdentity> objects = new ArrayList<ObjectIdentity>();
            objects.add(new ObjectIdentity().withRef(ws+"/"+genomeRef));
            Genome genome = wc.getObjects(objects).get(0).getData().asClassInstance(Genome.class);
            if (genome==null)
                throw new Exception("Null return error reading genome");

            // get associated contigset
            String contigsetRef = genome.getContigsetRef();
            if (contigsetRef == null)
                throw new Exception("ContigSet not defined for this genome");
            objects.add(new ObjectIdentity().withRef(contigsetRef));
            ContigSet cs = wc.getReferencedObjects(Arrays.asList(objects)).get(0).getData().asClassInstance(ContigSet.class);
            HashMap<String,String> contigSeqs = new HashMap<String,String>();
            for (Contig c : cs.getContigs())
                contigSeqs.put(c.getId(),c.getSequence());
            cs = null;

            // genome.fna = nucleic acid seq for each contig
            FastaWriter fw = new FastaWriter(new File(genomeDir+"/genome.fna"));
            List<String> contigs = genome.getContigIds();
            if (contigs==null)
                throw new Exception("No contig IDs defined for this genome");
            int nContigs = contigs.size();
            HashMap<String,Integer> contigMap = new HashMap<String,Integer>();
            for (int i=0; i<nContigs; i++) {
                String contigID = contigs.get(i);
                String seq = contigSeqs.get(contigID);
                fw.write(""+(i+1000), seq.toUpperCase());
                contigMap.put(contigID, new Integer(i+1000));
            }
            fw.close();

            // aaseq = protein seq for each feature
            // genes.tab = location and description of features
            fw = new FastaWriter(new File(genomeDir+"/aaseq"));
            PrintWriter tabWriter = new PrintWriter(new File(genomeDir+"/genes.tab"));
            tabWriter.println("locusId\tsysName\ttype\tscaffoldId\tbegin\tend\tstrand\tname\tdesc");
            List<Feature> features = genome.getFeatures();
            if (features==null)
                throw new Exception("No features defined for this genome");
            int nFeatures = features.size();
            int geneCount = 0;
            for (int i=0; i<nFeatures; i++) {
                Feature feat = features.get(i);
                if (feat.getLocation().size() < 1)
                    continue;
                Tuple4<String, Long, String, Long> loc = feat.getLocation().get(0);
                String contigID = loc.getE1();
                String featID = feat.getId();
                if ((contigID==null) || (featID==null))
                    continue;

                // get other info about gene
                String alias = "";
                List<String> aliases = feat.getAliases();
                if ((aliases != null) && (aliases.size() > 0))
                    alias = aliases.get(0);
                Integer contigNum = contigMap.get(contigID);
                int type = 0;
                String featType = feat.getType();
                if (featType != null) {
                    if (featType.equals("CDS"))
                        type = 1;
                    else if (featType.equals("rna"))
                        type = 5;
                }
                if (type==0)
                    throw new Exception("Unknown feature type "+featType);

                Long begin = loc.getE2();
                Long length = loc.getE4();
                String strand = loc.getE3();
                Long end = begin+(length-1);
                if (strand.equals("-")) {
                    begin -= (length-1);
                    end -= (length-1);
                }
                String desc = feat.getFunction();
                if (desc==null)
                    desc = "";

                // strip quotes from desc, since they may be
                // confusing Morgan's code.
                desc = desc.replaceAll("\"|\'", "");

                // only list features with sequence
                String seq = feat.getDnaSequence();
                if ((seq==null) || (seq.length()==0))
                    continue;

                // we have a called gene
                geneCount++;                    
                tabWriter.println((i+1)+"\tfeat"+(i+1)+"\t"+type+"\t"+contigNum+"\t"+begin+"\t"+end+"\t"+strand+"\t"+alias+"\t"+desc);

                // see if feature is a protein.  If so, add to aaseq.
                seq = feat.getProteinTranslation();
                if ((seq == null) || (seq.isEmpty()))
                    continue;
                fw.write(""+i, seq);
            }
            tabWriter.close();
            fw.close();
            if (geneCount==0)
                throw new Exception("No genes called for this genome");

            // make GC file
            File gcFile = new File(genomeDir+"/genes.GC");
            ProcessBuilder pb =
                new ProcessBuilder("/kb/module/feba/bin/RegionGC.pl",
                                   genomeDir+"/genome.fna",
                                   genomeDir+"/genes.tab");
            pb.redirectOutput(Redirect.to(gcFile));
            pb.start().waitFor();
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Error reading genome");
            e.printStackTrace();
            genomeDir = null;
        }
        
        return genomeDir;
    }

    /**
       Map reads.  Returns 2 files: stdout and stderr.
       This step is slow--about 20 minutes.
    */
    public static File[] mapReads(File tempDir,
                                  File readsFile,
                                  File genomeDir,
                                  String primerModelName) throws Exception {
        File[] rv = { File.createTempFile("map", ".tab", tempDir),
                      File.createTempFile("map", ".log", tempDir) };
        rv[0].delete();
        rv[1].delete();
        
        ProcessBuilder pb =
            new ProcessBuilder("/kb/module/feba/bin/MapTnSeq.pl",
                               "-model",
                               "/kb/module/feba/primers/"+primerModelName,
                               "-first",
                               readsFile.getAbsolutePath(),
                               "-genome",
                               genomeDir+"/genome.fna");
        pb.redirectOutput(Redirect.to(rv[0]));
        pb.redirectError(Redirect.to(rv[1]));

        pb.start().waitFor();
        return rv;
    }

    /**
       Design random pool, using uniquely mapped barcodes.
       Returns 2 files: stdout and stderr.  Input is the
       tab file that is the stdout from mapReads, and the tab-delimited
       genome dump

       minN is the minimum number of "good" reads for a barcode supporting
       its mapping.  ("Good" means a unique hit to the genome, where the
       first position in the read matches the genome sequence.)
    */
    public static File[] designRandomPool(File tempDir,
                                          File mappedReadsFile,
                                          File genesFile,
                                          int minN) throws Exception {
        File[] rv = { File.createTempFile("pool", ".pool", tempDir),
                      File.createTempFile("pool", ".log", tempDir) };
        rv[0].delete();
        rv[1].delete();

        ProcessBuilder pb =
            new ProcessBuilder("/kb/module/feba/bin/DesignRandomPool.pl",
                               "-pool",
                               rv[0].getAbsolutePath(),
                               "-genes",
                               genesFile.getAbsolutePath(),
                               "-minN",
                               "10",
                               mappedReadsFile.getAbsolutePath());
        pb.redirectErrorStream(true);
        pb.redirectOutput(Redirect.to(rv[1]));

        pb.start().waitFor();
        return rv;
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

        File genomeDir = dumpGenomeTab(wsURL,
                                       token,
                                       tempDir,
                                       inputParams.getWs(),
                                       inputParams.getInputGenome());

        File[] mapOutput = mapReads(tempDir,readsFile,genomeDir,"model_pKMW7");

        File[] poolOutput = designRandomPool(tempDir,
                                             mapOutput[0],
                                             new File(genomeDir+"/genes.tab"),
                                             10);

        return
            "reads file: "+readsFile.getAbsolutePath()+", "+
            "genome dir: "+genomeDir.getAbsolutePath()+", "+
            "map output: "+mapOutput[0].getAbsolutePath()+", "+
            "map log: "+mapOutput[1].getAbsolutePath()+", "+
            "pool output: "+poolOutput[0].getAbsolutePath()+", "+
            "pool log: "+poolOutput[1].getAbsolutePath();
    }
}
