
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
 * <p>Original spec-file type: TnSeqInput</p>
 * <pre>
 * Inputs to TnSeq part of pipeline.
 * This should be split into 2 methods or run as an app
 * </pre>
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("com.googlecode.jsonschema2pojo")
@JsonPropertyOrder({
    "ws",
    "input_read_library",
    "input_genome",
    "input_barcode_model",
    "input_minN",
    "output_mapped_reads",
    "output_pool"
})
public class TnSeqInput {

    @JsonProperty("ws")
    private String ws;
    @JsonProperty("input_read_library")
    private String inputReadLibrary;
    @JsonProperty("input_genome")
    private String inputGenome;
    @JsonProperty("input_barcode_model")
    private String inputBarcodeModel;
    @JsonProperty("input_minN")
    private Long inputMinN;
    @JsonProperty("output_mapped_reads")
    private String outputMappedReads;
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

    public TnSeqInput withWs(String ws) {
        this.ws = ws;
        return this;
    }

    @JsonProperty("input_read_library")
    public String getInputReadLibrary() {
        return inputReadLibrary;
    }

    @JsonProperty("input_read_library")
    public void setInputReadLibrary(String inputReadLibrary) {
        this.inputReadLibrary = inputReadLibrary;
    }

    public TnSeqInput withInputReadLibrary(String inputReadLibrary) {
        this.inputReadLibrary = inputReadLibrary;
        return this;
    }

    @JsonProperty("input_genome")
    public String getInputGenome() {
        return inputGenome;
    }

    @JsonProperty("input_genome")
    public void setInputGenome(String inputGenome) {
        this.inputGenome = inputGenome;
    }

    public TnSeqInput withInputGenome(String inputGenome) {
        this.inputGenome = inputGenome;
        return this;
    }

    @JsonProperty("input_barcode_model")
    public String getInputBarcodeModel() {
        return inputBarcodeModel;
    }

    @JsonProperty("input_barcode_model")
    public void setInputBarcodeModel(String inputBarcodeModel) {
        this.inputBarcodeModel = inputBarcodeModel;
    }

    public TnSeqInput withInputBarcodeModel(String inputBarcodeModel) {
        this.inputBarcodeModel = inputBarcodeModel;
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

    public TnSeqInput withInputMinN(Long inputMinN) {
        this.inputMinN = inputMinN;
        return this;
    }

    @JsonProperty("output_mapped_reads")
    public String getOutputMappedReads() {
        return outputMappedReads;
    }

    @JsonProperty("output_mapped_reads")
    public void setOutputMappedReads(String outputMappedReads) {
        this.outputMappedReads = outputMappedReads;
    }

    public TnSeqInput withOutputMappedReads(String outputMappedReads) {
        this.outputMappedReads = outputMappedReads;
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

    public TnSeqInput withOutputPool(String outputPool) {
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
        return ((((((((((((((((("TnSeqInput"+" [ws=")+ ws)+", inputReadLibrary=")+ inputReadLibrary)+", inputGenome=")+ inputGenome)+", inputBarcodeModel=")+ inputBarcodeModel)+", inputMinN=")+ inputMinN)+", outputMappedReads=")+ outputMappedReads)+", outputPool=")+ outputPool)+", additionalProperties=")+ additionalProperties)+"]");
    }

}
