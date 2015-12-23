
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
 * <p>Original spec-file type: ReferenceAssembly</p>
 * <pre>
 * @optional reference_name
 * </pre>
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("com.googlecode.jsonschema2pojo")
@JsonPropertyOrder({
    "handle",
    "reference_name"
})
public class ReferenceAssembly {

    /**
     * <p>Original spec-file type: Handle</p>
     * <pre>
     * @optional hid file_name type url remote_md5 remote_sha1
     * </pre>
     * 
     */
    @JsonProperty("handle")
    private Handle handle;
    @JsonProperty("reference_name")
    private String referenceName;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * <p>Original spec-file type: Handle</p>
     * <pre>
     * @optional hid file_name type url remote_md5 remote_sha1
     * </pre>
     * 
     */
    @JsonProperty("handle")
    public Handle getHandle() {
        return handle;
    }

    /**
     * <p>Original spec-file type: Handle</p>
     * <pre>
     * @optional hid file_name type url remote_md5 remote_sha1
     * </pre>
     * 
     */
    @JsonProperty("handle")
    public void setHandle(Handle handle) {
        this.handle = handle;
    }

    public ReferenceAssembly withHandle(Handle handle) {
        this.handle = handle;
        return this;
    }

    @JsonProperty("reference_name")
    public String getReferenceName() {
        return referenceName;
    }

    @JsonProperty("reference_name")
    public void setReferenceName(String referenceName) {
        this.referenceName = referenceName;
    }

    public ReferenceAssembly withReferenceName(String referenceName) {
        this.referenceName = referenceName;
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
        return ((((((("ReferenceAssembly"+" [handle=")+ handle)+", referenceName=")+ referenceName)+", additionalProperties=")+ additionalProperties)+"]");
    }

}
