
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
 * <p>Original spec-file type: Pool</p>
 * <pre>
 * A Pool is a collection of barcoded strains.  Barcodes, tags, etc should
 * be stored as Deltas in each strain.
 * </pre>
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("com.googlecode.jsonschema2pojo")
@JsonPropertyOrder({
    "mapped_reads",
    "strains"
})
public class Pool {

    @JsonProperty("mapped_reads")
    private String mappedReads;
    @JsonProperty("strains")
    private List<Tuple2 <Strain, Long>> strains;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("mapped_reads")
    public String getMappedReads() {
        return mappedReads;
    }

    @JsonProperty("mapped_reads")
    public void setMappedReads(String mappedReads) {
        this.mappedReads = mappedReads;
    }

    public Pool withMappedReads(String mappedReads) {
        this.mappedReads = mappedReads;
        return this;
    }

    @JsonProperty("strains")
    public List<Tuple2 <Strain, Long>> getStrains() {
        return strains;
    }

    @JsonProperty("strains")
    public void setStrains(List<Tuple2 <Strain, Long>> strains) {
        this.strains = strains;
    }

    public Pool withStrains(List<Tuple2 <Strain, Long>> strains) {
        this.strains = strains;
        return this;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperties(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

    @Override
    public String toString() {
        return ((((((("Pool"+" [mappedReads=")+ mappedReads)+", strains=")+ strains)+", additionalProperties=")+ additionalProperties)+"]");
    }

}
