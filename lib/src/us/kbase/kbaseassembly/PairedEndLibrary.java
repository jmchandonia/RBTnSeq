
package us.kbase.kbaseassembly;

import java.util.HashMap;
import java.util.Map;
import javax.annotation.Generated;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;


/**
 * <p>Original spec-file type: PairedEndLibrary</p>
 * <pre>
 * @optional handle_2 insert_size_mean insert_size_std_dev interleaved read_orientation_outward
 * </pre>
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("com.googlecode.jsonschema2pojo")
@JsonPropertyOrder({
    "handle_1",
    "handle_2",
    "insert_size_mean",
    "insert_size_std_dev",
    "interleaved",
    "read_orientation_outward"
})
public class PairedEndLibrary {

    /**
     * <p>Original spec-file type: Handle</p>
     * <pre>
     * @optional hid file_name type url remote_md5 remote_sha1
     * </pre>
     * 
     */
    @JsonProperty("handle_1")
    private Handle handle1;
    /**
     * <p>Original spec-file type: Handle</p>
     * <pre>
     * @optional hid file_name type url remote_md5 remote_sha1
     * </pre>
     * 
     */
    @JsonProperty("handle_2")
    private Handle handle2;
    @JsonProperty("insert_size_mean")
    private Double insertSizeMean;
    @JsonProperty("insert_size_std_dev")
    private Double insertSizeStdDev;
    @JsonProperty("interleaved")
    private Long interleaved;
    @JsonProperty("read_orientation_outward")
    private Long readOrientationOutward;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * <p>Original spec-file type: Handle</p>
     * <pre>
     * @optional hid file_name type url remote_md5 remote_sha1
     * </pre>
     * 
     */
    @JsonProperty("handle_1")
    public Handle getHandle1() {
        return handle1;
    }

    /**
     * <p>Original spec-file type: Handle</p>
     * <pre>
     * @optional hid file_name type url remote_md5 remote_sha1
     * </pre>
     * 
     */
    @JsonProperty("handle_1")
    public void setHandle1(Handle handle1) {
        this.handle1 = handle1;
    }

    public PairedEndLibrary withHandle1(Handle handle1) {
        this.handle1 = handle1;
        return this;
    }

    /**
     * <p>Original spec-file type: Handle</p>
     * <pre>
     * @optional hid file_name type url remote_md5 remote_sha1
     * </pre>
     * 
     */
    @JsonProperty("handle_2")
    public Handle getHandle2() {
        return handle2;
    }

    /**
     * <p>Original spec-file type: Handle</p>
     * <pre>
     * @optional hid file_name type url remote_md5 remote_sha1
     * </pre>
     * 
     */
    @JsonProperty("handle_2")
    public void setHandle2(Handle handle2) {
        this.handle2 = handle2;
    }

    public PairedEndLibrary withHandle2(Handle handle2) {
        this.handle2 = handle2;
        return this;
    }

    @JsonProperty("insert_size_mean")
    public Double getInsertSizeMean() {
        return insertSizeMean;
    }

    @JsonProperty("insert_size_mean")
    public void setInsertSizeMean(Double insertSizeMean) {
        this.insertSizeMean = insertSizeMean;
    }

    public PairedEndLibrary withInsertSizeMean(Double insertSizeMean) {
        this.insertSizeMean = insertSizeMean;
        return this;
    }

    @JsonProperty("insert_size_std_dev")
    public Double getInsertSizeStdDev() {
        return insertSizeStdDev;
    }

    @JsonProperty("insert_size_std_dev")
    public void setInsertSizeStdDev(Double insertSizeStdDev) {
        this.insertSizeStdDev = insertSizeStdDev;
    }

    public PairedEndLibrary withInsertSizeStdDev(Double insertSizeStdDev) {
        this.insertSizeStdDev = insertSizeStdDev;
        return this;
    }

    @JsonProperty("interleaved")
    public Long getInterleaved() {
        return interleaved;
    }

    @JsonProperty("interleaved")
    public void setInterleaved(Long interleaved) {
        this.interleaved = interleaved;
    }

    public PairedEndLibrary withInterleaved(Long interleaved) {
        this.interleaved = interleaved;
        return this;
    }

    @JsonProperty("read_orientation_outward")
    public Long getReadOrientationOutward() {
        return readOrientationOutward;
    }

    @JsonProperty("read_orientation_outward")
    public void setReadOrientationOutward(Long readOrientationOutward) {
        this.readOrientationOutward = readOrientationOutward;
    }

    public PairedEndLibrary withReadOrientationOutward(Long readOrientationOutward) {
        this.readOrientationOutward = readOrientationOutward;
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
        return ((((((((((((((("PairedEndLibrary"+" [handle1=")+ handle1)+", handle2=")+ handle2)+", insertSizeMean=")+ insertSizeMean)+", insertSizeStdDev=")+ insertSizeStdDev)+", interleaved=")+ interleaved)+", readOrientationOutward=")+ readOrientationOutward)+", additionalProperties=")+ additionalProperties)+"]");
    }

}
