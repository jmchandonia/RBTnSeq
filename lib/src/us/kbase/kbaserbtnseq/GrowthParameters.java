
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
 * <p>Original spec-file type: GrowthParameters</p>
 * <pre>
 * GrowthParameters describes all the conditions a particular aliquot
 * was subjected to in an experiment
 * @optional description gDNA_plate gDNA_well index media growth_method group temperature pH isLiquid isAerobic shaking growth_plate_id growth_plate_wells startOD endOD total_generations
 * </pre>
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("com.googlecode.jsonschema2pojo")
@JsonPropertyOrder({
    "description",
    "gDNA_plate",
    "gDNA_well",
    "index",
    "media",
    "growth_method",
    "group",
    "temperature",
    "pH",
    "isLiquid",
    "isAerobic",
    "shaking",
    "growth_plate_id",
    "growth_plate_wells",
    "startOD",
    "endOD",
    "total_generations"
})
public class GrowthParameters {

    @JsonProperty("description")
    private String description;
    @JsonProperty("gDNA_plate")
    private String gDNAPlate;
    @JsonProperty("gDNA_well")
    private String gDNAWell;
    @JsonProperty("index")
    private String index;
    @JsonProperty("media")
    private String media;
    @JsonProperty("growth_method")
    private String growthMethod;
    @JsonProperty("group")
    private String group;
    @JsonProperty("temperature")
    private Double temperature;
    @JsonProperty("pH")
    private Double pH;
    @JsonProperty("isLiquid")
    private Long isLiquid;
    @JsonProperty("isAerobic")
    private Long isAerobic;
    @JsonProperty("shaking")
    private String shaking;
    @JsonProperty("growth_plate_id")
    private String growthPlateId;
    @JsonProperty("growth_plate_wells")
    private String growthPlateWells;
    @JsonProperty("startOD")
    private Double startOD;
    @JsonProperty("endOD")
    private Double endOD;
    @JsonProperty("total_generations")
    private Double totalGenerations;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("description")
    public String getDescription() {
        return description;
    }

    @JsonProperty("description")
    public void setDescription(String description) {
        this.description = description;
    }

    public GrowthParameters withDescription(String description) {
        this.description = description;
        return this;
    }

    @JsonProperty("gDNA_plate")
    public String getGDNAPlate() {
        return gDNAPlate;
    }

    @JsonProperty("gDNA_plate")
    public void setGDNAPlate(String gDNAPlate) {
        this.gDNAPlate = gDNAPlate;
    }

    public GrowthParameters withGDNAPlate(String gDNAPlate) {
        this.gDNAPlate = gDNAPlate;
        return this;
    }

    @JsonProperty("gDNA_well")
    public String getGDNAWell() {
        return gDNAWell;
    }

    @JsonProperty("gDNA_well")
    public void setGDNAWell(String gDNAWell) {
        this.gDNAWell = gDNAWell;
    }

    public GrowthParameters withGDNAWell(String gDNAWell) {
        this.gDNAWell = gDNAWell;
        return this;
    }

    @JsonProperty("index")
    public String getIndex() {
        return index;
    }

    @JsonProperty("index")
    public void setIndex(String index) {
        this.index = index;
    }

    public GrowthParameters withIndex(String index) {
        this.index = index;
        return this;
    }

    @JsonProperty("media")
    public String getMedia() {
        return media;
    }

    @JsonProperty("media")
    public void setMedia(String media) {
        this.media = media;
    }

    public GrowthParameters withMedia(String media) {
        this.media = media;
        return this;
    }

    @JsonProperty("growth_method")
    public String getGrowthMethod() {
        return growthMethod;
    }

    @JsonProperty("growth_method")
    public void setGrowthMethod(String growthMethod) {
        this.growthMethod = growthMethod;
    }

    public GrowthParameters withGrowthMethod(String growthMethod) {
        this.growthMethod = growthMethod;
        return this;
    }

    @JsonProperty("group")
    public String getGroup() {
        return group;
    }

    @JsonProperty("group")
    public void setGroup(String group) {
        this.group = group;
    }

    public GrowthParameters withGroup(String group) {
        this.group = group;
        return this;
    }

    @JsonProperty("temperature")
    public Double getTemperature() {
        return temperature;
    }

