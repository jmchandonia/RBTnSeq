
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
 * <p>Original spec-file type: BarSeqExperiment</p>
 * <pre>
 * usually one or two conditions
 * </pre>
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("com.googlecode.jsonschema2pojo")
@JsonPropertyOrder({
    "name",
    "person",
    "mutant_lib_name",
    "start_date",
    "sequenced_at",
    "growth_parameters",
    "conditions",
    "tnseq_pool"
})
public class BarSeqExperiment {

    @JsonProperty("name")
    private String name;
    @JsonProperty("person")
    private String person;
    @JsonProperty("mutant_lib_name")
    private String mutantLibName;
    @JsonProperty("start_date")
    private String startDate;
    @JsonProperty("sequenced_at")
    private String sequencedAt;
    /**
     * <p>Original spec-file type: GrowthParameters</p>
     * <pre>
     * GrowthParameters describes all the conditions a particular aliquot
     * was subjected to in an experiment
     * @optional description gDNA_plate gDNA_well index media growth_method group temperature pH isLiquid isAerobic shaking growth_plate_id growth_plate_wells startOD endOD total_generations
     * </pre>
     * 
     */
    @JsonProperty("growth_parameters")
    private GrowthParameters growthParameters;
    @JsonProperty("conditions")
    private List<Condition> conditions;
    @JsonProperty("tnseq_pool")
    private String tnseqPool;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    public BarSeqExperiment withName(String name) {
        this.name = name;
        return this;
    }

    @JsonProperty("person")
    public String getPerson() {
        return person;
    }

    @JsonProperty("person")
    public void setPerson(String person) {
        this.person = person;
    }

    public BarSeqExperiment withPerson(String person) {
        this.person = person;
        return this;
    }

    @JsonProperty("mutant_lib_name")
    public String getMutantLibName() {
        return mutantLibName;
    }

    @JsonProperty("mutant_lib_name")
    public void setMutantLibName(String mutantLibName) {
        this.mutantLibName = mutantLibName;
    }

    public BarSeqExperiment withMutantLibName(String mutantLibName) {
        this.mutantLibName = mutantLibName;
        return this;
    }

    @JsonProperty("start_date")
    public String getStartDate() {
        return startDate;
    }

    @JsonProperty("start_date")
    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public BarSeqExperiment withStartDate(String startDate) {
        this.startDate = startDate;
        return this;
    }

    @JsonProperty("sequenced_at")
    public String getSequencedAt() {
        return sequencedAt;
    }

    @JsonProperty("sequenced_at")
    public void setSequencedAt(String sequencedAt) {
        this.sequencedAt = sequencedAt;
    }

    public BarSeqExperiment withSequencedAt(String sequencedAt) {
        this.sequencedAt = sequencedAt;
        return this;
    }

    /**
     * <p>Original spec-file type: GrowthParameters</p>
     * <pre>
     * GrowthParameters describes all the conditions a particular aliquot
     * was subjected to in an experiment
     * @optional description gDNA_plate gDNA_well index media growth_method group temperature pH isLiquid isAerobic shaking growth_plate_id growth_plate_wells startOD endOD total_generations
     * </pre>
     * 
     */
    @JsonProperty("growth_parameters")
    public GrowthParameters getGrowthParameters() {
        return growthParameters;
    }

    /**
     * <p>Original spec-file type: GrowthParameters</p>
     * <pre>
     * GrowthParameters describes all the conditions a particular aliquot
     * was subjected to in an experiment
     * @optional description gDNA_plate gDNA_well index media growth_method group temperature pH isLiquid isAerobic shaking growth_plate_id growth_plate_wells startOD endOD total_generations
     * </pre>
     * 
     */
    @JsonProperty("growth_parameters")
    public void setGrowthParameters(GrowthParameters growthParameters) {
        this.growthParameters = growthParameters;
    }

    public BarSeqExperiment withGrowthParameters(GrowthParameters growthParameters) {
        this.growthParameters = growthParameters;
        return this;
    }

    @JsonProperty("conditions")
    public List<Condition> getConditions() {
        return conditions;
    }

    @JsonProperty("conditions")
    public void setConditions(List<Condition> conditions) {
        this.conditions = conditions;
    }

    public BarSeqExperiment withConditions(List<Condition> conditions) {
        this.conditions = conditions;
        return this;
    }

    @JsonProperty("tnseq_pool")
    public String getTnseqPool() {
        return tnseqPool;
    }

    @JsonProperty("tnseq_pool")
    public void setTnseqPool(String tnseqPool) {
        this.tnseqPool = tnseqPool;
    }

    public BarSeqExperiment withTnseqPool(String tnseqPool) {
        this.tnseqPool = tnseqPool;
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
        return ((((((((((((((((((("BarSeqExperiment"+" [name=")+ name)+", person=")+ person)+", mutantLibName=")+ mutantLibName)+", startDate=")+ startDate)+", sequencedAt=")+ sequencedAt)+", growthParameters=")+ growthParameters)+", conditions=")+ conditions)+", tnseqPool=")+ tnseqPool)+", additionalProperties=")+ additionalProperties)+"]");
    }

}
