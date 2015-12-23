
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
 * <p>Original spec-file type: SingleEndLibrary</p>
 * 
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("com.googlecode.jsonschema2pojo")
@JsonPropertyOrder({
    "handle"
})
public class SingleEndLibrary {

    /**
     * <p>Original spec-file type: Handle</p>
     * <pre>
     * @optional hid file_name type url remote_md5 remote_sha1
     * </pre>
     * 
     */
    @JsonProperty("handle")
    private Handle handle;
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

    public SingleEndLibrary withHandle(Handle handle) {
        this.handle = handle;
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
        return ((((("SingleEndLibrary"+" [handle=")+ handle)+", additionalProperties=")+ additionalProperties)+"]");
    }

}
