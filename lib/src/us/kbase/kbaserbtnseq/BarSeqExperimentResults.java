
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
import us.kbase.common.service.Tuple2;
import us.kbase.common.service.Tuple5;
import us.kbase.kbasefeaturevalues.FloatMatrix2D;


/**
 * <p>Original spec-file type: BarSeqExperimentResults</p>
 * <pre>
 * row names (usually genes) to feature_index, i.e. reverse of 'feature_index_to_id'
 * </pre>
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("com.googlecode.jsonschema2pojo")
@JsonPropertyOrder({
    "genome",
    "feature_index_to_id",
    "experiments",
    "features_by_experiments",
    "col_to_index",
    "row_to_index"
})
public class BarSeqExperimentResults {

    @JsonProperty("genome")
    private java.lang.String genome;
    @JsonProperty("feature_index_to_id")
    private Map<Long, String> featureIndexToId;
    @JsonProperty("experiments")
    private List<Tuple2 <BarSeqExperiment, List<Tuple5 <Long, Long, Long, Long, Double>>>> experiments;
    /**
     * <p>Original spec-file type: FloatMatrix2D</p>
     * <pre>
     * A simple 2D matrix of floating point numbers with labels/ids for rows and
     * columns.  The matrix is stored as a list of lists, with the outer list
     * containing rows, and the inner lists containing values for each column of
     * that row.  Row/Col ids should be unique.
     * row_ids - unique ids for rows.
     * col_ids - unique ids for columns.
     * values - two dimensional array indexed as: values[row][col]
     * @metadata ws length(row_ids) as n_rows
     * @metadata ws length(col_ids) as n_cols
     * </pre>
     * 
     */
    @JsonProperty("features_by_experiments")
    private FloatMatrix2D featuresByExperiments;
    @JsonProperty("col_to_index")
    private Map<String, Long> colToIndex;
    @JsonProperty("row_to_index")
    private Map<String, Long> rowToIndex;
    private Map<java.lang.String, Object> additionalProperties = new HashMap<java.lang.String, Object>();

    @JsonProperty("genome")
    public java.lang.String getGenome() {
        return genome;
    }

    @JsonProperty("genome")
    public void setGenome(java.lang.String genome) {
        this.genome = genome;
    }

    public BarSeqExperimentResults withGenome(java.lang.String genome) {
        this.genome = genome;
        return this;
    }

    @JsonProperty("feature_index_to_id")
    public Map<Long, String> getFeatureIndexToId() {
        return featureIndexToId;
    }

    @JsonProperty("feature_index_to_id")
    public void setFeatureIndexToId(Map<Long, String> featureIndexToId) {
        this.featureIndexToId = featureIndexToId;
    }

    public BarSeqExperimentResults withFeatureIndexToId(Map<Long, String> featureIndexToId) {
        this.featureIndexToId = featureIndexToId;
        return this;
    }

    @JsonProperty("experiments")
    public List<Tuple2 <BarSeqExperiment, List<Tuple5 <Long, Long, Long, Long, Double>>>> getExperiments() {
        return experiments;
    }

    @JsonProperty("experiments")
    public void setExperiments(List<Tuple2 <BarSeqExperiment, List<Tuple5 <Long, Long, Long, Long, Double>>>> experiments) {
        this.experiments = experiments;
    }

    public BarSeqExperimentResults withExperiments(List<Tuple2 <BarSeqExperiment, List<Tuple5 <Long, Long, Long, Long, Double>>>> experiments) {
        this.experiments = experiments;
        return this;
    }

    /**
     * <p>Original spec-file type: FloatMatrix2D</p>
     * <pre>
     * A simple 2D matrix of floating point numbers with labels/ids for rows and
     * columns.  The matrix is stored as a list of lists, with the outer list
     * containing rows, and the inner lists containing values for each column of
     * that row.  Row/Col ids should be unique.
     * row_ids - unique ids for rows.
     * col_ids - unique ids for columns.
     * values - two dimensional array indexed as: values[row][col]
     * @metadata ws length(row_ids) as n_rows
     * @metadata ws length(col_ids) as n_cols
     * </pre>
     * 
     */
    @JsonProperty("features_by_experiments")
    public FloatMatrix2D getFeaturesByExperiments() {
        return featuresByExperiments;
    }

    /**
     * <p>Original spec-file type: FloatMatrix2D</p>
     * <pre>
     * A simple 2D matrix of floating point numbers with labels/ids for rows and
     * columns.  The matrix is stored as a list of lists, with the outer list
     * containing rows, and the inner lists containing values for each column of
     * that row.  Row/Col ids should be unique.
     * row_ids - unique ids for rows.
     * col_ids - unique ids for columns.
     * values - two dimensional array indexed as: values[row][col]
     * @metadata ws length(row_ids) as n_rows
     * @metadata ws length(col_ids) as n_cols
     * </pre>
     * 
     */
    @JsonProperty("features_by_experiments")
    public void setFeaturesByExperiments(FloatMatrix2D featuresByExperiments) {
        this.featuresByExperiments = featuresByExperiments;
    }

    public BarSeqExperimentResults withFeaturesByExperiments(FloatMatrix2D featuresByExperiments) {
        this.featuresByExperiments = featuresByExperiments;
        return this;
    }

    @JsonProperty("col_to_index")
    public Map<String, Long> getColToIndex() {
        return colToIndex;
    }

    @JsonProperty("col_to_index")
    public void setColToIndex(Map<String, Long> colToIndex) {
        this.colToIndex = colToIndex;
    }

    public BarSeqExperimentResults withColToIndex(Map<String, Long> colToIndex) {
        this.colToIndex = colToIndex;
        return this;
    }

    @JsonProperty("row_to_index")
    public Map<String, Long> getRowToIndex() {
        return rowToIndex;
    }

    @JsonProperty("row_to_index")
    public void setRowToIndex(Map<String, Long> rowToIndex) {
        this.rowToIndex = rowToIndex;
    }

    public BarSeqExperimentResults withRowToIndex(Map<String, Long> rowToIndex) {
        this.rowToIndex = rowToIndex;
        return this;
    }

    @JsonAnyGetter
    public Map<java.lang.String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperties(java.lang.String name, Object value) {
        this.additionalProperties.put(name, value);
    }

    @Override
    public java.lang.String toString() {
        return ((((((((((((((("BarSeqExperimentResults"+" [genome=")+ genome)+", featureIndexToId=")+ featureIndexToId)+", experiments=")+ experiments)+", featuresByExperiments=")+ featuresByExperiments)+", colToIndex=")+ colToIndex)+", rowToIndex=")+ rowToIndex)+", additionalProperties=")+ additionalProperties)+"]");
    }

}
