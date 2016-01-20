
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
 * <p>Original spec-file type: Condition</p>
 * <pre>
 * A Condition is something that is added to particular aliquots in
 * a growth experiment, in addition to the media.  e.g., it may be a stress
 * condition, or a nutrient.  Compound is needed if the condition is
 * addition of a chemical in the KBase Biochemistry database.
 * @optional concentration units compound
 * </pre>
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("com.googlecode.jsonschema2pojo")
@JsonPropertyOrder({
    "name",
    "concentration",
    "units",
    "compound"
})
public class Condition {

    @JsonProperty("name")
    private String name;
    @JsonProperty("concentration")
    private Double concentration;
    @JsonProperty("units")
    private String units;
    @JsonProperty("compound")
    private String compound;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    public Condition withName(String name) {
        this.name = name;
        return this;
    }

    @JsonProperty("concentration")
    public Double getConcentration() {
        return concentration;
    }

    @JsonProperty("concentration")
    public void setConcentration(Double concentration) {
        this.concentration = concentration;
    }

    public Condition withConcentration(Double concentration) {
        this.concentration = concentration;
        return this;
    }

    @JsonProperty("units")
    public String getUnits() {
        return units;
    }

    @JsonProperty("units")
    public void setUnits(String units) {
        this.units = units;
    }

    public Condition withUnits(String units) {
        this.units = units;
        return this;
    }

    @JsonProperty("compound")
    public String getCompound() {
        return compound;
    }

    @JsonProperty("compound")
    public void setCompound(String compound) {
        this.compound = compound;
    }

    public Condition withCompound(String compound) {
        this.compound = compound;
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
        return ((((((((((("Condition"+" [name=")+ name)+", concentration=")+ concentration)+", units=")+ units)+", compound=")+ compound)+", additionalProperties=")+ additionalProperties)+"]");
    }

}