    @JsonProperty("temperature")
    public void setTemperature(Double temperature) {
        this.temperature = temperature;
    }

    public GrowthParameters withTemperature(Double temperature) {
        this.temperature = temperature;
        return this;
    }

    @JsonProperty("pH")
    public Double getPH() {
        return pH;
    }

    @JsonProperty("pH")
    public void setPH(Double pH) {
        this.pH = pH;
    }

    public GrowthParameters withPH(Double pH) {
        this.pH = pH;
        return this;
    }

    @JsonProperty("isLiquid")
    public Long getIsLiquid() {
        return isLiquid;
    }

    @JsonProperty("isLiquid")
    public void setIsLiquid(Long isLiquid) {
        this.isLiquid = isLiquid;
    }

    public GrowthParameters withIsLiquid(Long isLiquid) {
        this.isLiquid = isLiquid;
        return this;
    }

    @JsonProperty("isAerobic")
    public Long getIsAerobic() {
        return isAerobic;
    }

    @JsonProperty("isAerobic")
    public void setIsAerobic(Long isAerobic) {
        this.isAerobic = isAerobic;
    }

    public GrowthParameters withIsAerobic(Long isAerobic) {
        this.isAerobic = isAerobic;
        return this;
    }

    @JsonProperty("shaking")
    public String getShaking() {
        return shaking;
    }

    @JsonProperty("shaking")
    public void setShaking(String shaking) {
        this.shaking = shaking;
    }

    public GrowthParameters withShaking(String shaking) {
        this.shaking = shaking;
        return this;
    }

    @JsonProperty("growth_plate_id")
    public String getGrowthPlateId() {
        return growthPlateId;
    }

    @JsonProperty("growth_plate_id")
    public void setGrowthPlateId(String growthPlateId) {
        this.growthPlateId = growthPlateId;
    }

    public GrowthParameters withGrowthPlateId(String growthPlateId) {
        this.growthPlateId = growthPlateId;
        return this;
    }

    @JsonProperty("growth_plate_wells")
    public String getGrowthPlateWells() {
        return growthPlateWells;
    }

    @JsonProperty("growth_plate_wells")
    public void setGrowthPlateWells(String growthPlateWells) {
        this.growthPlateWells = growthPlateWells;
    }

    public GrowthParameters withGrowthPlateWells(String growthPlateWells) {
        this.growthPlateWells = growthPlateWells;
        return this;
    }

    @JsonProperty("startOD")
    public Double getStartOD() {
        return startOD;
    }

    @JsonProperty("startOD")
    public void setStartOD(Double startOD) {
        this.startOD = startOD;
    }

    public GrowthParameters withStartOD(Double startOD) {
        this.startOD = startOD;
        return this;
    }

    @JsonProperty("endOD")
    public Double getEndOD() {
        return endOD;
    }

    @JsonProperty("endOD")
    public void setEndOD(Double endOD) {
        this.endOD = endOD;
    }

    public GrowthParameters withEndOD(Double endOD) {
        this.endOD = endOD;
        return this;
    }

    @JsonProperty("total_generations")
    public Double getTotalGenerations() {
        return totalGenerations;
    }

    @JsonProperty("total_generations")
    public void setTotalGenerations(Double totalGenerations) {
        this.totalGenerations = totalGenerations;
    }

    public GrowthParameters withTotalGenerations(Double totalGenerations) {
        this.totalGenerations = totalGenerations;
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
        return ((((((((((((((((((((((((((((((((((((("GrowthParameters"+" [description=")+ description)+", gDNAPlate=")+ gDNAPlate)+", gDNAWell=")+ gDNAWell)+", index=")+ index)+", media=")+ media)+", growthMethod=")+ growthMethod)+", group=")+ group)+", temperature=")+ temperature)+", pH=")+ pH)+", isLiquid=")+ isLiquid)+", isAerobic=")+ isAerobic)+", shaking=")+ shaking)+", growthPlateId=")+ growthPlateId)+", growthPlateWells=")+ growthPlateWells)+", startOD=")+ startOD)+", endOD=")+ endOD)+", totalGenerations=")+ totalGenerations)+", additionalProperties=")+ additionalProperties)+"]");
    }

}
