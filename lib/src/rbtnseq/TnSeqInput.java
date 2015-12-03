
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
 * This needs to be split into 2 steps, pending
 * update of some KBaseRBTnSeq objects
 * </pre>
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("com.googlecode.jsonschema2pojo")
@JsonPropertyOrder({
    "ws",
    "read_type",
    "input_read_library",
    "input_genome",
    "sequenced_at",
    "start_date",
    "output_pool",
    "output_tnseq_experiment",
    "output_tnseq_library"
})
public class TnSeqInput {

    @JsonProperty("ws")
    private String ws;
    @JsonProperty("read_type")
    private String readType;
    @JsonProperty("input_read_library")
    private String inputReadLibrary;
    @JsonProperty("input_genome")
    private String inputGenome;
    @JsonProperty("sequenced_at")
    private String sequencedAt;
    @JsonProperty("start_date")
    private String startDate;
    @JsonProperty("output_pool")
    private String outputPool;
    @JsonProperty("output_tnseq_experiment")
    private String outputTnseqExperiment;
    @JsonProperty("output_tnseq_library")
    private String outputTnseqLibrary;
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

    @JsonProperty("read_type")
    public String getReadType() {
        return readType;
    }

    @JsonProperty("read_type")
    public void setReadType(String readType) {
        this.readType = readType;
    }

    public TnSeqInput withReadType(String readType) {
        this.readType = readType;
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

    @JsonProperty("sequenced_at")
    public String getSequencedAt() {
        return sequencedAt;
    }

    @JsonProperty("sequenced_at")
    public void setSequencedAt(String sequencedAt) {
        this.sequencedAt = sequencedAt;
    }

    public TnSeqInput withSequencedAt(String sequencedAt) {
        this.sequencedAt = sequencedAt;
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

    public TnSeqInput withStartDate(String startDate) {
        this.startDate = startDate;
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

    @JsonProperty("output_tnseq_experiment")
    public String getOutputTnseqExperiment() {
        return outputTnseqExperiment;
    }

    @JsonProperty("output_tnseq_experiment")
    public void setOutputTnseqExperiment(String outputTnseqExperiment) {
        this.outputTnseqExperiment = outputTnseqExperiment;
    }

    public TnSeqInput withOutputTnseqExperiment(String outputTnseqExperiment) {
        this.outputTnseqExperiment = outputTnseqExperiment;
        return this;
    }

    @JsonProperty("output_tnseq_library")
    public String getOutputTnseqLibrary() {
        return outputTnseqLibrary;
    }

    @JsonProperty("output_tnseq_library")
    public void setOutputTnseqLibrary(String outputTnseqLibrary) {
        this.outputTnseqLibrary = outputTnseqLibrary;
    }

    public TnSeqInput withOutputTnseqLibrary(String outputTnseqLibrary) {
        this.outputTnseqLibrary = outputTnseqLibrary;
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
        return ((((((((((((((((((((("TnSeqInput"+" [ws=")+ ws)+", readType=")+ readType)+", inputReadLibrary=")+ inputReadLibrary)+", inputGenome=")+ inputGenome)+", sequencedAt=")+ sequencedAt)+", startDate=")+ startDate)+", outputPool=")+ outputPool)+", outputTnseqExperiment=")+ outputTnseqExperiment)+", outputTnseqLibrary=")+ outputTnseqLibrary)+", additionalProperties=")+ additionalProperties)+"]");
    }

}
