
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
 * <p>Original spec-file type: TnSeqPoolInput</p>
 * <pre>
 * Inputs to makeTnSeqPool
 * </pre>
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("com.googlecode.jsonschema2pojo")
@JsonPropertyOrder({
    "ws",
    "input_mapped_reads",
    "input_minN",
    "output_pool"
})
public class TnSeqPoolInput {

    @JsonProperty("ws")
    private String ws;
    @JsonProperty("input_mapped_reads")
    private String inputMappedReads;
    @JsonProperty("input_minN")
    private Long inputMinN;
    @JsonProperty("output_pool")
    private String outputPool;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("ws")
    public String getWs() {
        return ws;
    }

    @JsonProperty("ws")
    public void setWs(String ws) {
        this.ws = ws;
    }

    public TnSeqPoolInput withWs(String ws) {
        this.ws = ws;
        return this;
    }

    @JsonProperty("input_mapped_reads")
    public String getInputMappedReads() {
        return inputMappedReads;
    }

    @JsonProperty("input_mapped_reads")
    public void setInputMappedReads(String inputMappedReads) {
        this.inputMappedReads = inputMappedReads;
    }

    public TnSeqPoolInput withInputMappedReads(String inputMappedReads) {
        this.inputMappedReads = inputMappedReads;
        return this;
    }

    @JsonProperty("input_minN")
    public Long getInputMinN() {
        return inputMinN;
    }

    @JsonProperty("input_minN")
    public void setInputMinN(Long inputMinN) {
        this.inputMinN = inputMinN;
    }

    public TnSeqPoolInput withInputMinN(Long inputMinN) {
        this.inputMinN = inputMinN;
        return this;
    }

    @JsonProperty("output_pool")
    public String getOutputPool() {
        return outputPool;
    }

    @JsonProperty("output_pool")
    public void setOutputPool(String outputPool) {
        this.outputPool = outputPool;
    }

    public TnSeqPoolInput withOutputPool(String outputPool) {
        this.outputPool = outputPool;
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
        return ((((((((((("TnSeqPoolInput"+" [ws=")+ ws)+", inputMappedReads=")+ inputMappedReads)+", inputMinN=")+ inputMinN)+", outputPool=")+ outputPool)+", additionalProperties=")+ additionalProperties)+"]");
    }

}
