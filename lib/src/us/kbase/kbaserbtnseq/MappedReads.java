
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


/**
 * <p>Original spec-file type: MappedReads</p>
 * <pre>
 * A MappedReads object stores the mapping of reads to a genome.
 * Unique and non-unique reads are stored in arrays indexed using
 * the contig index.  The last set of reads in each of these arrays
 * corresponds to "past end" reads.
 * </pre>
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("com.googlecode.jsonschema2pojo")
@JsonPropertyOrder({
    "genome",
    "reads",
    "model",
    "unique_reads_by_contig",
    "nonunique_reads_by_contig"
})
public class MappedReads {

    @JsonProperty("genome")
    private java.lang.String genome;
    @JsonProperty("reads")
    private java.lang.String reads;
    @JsonProperty("model")
    private Tuple2 <String, String> model;
    @JsonProperty("unique_reads_by_contig")
    private List<List<us.kbase.common.service.Tuple7 <String, String, Long, Long, Long, Double, Double>>> uniqueReadsByContig;
    @JsonProperty("nonunique_reads_by_contig")
    private List<List<us.kbase.common.service.Tuple7 <String, String, Long, Long, Long, Double, Double>>> nonuniqueReadsByContig;
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

    @JsonProperty("unique_reads_by_contig")
    public List<List<us.kbase.common.service.Tuple7 <String, String, Long, Long, Long, Double, Double>>> getUniqueReadsByContig() {
        return uniqueReadsByContig;
    }

    @JsonProperty("unique_reads_by_contig")
    public void setUniqueReadsByContig(List<List<us.kbase.common.service.Tuple7 <String, String, Long, Long, Long, Double, Double>>> uniqueReadsByContig) {
        this.uniqueReadsByContig = uniqueReadsByContig;
    }

    public MappedReads withUniqueReadsByContig(List<List<us.kbase.common.service.Tuple7 <String, String, Long, Long, Long, Double, Double>>> uniqueReadsByContig) {
        this.uniqueReadsByContig = uniqueReadsByContig;
        return this;
    }

    @JsonProperty("nonunique_reads_by_contig")
    public List<List<us.kbase.common.service.Tuple7 <String, String, Long, Long, Long, Double, Double>>> getNonuniqueReadsByContig() {
        return nonuniqueReadsByContig;
    }

    @JsonProperty("nonunique_reads_by_contig")
    public void setNonuniqueReadsByContig(List<List<us.kbase.common.service.Tuple7 <String, String, Long, Long, Long, Double, Double>>> nonuniqueReadsByContig) {
        this.nonuniqueReadsByContig = nonuniqueReadsByContig;
    }

    public MappedReads withNonuniqueReadsByContig(List<List<us.kbase.common.service.Tuple7 <String, String, Long, Long, Long, Double, Double>>> nonuniqueReadsByContig) {
        this.nonuniqueReadsByContig = nonuniqueReadsByContig;
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
        return ((((((((((((("MappedReads"+" [genome=")+ genome)+", reads=")+ reads)+", model=")+ model)+", uniqueReadsByContig=")+ uniqueReadsByContig)+", nonuniqueReadsByContig=")+ nonuniqueReadsByContig)+", additionalProperties=")+ additionalProperties)+"]");
    }

}
