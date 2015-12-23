
package us.kbase.kbaseassembly;

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
 * <p>Original spec-file type: AssemblyInput</p>
 * <pre>
 * @optional paired_end_libs single_end_libs references expected_coverage estimated_genome_size dataset_prefix dataset_description
 * </pre>
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("com.googlecode.jsonschema2pojo")
@JsonPropertyOrder({
    "paired_end_libs",
    "single_end_libs",
    "references",
    "expected_coverage",
    "estimated_genome_size",
    "dataset_prefix",
    "dataset_description"
})
public class AssemblyInput {

    @JsonProperty("paired_end_libs")
    private List<PairedEndLibrary> pairedEndLibs;
    @JsonProperty("single_end_libs")
    private List<SingleEndLibrary> singleEndLibs;
    @JsonProperty("references")
    private List<ReferenceAssembly> references;
    @JsonProperty("expected_coverage")
    private Double expectedCoverage;
    @JsonProperty("estimated_genome_size")
    private Long estimatedGenomeSize;
    @JsonProperty("dataset_prefix")
    private String datasetPrefix;
    @JsonProperty("dataset_description")
    private String datasetDescription;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("paired_end_libs")
    public List<PairedEndLibrary> getPairedEndLibs() {
        return pairedEndLibs;
    }

    @JsonProperty("paired_end_libs")
    public void setPairedEndLibs(List<PairedEndLibrary> pairedEndLibs) {
        this.pairedEndLibs = pairedEndLibs;
    }

    public AssemblyInput withPairedEndLibs(List<PairedEndLibrary> pairedEndLibs) {
        this.pairedEndLibs = pairedEndLibs;
        return this;
    }

    @JsonProperty("single_end_libs")
    public List<SingleEndLibrary> getSingleEndLibs() {
        return singleEndLibs;
    }

    @JsonProperty("single_end_libs")
    public void setSingleEndLibs(List<SingleEndLibrary> singleEndLibs) {
        this.singleEndLibs = singleEndLibs;
    }

    public AssemblyInput withSingleEndLibs(List<SingleEndLibrary> singleEndLibs) {
        this.singleEndLibs = singleEndLibs;
        return this;
    }

    @JsonProperty("references")
    public List<ReferenceAssembly> getReferences() {
        return references;
    }

    @JsonProperty("references")
    public void setReferences(List<ReferenceAssembly> references) {
        this.references = references;
    }

    public AssemblyInput withReferences(List<ReferenceAssembly> references) {
        this.references = references;
        return this;
    }

    @JsonProperty("expected_coverage")
    public Double getExpectedCoverage() {
        return expectedCoverage;
    }

    @JsonProperty("expected_coverage")
    public void setExpectedCoverage(Double expectedCoverage) {
        this.expectedCoverage = expectedCoverage;
    }

    public AssemblyInput withExpectedCoverage(Double expectedCoverage) {
        this.expectedCoverage = expectedCoverage;
        return this;
    }

    @JsonProperty("estimated_genome_size")
    public Long getEstimatedGenomeSize() {
        return estimatedGenomeSize;
    }

    @JsonProperty("estimated_genome_size")
    public void setEstimatedGenomeSize(Long estimatedGenomeSize) {
        this.estimatedGenomeSize = estimatedGenomeSize;
    }

    public AssemblyInput withEstimatedGenomeSize(Long estimatedGenomeSize) {
        this.estimatedGenomeSize = estimatedGenomeSize;
        return this;
    }

    @JsonProperty("dataset_prefix")
    public String getDatasetPrefix() {
        return datasetPrefix;
    }

    @JsonProperty("dataset_prefix")
    public void setDatasetPrefix(String datasetPrefix) {
        this.datasetPrefix = datasetPrefix;
    }

    public AssemblyInput withDatasetPrefix(String datasetPrefix) {
        this.datasetPrefix = datasetPrefix;
        return this;
    }

    @JsonProperty("dataset_description")
    public String getDatasetDescription() {
        return datasetDescription;
    }

    @JsonProperty("dataset_description")
    public void setDatasetDescription(String datasetDescription) {
        this.datasetDescription = datasetDescription;
    }

    public AssemblyInput withDatasetDescription(String datasetDescription) {
        this.datasetDescription = datasetDescription;
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
        return ((((((((((((((((("AssemblyInput"+" [pairedEndLibs=")+ pairedEndLibs)+", singleEndLibs=")+ singleEndLibs)+", references=")+ references)+", expectedCoverage=")+ expectedCoverage)+", estimatedGenomeSize=")+ estimatedGenomeSize)+", datasetPrefix=")+ datasetPrefix)+", datasetDescription=")+ datasetDescription)+", additionalProperties=")+ additionalProperties)+"]");
    }

}
