
package us.kbase.kbaserbtnseq;

import java.util.HashMap;
import java.util.Map;
import javax.annotation.Generated;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;


/**
 * <p>Original spec-file type: Delta</p>
 * <pre>
 * A Delta is a description of a single change to a strain.  A series
 * of Deltas defines the transition from one Strain to another.  For
 * sequenced insertions or substitutions, give the 0-indexed position
 * on the contig where the insertion/substitution begins, in the +
 * direction.  For sequenced deletions and substitutions, give the
 * position and length.  The position of all Deltas should be
 * calculated relative to the parent strain (derived_from_strain), so
 * that the Deltas could be applied in any order.
 * @optional change feature contig_index sequence position length
 * </pre>
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("com.googlecode.jsonschema2pojo")
@JsonPropertyOrder({
    "description",
    "change",
    "feature",
    "contig_index",
    "sequence",
    "position",
    "length"
})
public class Delta {

    @JsonProperty("description")
    private String description;
    @JsonProperty("change")
    private String change;
    @JsonProperty("feature")
    private String feature;
    @JsonProperty("contig_index")
    private Long contigIndex;
    @JsonProperty("sequence")
    private String sequence;
    @JsonProperty("position")
    private Long position;
    @JsonProperty("length")
    private Long length;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("description")
    public String getDescription() {
        return description;
    }

    @JsonProperty("description")
    public void setDescription(String description) {
        this.description = description;
    }

    public Delta withDescription(String description) {
        this.description = description;
        return this;
    }

    @JsonProperty("change")
    public String getChange() {
        return change;
    }

    @JsonProperty("change")
    public void setChange(String change) {
        this.change = change;
    }

    public Delta withChange(String change) {
        this.change = change;
        return this;
    }

    @JsonProperty("feature")
    public String getFeature() {
        return feature;
    }

    @JsonProperty("feature")
    public void setFeature(String feature) {
        this.feature = feature;
    }

    public Delta withFeature(String feature) {
        this.feature = feature;
        return this;
    }

    @JsonProperty("contig_index")
    public Long getContigIndex() {
        return contigIndex;
    }

    @JsonProperty("contig_index")
    public void setContigIndex(Long contigIndex) {
        this.contigIndex = contigIndex;
    }

    public Delta withContigIndex(Long contigIndex) {
        this.contigIndex = contigIndex;
        return this;
    }

    @JsonProperty("sequence")
    public String getSequence() {
        return sequence;
    }

    @JsonProperty("sequence")
    public void setSequence(String sequence) {
        this.sequence = sequence;
    }

    public Delta withSequence(String sequence) {
        this.sequence = sequence;
        return this;
    }

    @JsonProperty("position")
    public Long getPosition() {
        return position;
    }

    @JsonProperty("position")
    public void setPosition(Long position) {
        this.position = position;
    }

    public Delta withPosition(Long position) {
        this.position = position;
        return this;
    }

    @JsonProperty("length")
    public Long getLength() {
        return length;
    }

    @JsonProperty("length")
    public void setLength(Long length) {
        this.length = length;
    }

    public Delta withLength(Long length) {
        this.length = length;
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
        return ((((((((((((((((("Delta"+" [description=")+ description)+", change=")+ change)+", feature=")+ feature)+", contigIndex=")+ contigIndex)+", sequence=")+ sequence)+", position=")+ position)+", length=")+ length)+", additionalProperties=")+ additionalProperties)+"]");
    }

}
