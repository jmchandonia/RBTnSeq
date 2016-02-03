package rbtnseq;

import java.io.*;
import java.nio.file.*;
import java.nio.charset.*;
import java.util.*;
import java.net.URL;
import java.text.SimpleDateFormat;

import us.kbase.auth.AuthService;
import us.kbase.auth.AuthToken;
import us.kbase.common.service.*;
import us.kbase.workspace.*;
import us.kbase.kbasegenomes.*;
import us.kbase.kbaseassembly.*;
import us.kbase.kbaserbtnseq.*;
import us.kbase.common.utils.FastaWriter;

import com.fasterxml.jackson.databind.*;

import org.strbio.IO;
import org.strbio.io.*;
import org.strbio.util.*;

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
       helper function to get the reference back when saving an object
    */
    public static String getRefFromObjectInfo(Tuple11<Long, String, String, String, Long, String, Long, String, String, Long, Map<String,String>> info) {
        return info.getE7() + "/" + info.getE1() + "/" + info.getE5();
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
       dumps a Genome object out in FEBA directory format.

       returns tuple2:
       name of genome directory,
       and map of fake contig ids to real contig refs
    */
    public static Tuple2<File,Map<String,String>> dumpGenomeTab(String wsURL,
                                                                AuthToken token,
                                                                File tempDir,
                                                                String ws,
                                                                String genomeRef) throws Exception {
        WorkspaceClient wc = createWsClient(wsURL,token);
        ObjectMapper mapper = new ObjectMapper();

        File genomeDir = File.createTempFile("genome", "", tempDir);
        genomeDir.delete();
        genomeDir.mkdir();

        HashMap<String,String> reverseContigMap = new HashMap<String,String>();

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
                reverseContigMap.put(""+(i+1000), contigID);
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
            reverseContigMap = null;
        }

        Tuple2 rv = new Tuple2<File,Map<String,String>>()
            .withE1(genomeDir)
            .withE2(reverseContigMap);
        
        return rv;
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
       make a tnseq_model object (Tuple2<String,String>) from one
       of the standard feba model files
    */
    public static Tuple2<String,String> parseModel(String primerModelName) throws Exception {
        List<String> lines = Files.readAllLines(Paths.get("/kb/module/feba/primers/"+primerModelName), Charset.defaultCharset());
        if (lines.size() < 2)
            throw new Exception("Barcode model file "+primerModelName+" seems corrupted; should have two lines");
        return new Tuple2<String,String>()
            .withE1(lines.get(0))
            .withE1(lines.get(1));
    }

    /**
       make a MappedReads object from the output (.tab file) from
       mapReads
    */
    public static MappedReads parseMappedReads(int nContigs,
                                               String genomeRef,
                                               String readsRef,
                                               File mappedReadsFile,
                                               String primerModelName) throws Exception {
        List<List<Tuple4<String, Long, Long, Long>>> uniqueReadsByContig = new ArrayList<List<Tuple4<String, Long, Long, Long>>>();
        List<List<Tuple4<String, Long, Long, Long>>> nonuniqueReadsByContig = new ArrayList<List<Tuple4<String, Long, Long, Long>>>();
        List<Tuple4<String, Long, Long, Long>> readSet = null;
        
        // add mapped reads arrays for each contig,
        // plus one extra for "pastEnd" reads
        for (int i=0; i<=nContigs; i++) {
            if (i!= nContigs) {
                // past end reads are all non-unique, so don't need array
                readSet = new ArrayList<Tuple4<String, Long, Long, Long>>();        
                uniqueReadsByContig.add(readSet);
            }
            readSet = new ArrayList<Tuple4<String, Long, Long, Long>>();
            nonuniqueReadsByContig.add(readSet);
        }
        
        MappedReads rv = new MappedReads()
            .withGenome(genomeRef)
            .withReads(readsRef)
            .withModel(parseModel(primerModelName))
            .withUniqueReadsByContig(uniqueReadsByContig)
            .withNonuniqueReadsByContig(nonuniqueReadsByContig);

        BufferedReader infile = IO.openReader(mappedReadsFile.getPath());
        if (infile==null)
            throw new Exception("failed to open mapped reads output "+mappedReadsFile.getPath());

        int i=0;
        while (infile.ready()) {
            String buffer = infile.readLine();
            if (buffer==null) {
                infile.close();
                break;
            }

            // System.err.println("line "+i++);

            // use StringTokenizer instead of split for performance
            StringTokenizer st = new StringTokenizer(buffer,"\t");
            
            // I'm doing new String(fields[]) below in order
            // to work around the java bug described here:
            // http://stackoverflow.com/questions/6056389/java-string-split-memory-leak

            Tuple4<String, Long, Long, Long> mappedRead = new Tuple4<String, Long, Long, Long>();
            try {
                st.nextToken(); // ignore read name
                mappedRead.setE1(new String(st.nextToken())); // barcode

                // mapped reads past end of transposon don't have all fields
                String contig = new String(st.nextToken());
                int contigIndex = nContigs;
                boolean isUnique = false;
                if (!contig.equals("pastEnd")) {
                    contigIndex = StringUtil.atoi(contig)-1000;
                    mappedRead.setE2(new Long(StringUtil.atol(st.nextToken()))); // insert_pos
                    boolean isPlusStrand = (st.nextToken().equals("+"));
                    isUnique = (st.nextToken().equals("1"));
                    long hitStart = StringUtil.atol(st.nextToken());
                    long hitEnd = StringUtil.atol(st.nextToken());
                    if (isPlusStrand) {
                        mappedRead.setE3(new Long(hitStart));
                        mappedRead.setE4(new Long(hitEnd));
                    }
                    else {
                        mappedRead.setE3(new Long(hitEnd));
                        mappedRead.setE4(new Long(hitStart));
                    }
                    // ignore bit score
                    // ignore pct identity
                }

                if (isUnique)
                    readSet = uniqueReadsByContig.get(contigIndex);
                else
                    readSet = nonuniqueReadsByContig.get(contigIndex);

                readSet.add(mappedRead);
            }
            catch (NoSuchElementException e) {
                // don't add this read
                e.printStackTrace(System.err);
            }
        }
        infile.close();
            
        return rv;
    }

    /**
       save mapped reads to workspace, with provenance.
       returns ref
    */
    public static String saveMappedReads(String wsURL,
                                         AuthToken token,
                                         String ws,
                                         String id,
                                         MappedReads mappedReads,
                                         String methodName,
                                         List<UObject> methodParams) throws Exception {

        WorkspaceClient wc = createWsClient(wsURL,token);

        // to get service version:
        RBTnSeqServer server = new RBTnSeqServer();
        
        ObjectSaveData data = new ObjectSaveData()
            .withData(new UObject(mappedReads))
            .withType("KBaseRBTnSeq.MappedReads")
            .withProvenance(Arrays.asList(new ProvenanceAction()
                                          .withDescription("TnSeq mapped reads")
                                          .withService("RBTnSeq")
                                          .withServiceVer(server.version(null))
                                          .withMethod(methodName)
                                          .withMethodParams(methodParams)));
        try {
            long objid = Long.parseLong(id);
            data.withObjid(objid);
        } catch (NumberFormatException ex) {
            data.withName(id);
        }
        return getRefFromObjectInfo(wc.saveObjects(new SaveObjectsParams().withWorkspace(ws).withObjects(Arrays.asList(data))).get(0));
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
                                          long minN) throws Exception {
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
                               ""+minN,
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

        String rv = "TnSeq pipeline output:\n";

        File readsFile = dumpSEReadsFASTQ(wsURL,
                                          token,
                                          tempDir,
                                          inputParams.getWs(),
                                          inputParams.getInputReadLibrary());

        Tuple2<File,Map<String,String>> genomeData = dumpGenomeTab(wsURL,
                                                                   token,
                                                                   tempDir,
                                                                   inputParams.getWs(),
                                                                   inputParams.getInputGenome());
        File genomeDir = genomeData.getE1();
        Map<String,String> contigMap = genomeData.getE2();

        File[] mapOutput = mapReads(tempDir,
                                    readsFile,
                                    genomeDir,
                                    inputParams.getInputBarcodeModel());

        MappedReads mappedReads = parseMappedReads(contigMap.size(),
                                                   inputParams.getInputGenome(),
                                                   inputParams.getInputReadLibrary(),
                                                   mapOutput[0],
                                                   inputParams.getInputBarcodeModel());


        String mappedReadsID = saveMappedReads(wsURL,
                                               token,
                                               inputParams.getWs(),
                                               inputParams.getOutputMappedReads(),
                                               mappedReads,
                                               "TnSeq.run",
                                               Arrays.asList(new UObject(inputParams)));

        rv += "---\nStep 1: Read mapping output:\n";
        List<String> lines = Files.readAllLines(Paths.get(mapOutput[1].getPath()), Charset.defaultCharset());
        for (String line : lines) {
            if (line.startsWith("Parsed model"))
                line = "Parsed model "+inputParams.getInputBarcodeModel();
            rv += line+"\n";
        }

        File[] poolOutput = designRandomPool(tempDir,
                                             mapOutput[0],
                                             new File(genomeDir+"/genes.tab"),
                                             inputParams.getInputMinN().longValue());

        rv += "\n---\n\nStep 2: TnSeq pool construction output:\n";
        lines = Files.readAllLines(Paths.get(poolOutput[1].getPath()), Charset.defaultCharset());
        for (String line : lines) {
            rv += line+"\n";
        }

        return rv;
    }
}
