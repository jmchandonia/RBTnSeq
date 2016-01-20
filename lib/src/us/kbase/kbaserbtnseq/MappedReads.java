
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
import us.kbase.common.service.Tuple10;
import us.kbase.common.service.Tuple2;


/**
 * <p>Original spec-file type: MappedReads</p>
 * <pre>
 * A MappedReads object stores the mapping of reads to a genome
 * </pre>
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("com.googlecode.jsonschema2pojo")
@JsonPropertyOrder({
    "genome",
    "reads",
    "model",
    "mapped_reads"
})
public class MappedReads {

    @JsonProperty("genome")
    private java.lang.String genome;
    @JsonProperty("reads")
    private java.lang.String reads;
    @JsonProperty("model")
    private Tuple2 <String, String> model;
    @JsonProperty("mapped_reads")
    private List<Tuple10 <String, String, String, Long, String, Long, Long, Long, Double, Double>> mappedReads;
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

    @JsonProperty("mapped_reads")
    public List<Tuple10 <String, String, String, Long, String, Long, Long, Long, Double, Double>> getMappedReads() {
        return mappedReads;
    }

    @JsonProperty("mapped_reads")
    public void setMappedReads(List<Tuple10 <String, String, String, Long, String, Long, Long, Long, Double, Double>> mappedReads) {
        this.mappedReads = mappedReads;
    }

    public MappedReads withMappedReads(List<Tuple10 <String, String, String, Long, String, Long, Long, Long, Double, Double>> mappedReads) {
        this.mappedReads = mappedReads;
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
        return ((((((((((("MappedReads"+" [genome=")+ genome)+", reads=")+ reads)+", model=")+ model)+", mappedReads=")+ mappedReads)+", additionalProperties=")+ additionalProperties)+"]");
    }

}
