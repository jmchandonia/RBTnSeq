
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
 * <p>Original spec-file type: AssemblyReport</p>
 * <pre>
 * @optional log server_url user job_id
 * </pre>
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("com.googlecode.jsonschema2pojo")
@JsonPropertyOrder({
    "report",
    "log",
    "server_url",
    "user",
    "job_id"
})
public class AssemblyReport {

    @JsonProperty("report")
    private String report;
    @JsonProperty("log")
    private String log;
    @JsonProperty("server_url")
    private String serverUrl;
    @JsonProperty("user")
    private String user;
    @JsonProperty("job_id")
    private String jobId;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("report")
    public String getReport() {
        return report;
    }

    @JsonProperty("report")
    public void setReport(String report) {
        this.report = report;
    }

    public AssemblyReport withReport(String report) {
        this.report = report;
        return this;
    }

    @JsonProperty("log")
    public String getLog() {
        return log;
    }

    @JsonProperty("log")
    public void setLog(String log) {
        this.log = log;
    }

    public AssemblyReport withLog(String log) {
        this.log = log;
        return this;
    }

    @JsonProperty("server_url")
    public String getServerUrl() {
        return serverUrl;
    }

    @JsonProperty("server_url")
    public void setServerUrl(String serverUrl) {
        this.serverUrl = serverUrl;
    }

    public AssemblyReport withServerUrl(String serverUrl) {
        this.serverUrl = serverUrl;
        return this;
    }

    @JsonProperty("user")
    public String getUser() {
        return user;
    }

    @JsonProperty("user")
    public void setUser(String user) {
        this.user = user;
    }

    public AssemblyReport withUser(String user) {
        this.user = user;
        return this;
    }

    @JsonProperty("job_id")
    public String getJobId() {
        return jobId;
    }

    @JsonProperty("job_id")
    public void setJobId(String jobId) {
        this.jobId = jobId;
    }

    public AssemblyReport withJobId(String jobId) {
        this.jobId = jobId;
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
        return ((((((((((((("AssemblyReport"+" [report=")+ report)+", log=")+ log)+", serverUrl=")+ serverUrl)+", user=")+ user)+", jobId=")+ jobId)+", additionalProperties=")+ additionalProperties)+"]");
    }

}
