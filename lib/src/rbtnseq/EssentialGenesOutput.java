
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
 * <p>Original spec-file type: EssentialGenesOutput</p>
 * <pre>
 * getEssentialGenes outputs a report and a FeatureSet
 * </pre>
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("com.googlecode.jsonschema2pojo")
@JsonPropertyOrder({
    "output_feature_set",
    "report_name",
    "report_ref"
})
public class EssentialGenesOutput {

    @JsonProperty("output_feature_set")
    private String outputFeatureSet;
    @JsonProperty("report_name")
    private String reportName;
    @JsonProperty("report_ref")
    private String reportRef;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("output_feature_set")
    public String getOutputFeatureSet() {
        return outputFeatureSet;
    }

    @JsonProperty("output_feature_set")
    public void setOutputFeatureSet(String outputFeatureSet) {
        this.outputFeatureSet = outputFeatureSet;
    }

    public EssentialGenesOutput withOutputFeatureSet(String outputFeatureSet) {
        this.outputFeatureSet = outputFeatureSet;
        return this;
    }

    @JsonProperty("report_name")
    public String getReportName() {
        return reportName;
    }

    @JsonProperty("report_name")
    public void setReportName(String reportName) {
        this.reportName = reportName;
    }

    public EssentialGenesOutput withReportName(String reportName) {
        this.reportName = reportName;
        return this;
    }

    @JsonProperty("report_ref")
    public String getReportRef() {
        return reportRef;
    }

    @JsonProperty("report_ref")
    public void setReportRef(String reportRef) {
        this.reportRef = reportRef;
    }

    public EssentialGenesOutput withReportRef(String reportRef) {
        this.reportRef = reportRef;
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
        return ((((((((("EssentialGenesOutput"+" [outputFeatureSet=")+ outputFeatureSet)+", reportName=")+ reportName)+", reportRef=")+ reportRef)+", additionalProperties=")+ additionalProperties)+"]");
    }

}
