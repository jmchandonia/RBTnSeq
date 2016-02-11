
package rbtnseq;

import java.util.HashMap;
import java.util.Map;
import javax.annotation.Generated;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;


/**
 * <p>Original spec-file type: EssentialGenesInput</p>
 * <pre>
 * Inputs to getEssentialGenes
 * </pre>
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("com.googlecode.jsonschema2pojo")
@JsonPropertyOrder({
    "ws",
    "input_pool",
    "output_feature_set"
})
public class EssentialGenesInput {

    @JsonProperty("ws")
    private String ws;
    @JsonProperty("input_pool")
    private String inputPool;
    @JsonProperty("output_feature_set")
    private String outputFeatureSet;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("ws")
    public String getWs() {
        return ws;
    }

    @JsonProperty("ws")
    public void setWs(String ws) {
        this.ws = ws;
    }

    public EssentialGenesInput withWs(String ws) {
        this.ws = ws;
        return this;
    }

    @JsonProperty("input_pool")
    public String getInputPool() {
        return inputPool;
    }

    @JsonProperty("input_pool")
    public void setInputPool(String inputPool) {
        this.inputPool = inputPool;
    }

    public EssentialGenesInput withInputPool(String inputPool) {
        this.inputPool = inputPool;
        return this;
    }

    @JsonProperty("output_feature_set")
    public String getOutputFeatureSet() {
        return outputFeatureSet;
    }

    @JsonProperty("output_feature_set")
    public void setOutputFeatureSet(String outputFeatureSet) {
        this.outputFeatureSet = outputFeatureSet;
    }

    public EssentialGenesInput withOutputFeatureSet(String outputFeatureSet) {
        this.outputFeatureSet = outputFeatureSet;
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
        return ((((((((("EssentialGenesInput"+" [ws=")+ ws)+", inputPool=")+ inputPool)+", outputFeatureSet=")+ outputFeatureSet)+", additionalProperties=")+ additionalProperties)+"]");
    }

}
