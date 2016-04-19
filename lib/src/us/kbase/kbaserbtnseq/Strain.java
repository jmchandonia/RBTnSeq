
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


/**
 * <p>Original spec-file type: Strain</p>
 * <pre>
 * A Strain is a particular genetic variant of an organism.  Optionally,
 * it may be:
 *   * derived from another Strain (e.g., as an engineered mutant)
 *   * sequenced
 *   * a wild-type example of a Genome
 * If a strain is "wild type" it should have a non-null genome_ref and a
 * null derived_from_strain.  If not wild type (or otherwise not
 * characterized), genome_ref should be set to null.  (If genome_ref
 * were not null, a large pool of mutant strains would have too many references
 * to the genome in our current data model.)
 * @optional description genome derived_from_strain deltas
 * </pre>
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("com.googlecode.jsonschema2pojo")
@JsonPropertyOrder({
    "name",
    "description",
    "genome",
    "derived_from_strain",
    "deltas"
})
public class Strain {

    @JsonProperty("name")
    private String name;
    @JsonProperty("description")
    private String description;
    @JsonProperty("genome")
    private String genome;
    @JsonProperty("derived_from_strain")
    private String derivedFromStrain;
    @JsonProperty("deltas")
    private List<Delta> deltas;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    public Strain withName(String name) {
        this.name = name;
        return this;
    }

    @JsonProperty("description")
    public String getDescription() {
        return description;
    }

    @JsonProperty("description")
    public void setDescription(String description) {
        this.description = description;
    }

    public Strain withDescription(String description) {
        this.description = description;
        return this;
    }

    @JsonProperty("genome")
    public String getGenome() {
        return genome;
    }

    @JsonProperty("genome")
    public void setGenome(String genome) {
        this.genome = genome;
    }

    public Strain withGenome(String genome) {
        this.genome = genome;
        return this;
    }

    @JsonProperty("derived_from_strain")
    public String getDerivedFromStrain() {
        return derivedFromStrain;
    }

    @JsonProperty("derived_from_strain")
    public void setDerivedFromStrain(String derivedFromStrain) {
        this.derivedFromStrain = derivedFromStrain;
    }

    public Strain withDerivedFromStrain(String derivedFromStrain) {
        this.derivedFromStrain = derivedFromStrain;
        return this;
    }

    @JsonProperty("deltas")
    public List<Delta> getDeltas() {
        return deltas;
    }

    @JsonProperty("deltas")
    public void setDeltas(List<Delta> deltas) {
        this.deltas = deltas;
    }

    public Strain withDeltas(List<Delta> deltas) {
        this.deltas = deltas;
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
        return ((((((((((((("Strain"+" [name=")+ name)+", description=")+ description)+", genome=")+ genome)+", derivedFromStrain=")+ derivedFromStrain)+", deltas=")+ deltas)+", additionalProperties=")+ additionalProperties)+"]");
    }

}
