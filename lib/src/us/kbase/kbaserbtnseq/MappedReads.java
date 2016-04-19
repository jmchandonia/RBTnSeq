
package us.kbase.kbaserbtnseq;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Generated;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import us.kbase.common.service.Tuple2;
import us.kbase.kbaseassembly.Handle;


/**
 * <p>Original spec-file type: MappedReads</p>
 * <pre>
 * A MappedReads object stores the mapping of reads to a genome.
 * Unique and non-unique read positions are stored in arrays indexed using
 * the contig index.  The last set of reads in each of these arrays
 * corresponds to "past end" reads.  Handle should be replaced by
 * a KBaseFile.Handle, but this is not registered yet.
 * </pre>
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("com.googlecode.jsonschema2pojo")
@JsonPropertyOrder({
    "genome",
    "reads",
    "model",
    "mapped_reads_file",
    "unique_insert_pos_by_contig",
    "nonunique_insert_pos_by_contig"
})
public class MappedReads {

    @JsonProperty("genome")
    private java.lang.String genome;
    @JsonProperty("reads")
    private java.lang.String reads;
    @JsonProperty("model")
    private Tuple2 <String, String> model;
    /**
     * <p>Original spec-file type: Handle</p>
     * <pre>
     * @optional hid file_name type url remote_md5 remote_sha1
     * </pre>
     * 
     */
    @JsonProperty("mapped_reads_file")
    private Handle mappedReadsFile;
    @JsonProperty("unique_insert_pos_by_contig")
    private List<List<Long>> uniqueInsertPosByContig;
    @JsonProperty("nonunique_insert_pos_by_contig")
    private List<List<Long>> nonuniqueInsertPosByContig;
    private Map<java.lang.String, Object> additionalProperties = new HashMap<java.lang.String, Object>();

    @JsonProperty("genome")
    public java.lang.String getGenome() {
        return genome;
    }

    @JsonProperty("genome")
    public void setGenome(java.lang.String genome) {
        this.genome = genome;
    }

    public MappedReads withGenome(java.lang.String genome) {
        this.genome = genome;
        return this;
    }

    @JsonProperty("reads")
    public java.lang.String getReads() {
        return reads;
    }

    @JsonProperty("reads")
    public void setReads(java.lang.String reads) {
        this.reads = reads;
    }

    public MappedReads withReads(java.lang.String reads) {
        this.reads = reads;
        return this;
    }

    @JsonProperty("model")
    public Tuple2 <String, String> getModel() {
        return model;
    }

    @JsonProperty("model")
    public void setModel(Tuple2 <String, String> model) {
        this.model = model;
    }

    public MappedReads withModel(Tuple2 <String, String> model) {
        this.model = model;
        return this;
    }

    /**
     * <p>Original spec-file type: Handle</p>
     * <pre>
     * @optional hid file_name type url remote_md5 remote_sha1
     * </pre>
     * 
     */
    @JsonProperty("mapped_reads_file")
    public Handle getMappedReadsFile() {
        return mappedReadsFile;
    }

    /**
     * <p>Original spec-file type: Handle</p>
     * <pre>
     * @optional hid file_name type url remote_md5 remote_sha1
     * </pre>
     * 
     */
    @JsonProperty("mapped_reads_file")
    public void setMappedReadsFile(Handle mappedReadsFile) {
        this.mappedReadsFile = mappedReadsFile;
    }

    public MappedReads withMappedReadsFile(Handle mappedReadsFile) {
        this.mappedReadsFile = mappedReadsFile;
        return this;
    }

    @JsonProperty("unique_insert_pos_by_contig")
    public List<List<Long>> getUniqueInsertPosByContig() {
        return uniqueInsertPosByContig;
    }

    @JsonProperty("unique_insert_pos_by_contig")
    public void setUniqueInsertPosByContig(List<List<Long>> uniqueInsertPosByContig) {
        this.uniqueInsertPosByContig = uniqueInsertPosByContig;
    }

    public MappedReads withUniqueInsertPosByContig(List<List<Long>> uniqueInsertPosByContig) {
        this.uniqueInsertPosByContig = uniqueInsertPosByContig;
        return this;
    }

    @JsonProperty("nonunique_insert_pos_by_contig")
    public List<List<Long>> getNonuniqueInsertPosByContig() {
        return nonuniqueInsertPosByContig;
    }

    @JsonProperty("nonunique_insert_pos_by_contig")
    public void setNonuniqueInsertPosByContig(List<List<Long>> nonuniqueInsertPosByContig) {
        this.nonuniqueInsertPosByContig = nonuniqueInsertPosByContig;
    }

    public MappedReads withNonuniqueInsertPosByContig(List<List<Long>> nonuniqueInsertPosByContig) {
        this.nonuniqueInsertPosByContig = nonuniqueInsertPosByContig;
        return this;
    }

    @JsonAnyGetter
    public Map<java.lang.String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperties(java.lang.String name, Object value) {
        this.additionalProperties.put(name, value);
    }

    @Override
    public java.lang.String toString() {
        return ((((((((((((((("MappedReads"+" [genome=")+ genome)+", reads=")+ reads)+", model=")+ model)+", mappedReadsFile=")+ mappedReadsFile)+", uniqueInsertPosByContig=")+ uniqueInsertPosByContig)+", nonuniqueInsertPosByContig=")+ nonuniqueInsertPosByContig)+", additionalProperties=")+ additionalProperties)+"]");
    }

}
