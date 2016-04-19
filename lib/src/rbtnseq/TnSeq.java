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
import us.kbase.shock.client.*;
import us.kbase.kbasegenomes.*;
import us.kbase.kbaseassembly.*;
import us.kbase.kbaserbtnseq.*;
import us.kbase.kbasereport.*;
import us.kbase.kbasecollections.*;
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
    public static File dumpSEReadsFASTQ(WorkspaceClient wc,
                                        File tempDir,
                                        String readsRef) throws Exception {
        ObjectMapper mapper = new ObjectMapper();

        File fastQFile = File.createTempFile("reads", ".fastq.gz", tempDir);
        fastQFile.delete();

        try {
            us.kbase.kbaseassembly.SingleEndLibrary kasl = wc.getObjects(Arrays.asList(new ObjectIdentity().withRef(readsRef))).get(0).getData().asClassInstance(us.kbase.kbaseassembly.SingleEndLibrary.class);
            // System.out.println("read single-end library as assembly object");
            Handle h = kasl.getHandle();
            AuthToken token = wc.getToken();

            fastQFile = fromShock(h, token, fastQFile, true);
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

       returns tuple3:
       1) name of genome directory,
       2) map of fake contig ids to real contig refs,
       3) list of features, tuples of <contig, begin, end, id, alias>

       If tempDir is null, doesn't create any files, but just returns
       the contig/feature maps.
    */
    public static Tuple3<File,
        Map<String,String>,
        ArrayList<Tuple5<Integer,Long,Long,String,String>>> dumpGenomeTab(WorkspaceClient wc,
                                                                          File tempDir,
                                                                          String genomeRef) throws Exception {
        ObjectMapper mapper = new ObjectMapper();

        File genomeDir = null;
        if (tempDir != null) {
            genomeDir = File.createTempFile("genome", "", tempDir);
            genomeDir.delete();
            genomeDir.mkdir();
        }

        HashMap<String,String> reverseContigMap = new HashMap<String,String>();
        ArrayList<Tuple5<Integer,Long,Long,String,String>> featureList = new ArrayList<Tuple5<Integer,Long,Long,String,String>>();

        try {
            // first get genome object
            List<ObjectIdentity> objects = new ArrayList<ObjectIdentity>();
            objects.add(new ObjectIdentity().withRef(genomeRef));
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
            FastaWriter fw = null;
            if (genomeDir != null)
                fw = new FastaWriter(new File(genomeDir+"/genome.fna"));
            
            List<String> contigs = genome.getContigIds();
            if (contigs==null)
                throw new Exception("No contig IDs defined for this genome");
            int nContigs = contigs.size();
            HashMap<String,Integer> contigMap = new HashMap<String,Integer>();
            for (int i=0; i<nContigs; i++) {
                String contigID = contigs.get(i);
                String seq = contigSeqs.get(contigID);
                if (fw != null)
                    fw.write(""+(i+1000), seq.toUpperCase());
                contigMap.put(contigID, new Integer(i+1000));
                reverseContigMap.put(""+(i+1000), contigID);
            }
            if (fw != null)
                fw.close();

            // aaseq = protein seq for each feature
            // genes.tab = location and description of features

            PrintWriter tabWriter = null;
            if (genomeDir != null) {
                fw = new FastaWriter(new File(genomeDir+"/aaseq"));
                tabWriter = new PrintWriter(new File(genomeDir+"/genes.tab"));
                tabWriter.println("locusId\tsysName\ttype\tscaffoldId\tbegin\tend\tstrand\tname\tdesc");
            }
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
                if (contigID==null)
                    continue;

                // get other info about gene
                String alias = "";
                List<String> aliases = feat.getAliases();
                if ((aliases != null) && (aliases.size() > 0))
                    alias = aliases.get(0);
                Integer contigNum = contigMap.get(contigID);
                int type = 0;
                String featType = feat.getType().toUpperCase();
                if (featType != null) {
                    if (featType.equals("CDS"))
                        type = 1;
                    else if (featType.equals("RNA"))
                        type = 5;
                }
                if (type==0) {
                    System.out.println("Warning: unknown feature type "+featType);
                    continue;
                }

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

                /*
                  don't do this; since some features in
                  KBase sample genomes inexplicably have no sequence!

                  // only list features with sequence
                  String seq = feat.getDnaSequence();
                  if ((seq==null) || (seq.length()==0))
                    continue;
                */

                // we have a called gene
                geneCount++;
                if (tabWriter != null)
                    tabWriter.println((i+1)+"\tfeat"+(i+1)+"\t"+type+"\t"+contigNum+"\t"+begin+"\t"+end+"\t"+strand+"\t"+alias+"\t"+desc);

                // save it in feature list
                featureList.add(new Tuple5<Integer,Long,Long,String,String>()
                                .withE1(contigNum)
                                .withE2(begin)
                                .withE3(end)
                                .withE4(feat.getId())
                                .withE5(alias));

                // see if feature is a protein.  If so, add to aaseq.
                String seq = feat.getProteinTranslation();
                if ((seq == null) || (seq.isEmpty()))
                    continue;
                if (fw != null)
                    fw.write(""+i, seq);
            }
            if (tabWriter != null)
                tabWriter.close();
            if (fw != null)
                fw.close();
            if (geneCount==0)
                throw new Exception("No genes called for this genome");

            // make GC file
            if (genomeDir != null) {
                File gcFile = new File(genomeDir+"/genes.GC");
                ProcessBuilder pb =
                    new ProcessBuilder("/kb/module/feba/bin/RegionGC.pl",
                                       genomeDir+"/genome.fna",
                                       genomeDir+"/genes.tab");
                pb.redirectOutput(Redirect.to(gcFile));
                pb.start().waitFor();
            }
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Error reading genome");
            e.printStackTrace();
            genomeDir = null;
            reverseContigMap = null;
        }

        Tuple3<File,Map<String,String>,ArrayList<Tuple5<Integer,Long,Long,String,String>>> rv =
            new Tuple3<File,Map<String,String>,ArrayList<Tuple5<Integer,Long,Long,String,String>>>()
            .withE1(genomeDir)
            .withE2(reverseContigMap)
            .withE3(featureList);
        
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
            throw new Exception("Format error for barcode model file "+primerModelName+"; should have exactly two lines");
        return new Tuple2<String,String>()
            .withE1(lines.get(0))
            .withE2(lines.get(1));
    }

    /**
       make a MappedReads object from the output (.tab file) from
       mapReads
    */
    public static MappedReads parseMappedReads(int nContigs,
                                               String genomeRef,
                                               String readsRef,
                                               File mappedReadsFile,
                                               Handle readsFileHandle,
                                               String primerModelName) throws Exception {
        List<List<Long>> uniqueInsertPosByContig = new ArrayList<List<Long>>();
        List<List<Long>> nonuniqueInsertPosByContig = new ArrayList<List<Long>>();
        List<Long> insertPosSet = null;

        // add mapped reads arrays for each contig,
        for (int i=0; i<nContigs; i++) {
            insertPosSet = new ArrayList<Long>();        
            uniqueInsertPosByContig.add(insertPosSet);
            insertPosSet = new ArrayList<Long>();
            nonuniqueInsertPosByContig.add(insertPosSet);
        }

        MappedReads rv = new MappedReads()
            .withGenome(genomeRef)
            .withReads(readsRef)
            .withModel(parseModel(primerModelName))
            .withMappedReadsFile(readsFileHandle)
            .withUniqueInsertPosByContig(uniqueInsertPosByContig)
            .withNonuniqueInsertPosByContig(nonuniqueInsertPosByContig);

        BufferedReader infile = IO.openReader(mappedReadsFile.getPath());
        if (infile==null)
            throw new Exception("failed to open mapped reads output "+mappedReadsFile.getPath());

        int i=0;
        while (infile.ready()) {
            String buffer = infile.readLine();
            if (buffer==null)
                break;

            // System.err.println("line "+i++);

            // use StringTokenizer instead of split for performance
            StringTokenizer st = new StringTokenizer(buffer,"\t");
            
            // I'm doing new String(fields[]) below in order
            // to work around the java bug described here:
            // http://stackoverflow.com/questions/6056389/java-string-split-memory-leak

            long insertPos = -1;
            try {
                st.nextToken(); // ignore read name
                st.nextToken(); // ignore barcode

                // mapped reads past end of transposon don't have all fields
                String contig = new String(st.nextToken());
                int contigIndex = nContigs;
                boolean isUnique = false;
                if (!contig.equals("pastEnd")) {
                    contigIndex = StringUtil.atoi(contig)-1000;
                    insertPos = StringUtil.atol(st.nextToken());
                    st.nextToken(); // ignore strand
                    isUnique = (st.nextToken().equals("1"));
                    // ignore hitStart
                    // ignore hitEnd
                    // ignore bit score
                    // ignore pct identity
                }

                if (insertPos > -1) {
                    if (isUnique)
                        insertPosSet = uniqueInsertPosByContig.get(contigIndex);
                    else
                        insertPosSet = nonuniqueInsertPosByContig.get(contigIndex);
                }
                insertPosSet.add(new Long(insertPos));
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
       dumps a mapped reads object out to a tab file; returns a
       tuple with the file and the genome reference
    */
    public static Tuple2<File,String> dumpMappedReads(WorkspaceClient wc,
                                                      File tempDir,
                                                      String mappedReadsRef) throws Exception {
        ObjectMapper mapper = new ObjectMapper();

        File mappedReadsFile = File.createTempFile("reads", ".fastq.gz", tempDir);
        mappedReadsFile.delete();
        String genomeRef = null;

        try {
            us.kbase.kbaserbtnseq.MappedReads mr = wc.getObjects(Arrays.asList(new ObjectIdentity().withRef(mappedReadsRef))).get(0).getData().asClassInstance(us.kbase.kbaserbtnseq.MappedReads.class);
            // System.out.println("read single-end library as assembly object");
            Handle h = mr.getMappedReadsFile();
            genomeRef = mr.getGenome();
            AuthToken token = wc.getToken();

            mappedReadsFile = fromShock(h, token, mappedReadsFile, false);
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Error reading mapped reads object");
            e.printStackTrace();
            mappedReadsFile = null;
        }

        Tuple2<File,String> rv = new Tuple2<File,String>()
            .withE1(mappedReadsFile)
            .withE2(genomeRef);
        
        return rv;
    }

    /**
       save mapped reads to workspace, with provenance.
       returns ref
    */
    public static String saveMappedReads(WorkspaceClient wc,
                                         String ws,
                                         String id,
                                         MappedReads mappedReads,
                                         List<ProvenanceAction> provenance) throws Exception {
        ObjectSaveData data = new ObjectSaveData()
            .withData(new UObject(mappedReads))
            .withType("KBaseRBTnSeq.MappedReads")
            .withProvenance(provenance);
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
       find a feature that spans a given position.  Note: very
       inefficient, but shouldn't be rate-limiting.
       Returns 0-indexed index into featureList, or -1 if not found.
    */
    public static int findFeature(ArrayList<Tuple5<Integer,Long,Long,String,String>> featureList,
                                  int contigIndex,
                                  long pos) {
        for (int i=0; i<featureList.size(); i++) {
            Tuple5<Integer,Long,Long,String,String> feat = featureList.get(i);
            if ((feat.getE1().intValue() == contigIndex) &&
                (feat.getE2().longValue() <= pos) &&
                (feat.getE3().longValue() >= pos))
                return i;
        }
        return -1;
    }

    /**
       make a Pool object from the output (.pool file) from
       designRandomPool
    */
    public static Pool parsePool(ArrayList<Tuple5<Integer,Long,Long,String,String>> featureList,
                                 String genomeRef,
                                 String mappedReadsRef,
                                 File poolFile,
                                 Handle poolHandle,
                                 Handle poolHitHandle,
                                 Handle poolUnhitHandle,
                                 Handle poolSurpriseHandle) throws Exception {

        List<Strain> strains = new ArrayList<Strain>();
        List<Long> counts = new ArrayList<Long>();
        
        Pool rv = new Pool()
            .withGenome(genomeRef)
            .withMappedReads(mappedReadsRef)
            .withPoolFile(poolHandle)
            .withPoolHitFile(poolHitHandle)
            .withPoolUnhitFile(poolUnhitHandle)
            .withPoolSurpriseFile(poolSurpriseHandle)
            .withStrains(strains)
            .withCounts(counts);

        BufferedReader infile = IO.openReader(poolFile.getPath());
        if (infile==null)
            throw new Exception("failed to open pool output "+poolFile.getPath());

        int i=0;
        while (infile.ready()) {
            i++;
            
            String buffer = infile.readLine();
            if (buffer==null)
                break;
            if (buffer.startsWith("barcode"))
                continue;

            // System.err.println("line "+i);

            // use StringTokenizer instead of split for performance
            StringTokenizer st = new StringTokenizer(buffer,"\t");
            
            long insertPos = -1;
            try {
                String barcode = st.nextToken();
                String rcBarcode = st.nextToken();
                st.nextToken(); // ignore nTot
                long n1 = StringUtil.atol(st.nextToken());
                String contig = st.nextToken();
                if (contig.equals("pastEnd"))
                    continue; // skip pool members past end of contigs

                long contigIndex = StringUtil.atol(contig)-1000L;
                if (contigIndex < 0)
                    throw new Exception("error parsing pool "+buffer);

                List<Delta> deltas = new ArrayList<Delta>();

                Delta d = new Delta()
                    .withChange("insertion")
                    .withContigIndex(new Long(contigIndex));
                    
                String strand = st.nextToken();
                if (strand.equals("+")) {
                    d.setDescription("barcode");
                    d.setSequence(new String(barcode));
                }
                else {
                    d.setDescription("rcBarcode");
                    d.setSequence(new String(rcBarcode));
                }
                d.setPosition(new Long(StringUtil.atol(st.nextToken())));
                d.setLength(new Long(d.getSequence().length()));

                // ignore pos2

                // save barcode delta
                deltas.add(d);

                Strain s = new Strain()
                    .withName("S"+i)
                    .withDeltas(deltas);
                
                // and add phenotype delta showing gene disrupted, if any
                int featureIndex = findFeature(featureList,
                                               (int)(d.getContigIndex().longValue()),
                                               d.getPosition().longValue());
                if (featureIndex != -1) {
                    d = new Delta()
                        .withDescription("phenotype")
                        .withChange("deletion")
                        .withFeature(featureList.get(featureIndex).getE4());
                    deltas.add(d);
                    String alias = featureList.get(featureIndex).getE5();
                    if ((alias != null) &&
                        (alias.length() > 0))
                        s.setDescription("delta "+alias);
                }

                strains.add(s);
                counts.add(new Long(n1));
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
       save pool to workspace, with provenance.
       returns ref
    */
    public static String savePool(WorkspaceClient wc,
                                  String ws,
                                  String id,
                                  Pool pool,
                                  List<ProvenanceAction> provenance) throws Exception {

        ObjectSaveData data = new ObjectSaveData()
            .withData(new UObject(pool))
            .withType("KBaseRBTnSeq.Pool")
            .withProvenance(provenance);
        try {
            long objid = Long.parseLong(id);
            data.withObjid(objid);
        } catch (NumberFormatException ex) {
            data.withName(id);
        }
        return getRefFromObjectInfo(wc.saveObjects(new SaveObjectsParams().withWorkspace(ws).withObjects(Arrays.asList(data))).get(0));
    }

    /**
       save feature set to workspace, with provenance.
       returns ref
    */
    public static String saveFeatureSet(WorkspaceClient wc,
                                        String ws,
                                        String id,
                                        FeatureSet featureSet,
                                        List<ProvenanceAction> provenance) throws Exception {

        ObjectSaveData data = new ObjectSaveData()
            .withData(new UObject(featureSet))
            .withType("KBaseCollections.FeatureSet")
            .withProvenance(provenance);
        try {
            long objid = Long.parseLong(id);
            data.withObjid(objid);
        } catch (NumberFormatException ex) {
            data.withName(id);
        }
        return getRefFromObjectInfo(wc.saveObjects(new SaveObjectsParams().withWorkspace(ws).withObjects(Arrays.asList(data))).get(0));
    }
    
    /**
       store a file in Shock; returns handle.
       If file doesn't exist or can't be read, returns null.
    */
    public static Handle toShock(String shockURL,
                                 AuthToken token,
                                 File f) throws Exception {
        if (!f.canRead())
            return null;
        
        Handle rv = new Handle()
            .withFileName(f.getName())
            .withUrl(shockURL);
        BasicShockClient shockClient = new BasicShockClient(new URL(shockURL), token);
        InputStream is = new BufferedInputStream(new FileInputStream(f));
        ShockNode sn = shockClient.addNode(is,f.getName(),null);
        is.close();
        String shockNodeID = sn.getId().getId();
        rv.setId(shockNodeID);
        return rv;
    }

    /**
       Load a file from Shock; returns File, or null if file couldn't be read.
       If the file from Shock is 0-length, it is deleted and null is returned.
    */
    public static File fromShock(Handle h,
                                 AuthToken token,
                                 File f,
                                 boolean gzip) throws Exception {
        String url = h.getUrl()+"/node/"+h.getId()+"?download";

        // needs authenticated shock download
        ProcessBuilder pb =
            new ProcessBuilder("/bin/bash",
                               "-c",
                               "/usr/bin/curl -k -X GET "+url+" -H \"Authorization: OAuth "+token.toString()+"\""+(gzip ? "| /bin/gzip" : ""));
        pb.redirectOutput(Redirect.to(f));
        pb.start().waitFor();
        if (f.length()==0)
            f.delete();

        if (!f.canRead())
            return null;
        
        return f;
    }

    /**
       Make a provenance object
    */
    public static List<ProvenanceAction> makeProvenance(String description,
                                                        String methodName,
                                                        List<UObject> methodParams) throws Exception {
        // to get service version:
        RBTnSeqServer server = new RBTnSeqServer();
        return new ArrayList<ProvenanceAction>
            (Arrays.asList(new ProvenanceAction()
                           .withDescription(description)
                           .withService("RBTnSeq")
                           .withServiceVer(server.version(null))
                           .withMethod(methodName)
                           .withMethodParams(methodParams)));
    }

    /**
       Make and save Report object, returning its name and reference
    */
    public static String[] makeReport(WorkspaceClient wc,
                                      String ws,
                                      String reportText,
                                      List<String> warnings,
                                      List<WorkspaceObject> objects,
                                      List<ProvenanceAction> provenance) throws Exception {
        String reportName = "rbtnseq_report_"+UUID.randomUUID().toString();
        Report report = new Report()
            .withTextMessage(reportText)
            .withWarnings(warnings)
            .withObjectsCreated(objects);

        ObjectSaveData reportData = new ObjectSaveData()
            .withType("KBaseReport.Report")
            .withData(new UObject(report))
            .withName(reportName)
            .withHidden(1L)
            .withProvenance(provenance);
        String reportRef = getRefFromObjectInfo(wc.saveObjects(new SaveObjectsParams().withWorkspace(ws).withObjects(Arrays.asList(reportData))).get(0));
        return new String[] { reportName, reportRef };
    }

    /**
       runs the 2nd part of the TnSeq pipeline
    */
    public static TnSeqOutput makePool(String wsURL,
                                       String shockURL,
                                       AuthToken token,
                                       TnSeqPoolInput input) throws Exception {

        // turn local into absolute paths
        String mappedReadsRef = input.getInputMappedReads();
        if (mappedReadsRef.indexOf("/") == -1)
            mappedReadsRef = input.getWs()+"/"+mappedReadsRef;

        // for provenance
        String methodName = "TnSeq.makePool";
        List<UObject> methodParams = Arrays.asList(new UObject(input));

        // dump mapped reads to file
        WorkspaceClient wc = createWsClient(wsURL,token);
        File tempDir = new File("/kb/module/work/");
        Tuple2<File,String> mr = dumpMappedReads(wc,
                                                 tempDir,
                                                 mappedReadsRef);
        File mappedReadsFile = mr.getE1();
        String genomeRef = mr.getE2();

        // dump genome to file
        Tuple3<File,
            Map<String,String>,
            ArrayList<Tuple5<Integer,Long,Long,String,String>>> genomeData =
            dumpGenomeTab(wc,
                          tempDir,
                          genomeRef);
        
        File genomeDir = genomeData.getE1();
        ArrayList<Tuple5<Integer,Long,Long,String,String>> featureList = genomeData.getE3();

        // make random pool
        File[] poolOutput = designRandomPool(tempDir,
                                             mappedReadsFile,
                                             new File(genomeDir+"/genes.tab"),
                                             input.getInputMinN().longValue());
        mappedReadsFile.delete();

        Handle poolHandle = toShock(shockURL, token, poolOutput[0]);

        File f = new File(poolOutput[0]+".hit");
        Handle poolHitHandle = toShock(shockURL, token, f);
        f.delete();
        f = new File(poolOutput[0]+".unhit");
        Handle poolUnhitHandle = toShock(shockURL, token, f);
        f.delete();
        f = new File(poolOutput[0]+".surprise");
        Handle poolSurpriseHandle = toShock(shockURL, token, f);
        f.delete();
        
        Pool pool = parsePool(featureList,
                              genomeRef,
                              mappedReadsRef,
                              poolOutput[0],
                              poolHandle,
                              poolHitHandle,
                              poolUnhitHandle,
                              poolSurpriseHandle);
        poolOutput[0].delete();

        String poolRef = savePool(wc,
                                  input.getWs(),
                                  input.getOutputPool(),
                                  pool,
                                  makeProvenance("TnSeq pool",
                                                 methodName,
                                                 methodParams));

        String reportText = "TnSeq pool construction output:\n";
        List<String> lines = Files.readAllLines(Paths.get(poolOutput[1].getPath()), Charset.defaultCharset());
        for (String line : lines) {
            if (line.indexOf("with no good insertions") > -1)
                line = line.replace(poolOutput[0].getAbsolutePath()+".unhit", "Pool object (use 'Get Essential Genes' method to view)");
            else if (line.indexOf("with surprising insertions") > -1)
                line = line.replace(poolOutput[0].getAbsolutePath()+".surprise", "Pool object");
            else if (line.indexOf("read and strain counts for hit genes") > -1)
                line = line.replace(poolOutput[0].getAbsolutePath()+".hit", "Pool object");
            
            reportText += line+"\n";
        }
        poolOutput[1].delete();

        // generate report with list of objects created
        List<WorkspaceObject> objects = new ArrayList<WorkspaceObject>();
        objects.add(new WorkspaceObject()
                    .withRef(poolRef)
                    .withDescription("TnSeq library pool"));
        String[] report = makeReport(wc,
                                     input.getWs(),
                                     reportText,
                                     null,
                                     objects,
                                     makeProvenance("RBTnSeq Make TnSeq Pool report",
                                                    methodName,
                                                    methodParams));

        TnSeqOutput rv = new TnSeqOutput()
            .withReportName(report[0])
            .withReportRef(report[1]);

        return rv;
    }

    /**
       runs the whole TnSeq pipeline
    */
    public static TnSeqOutput run(String wsURL,
                                  String shockURL,
                                  AuthToken token,
                                  TnSeqInput input) throws Exception {

        WorkspaceClient wc = createWsClient(wsURL,token);
        File tempDir = new File("/kb/module/work/");
        
        // turn local into absolute paths
        String genomeRef = input.getInputGenome();
        if (genomeRef.indexOf("/") == -1)
            genomeRef = input.getWs()+"/"+genomeRef;
        String readsRef = input.getInputReadLibrary();
        if (readsRef.indexOf("/") == -1)
            readsRef = input.getWs()+"/"+readsRef;

        // for provenance
        String methodName = "TnSeq.run";
        List<UObject> methodParams = Arrays.asList(new UObject(input));
        
        // expected outputs:
        String reportText = "TnSeq pipeline output:\n";
        String mappedReadsRef = null;
        String poolRef = null;
        List<String> warnings = null;
        List<WorkspaceObject> objects = new ArrayList<WorkspaceObject>();

        try {
            // dump reads
            File readsFile = dumpSEReadsFASTQ(wc,
                                              tempDir,
                                              readsRef);

            // dump genome
            Tuple3<File,
                Map<String,String>,
                ArrayList<Tuple5<Integer,Long,Long,String,String>>> genomeData =
                dumpGenomeTab(wc,
                              tempDir,
                              genomeRef);
        
            File genomeDir = genomeData.getE1();
            Map<String,String> contigMap = genomeData.getE2();
            ArrayList<Tuple5<Integer,Long,Long,String,String>> featureList = genomeData.getE3();

            File[] mapOutput = mapReads(tempDir,
                                        readsFile,
                                        genomeDir,
                                        input.getInputBarcodeModel());
            if (mapOutput[0].length()==0) {
                mapOutput[0].delete();
                mapOutput[1].delete();
                throw new Exception("Error mapping reads; are you sure you picked the correct barcode model?");
            }

            readsFile.delete();

            Handle mappedReadsHandle = toShock(shockURL, token, mapOutput[0]);

            MappedReads mappedReads = parseMappedReads(contigMap.size(),
                                                       genomeRef,
                                                       readsRef,
                                                       mapOutput[0],
                                                       mappedReadsHandle,
                                                       input.getInputBarcodeModel());
            /*
              ObjectMapper mapper = new ObjectMapper();
              File f = new File(tempDir+"/mappedReadsObject.json");
              mapper.writeValue(f,mappedReads);
            */

            mappedReadsRef = saveMappedReads(wc,
                                             input.getWs(),
                                             input.getOutputMappedReads(),
                                             mappedReads,
                                             makeProvenance("TnSeq mapped reads",
                                                            methodName,
                                                            methodParams));
            objects.add(new WorkspaceObject()
                        .withRef(mappedReadsRef)
                        .withDescription("TnSeq mapped reads"));
           
            reportText += "---\nStep 1: Read mapping output:\n";
            List<String> lines = Files.readAllLines(Paths.get(mapOutput[1].getPath()), Charset.defaultCharset());
            for (String line : lines) {
                if (line.startsWith("Parsed model"))
                    line = "Parsed model "+input.getInputBarcodeModel();
                reportText += line+"\n";
            }
            mapOutput[1].delete();

            // step 2: make random pool
            File[] poolOutput = designRandomPool(tempDir,
                                                 mapOutput[0],
                                                 new File(genomeDir+"/genes.tab"),
                                                 input.getInputMinN().longValue());
            mapOutput[0].delete();

            Handle poolHandle = toShock(shockURL, token, poolOutput[0]);

            File f = new File(poolOutput[0]+".hit");
            Handle poolHitHandle = toShock(shockURL, token, f);
            f.delete();
            f = new File(poolOutput[0]+".unhit");
            Handle poolUnhitHandle = toShock(shockURL, token, f);
            f.delete();
            f = new File(poolOutput[0]+".surprise");
            Handle poolSurpriseHandle = toShock(shockURL, token, f);
            f.delete();
        
            Pool pool = parsePool(featureList,
                                  genomeRef,
                                  mappedReadsRef,
                                  poolOutput[0],
                                  poolHandle,
                                  poolHitHandle,
                                  poolUnhitHandle,
                                  poolSurpriseHandle);
            poolOutput[0].delete();

            // save it to workspace
            poolRef = savePool(wc,
                               input.getWs(),
                               input.getOutputPool(),
                               pool,
                               makeProvenance("TnSeq pool",
                                              methodName,
                                              methodParams));
            objects.add(new WorkspaceObject()
                        .withRef(poolRef)
                        .withDescription("TnSeq library pool"));
            

            reportText += "\n---\n\nStep 2: TnSeq pool construction output:\n";
            lines = Files.readAllLines(Paths.get(poolOutput[1].getPath()), Charset.defaultCharset());
            for (String line : lines) {
                if (line.indexOf("with no good insertions") > -1)
                    line = line.replace(poolOutput[0].getAbsolutePath()+".unhit", "Pool object (use 'Get Essential Genes' method to view)");
                else if (line.indexOf("with surprising insertions") > -1)
                    line = line.replace(poolOutput[0].getAbsolutePath()+".surprise", "Pool object");
                else if (line.indexOf("read and strain counts for hit genes") > -1)
                    line = line.replace(poolOutput[0].getAbsolutePath()+".hit", "Pool object");
            
                reportText += line+"\n";
            }
            poolOutput[1].delete();
        }
        catch (Exception e) {
            reportText += "\n\nERROR: "+e.getMessage();
            warnings = new ArrayList<String>();
            warnings.add("ERROR: "+e.getMessage());
        }

        // generate report with list of objects created
        String[] report = makeReport(wc,
                                     input.getWs(),
                                     reportText,
                                     warnings,
                                     objects,
                                     makeProvenance("RBTnSeq TnSeq report",
                                                    methodName,
                                                    methodParams));

        TnSeqOutput rv = new TnSeqOutput()
            .withReportName(report[0])
            .withReportRef(report[1]);

        return rv;
    }

    /**
       Gets list of essential genes from a TnSeq experiment
    */
    public static EssentialGenesOutput getEssentialGenes(String wsURL,
                                                         String shockURL,
                                                         AuthToken token,
                                                         EssentialGenesInput input) throws Exception {

        // turn local into absolute paths
        String poolRef = input.getInputPool();
        if (poolRef.indexOf("/") == -1)
            poolRef = input.getWs()+"/"+poolRef;

        // output feature set
        Map<String,List<String>> featureMap = new HashMap<String,List<String>>();
        FeatureSet featureSet = new FeatureSet()
            .withDescription("Essential genes")
            .withElements(featureMap);

        // for provenance
        String methodName = "TnSeq.getEssentialGenes";
        List<UObject> methodParams = Arrays.asList(new UObject(input));

        // load in Pool object
        String reportText = "Getting essential genes from TnSeq experiment.\n";
        WorkspaceClient wc = createWsClient(wsURL,token);
        Pool pool = wc.getObjects(Arrays.asList(new ObjectIdentity().withRef(poolRef))).get(0).getData().asClassInstance(Pool.class);
        if (pool==null)
            throw new Exception("Null return error reading Pool");
        String genomeRef = pool.getGenome();

        // each feature in the FeatureSet should map to list of genomes(?)
        List<String> genomeList = new ArrayList<String>();
        genomeList.add(genomeRef);

        // get list of features in genome; don't actually dump it
        Tuple3<File,
            Map<String,String>,
            ArrayList<Tuple5<Integer,Long,Long,String,String>>> genomeData =
            dumpGenomeTab(wc,
                          null,
                          genomeRef);
        ArrayList<Tuple5<Integer,Long,Long,String,String>> featureList = genomeData.getE3();

        // dump un-hit (essential) gene list from Shock
        Handle h = pool.getPoolUnhitFile();
        if (h==null)
            reportText += "No essential genes were found in this experiment.";
        else {
            int nEssentialGenes = 0;
            File tempDir = new File("/kb/module/work/");
            File unhitFile = File.createTempFile("pool", ".tab", tempDir);
            unhitFile.delete();
            unhitFile = fromShock(h, token, unhitFile, false);

            if (unhitFile==null)
                throw new Exception("failed to load from Shock unhit pool file "+unhitFile.getPath());

            // load in file; feature index is first column
            BufferedReader infile = IO.openReader(unhitFile.getPath());
            if (infile==null)
                throw new Exception("failed to open unhit pool file "+unhitFile.getPath());

            while (infile.ready()) {
                String buffer = infile.readLine();
                if (buffer==null)
                    break;

                int pos = buffer.indexOf("\t");
                if (pos > 0) {
                    int featureIndex = StringUtil.atoi(buffer,0,pos)-1;
                    if (featureIndex > -1) {
                        Tuple5<Integer,Long,Long,String,String> featureInfo = featureList.get(featureIndex);
                        // feature ID is 4th element
                        if (featureInfo != null) {
                            nEssentialGenes++;
                            featureMap.put(featureInfo.getE4(),genomeList);
                        }
                    }
                }
            }
            infile.close();
            unhitFile.delete();
            
            reportText += nEssentialGenes+" essential genes were found in this TnSeq experiment";
        }

        // save the feature set
        String featureSetRef = saveFeatureSet(wc,
                                              input.getWs(),
                                              input.getOutputFeatureSet(),
                                              featureSet,
                                              makeProvenance("Essential genes",
                                                             methodName,
                                                             methodParams));

        // generate report with list of objects created
        List<WorkspaceObject> objects = new ArrayList<WorkspaceObject>();
        objects.add(new WorkspaceObject()
                    .withRef(featureSetRef)
                    .withDescription("Essential genes"));
        String[] report = makeReport(wc,
                                     input.getWs(),
                                     reportText,
                                     null,
                                     objects,
                                     makeProvenance("RBTnSeq essential genes report",
                                                    methodName,
                                                    methodParams));
        EssentialGenesOutput rv = new EssentialGenesOutput()
            .withOutputFeatureSet(featureSetRef)
            .withReportName(report[0])
            .withReportRef(report[1]);

        return rv;
    }
}


