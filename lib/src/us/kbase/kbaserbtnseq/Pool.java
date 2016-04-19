
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
import us.kbase.kbaseassembly.Handle;


/**
 * <p>Original spec-file type: Pool</p>
 * <pre>
 * A Pool is a collection of barcoded strains.  Barcodes, tags, etc should
 * be stored as Deltas in each strain.
 * @optional pool_hit_file pool_unhit_file pool_surprise_file
 * </pre>
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("com.googlecode.jsonschema2pojo")
@JsonPropertyOrder({
    "genome",
    "mapped_reads",
    "pool_file",
    "pool_hit_file",
    "pool_unhit_file",
    "pool_surprise_file",
    "strains",
    "counts"
})
public class Pool {

    @JsonProperty("genome")
    private String genome;
    @JsonProperty("mapped_reads")
    private String mappedReads;
    /**
     * <p>Original spec-file type: Handle</p>
     * <pre>
     * @optional hid file_name type url remote_md5 remote_sha1
     * </pre>
     * 
     */
    @JsonProperty("pool_file")
    private Handle poolFile;
    /**
     * <p>Original spec-file type: Handle</p>
     * <pre>
     * @optional hid file_name type url remote_md5 remote_sha1
     * </pre>
     * 
     */
    @JsonProperty("pool_hit_file")
    private Handle poolHitFile;
    /**
     * <p>Original spec-file type: Handle</p>
     * <pre>
     * @optional hid file_name type url remote_md5 remote_sha1
     * </pre>
     * 
     */
    @JsonProperty("pool_unhit_file")
    private Handle poolUnhitFile;
    /**
     * <p>Original spec-file type: Handle</p>
     * <pre>
     * @optional hid file_name type url remote_md5 remote_sha1
     * </pre>
     * 
     */
    @JsonProperty("pool_surprise_file")
    private Handle poolSurpriseFile;
    @JsonProperty("strains")
    private List<Strain> strains;
    @JsonProperty("counts")
    private List<Long> counts;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("genome")
    public String getGenome() {
        return genome;
    }

    @JsonProperty("genome")
    public void setGenome(String genome) {
        this.genome = genome;
    }

    public Pool withGenome(String genome) {
        this.genome = genome;
        return this;
    }

    @JsonProperty("mapped_reads")
    public String getMappedReads() {
        return mappedReads;
    }

    @JsonProperty("mapped_reads")
    public void setMappedReads(String mappedReads) {
        this.mappedReads = mappedReads;
    }

    public Pool withMappedReads(String mappedReads) {
        this.mappedReads = mappedReads;
        return this;
    }

    /**
     * <p>Original spec-file type: Handle</p>
     * <pre>
     * @optional hid file_name type url remote_md5 remote_sha1
     * </pre>
     * 
     */
    @JsonProperty("pool_file")
    public Handle getPoolFile() {
        return poolFile;
    }

    /**
     * <p>Original spec-file type: Handle</p>
     * <pre>
     * @optional hid file_name type url remote_md5 remote_sha1
     * </pre>
     * 
     */
    @JsonProperty("pool_file")
    public void setPoolFile(Handle poolFile) {
        this.poolFile = poolFile;
    }

    public Pool withPoolFile(Handle poolFile) {
        this.poolFile = poolFile;
        return this;
    }

    /**
     * <p>Original spec-file type: Handle</p>
     * <pre>
     * @optional hid file_name type url remote_md5 remote_sha1
     * </pre>
     * 
     */
    @JsonProperty("pool_hit_file")
    public Handle getPoolHitFile() {
        return poolHitFile;
    }

    /**
     * <p>Original spec-file type: Handle</p>
     * <pre>
     * @optional hid file_name type url remote_md5 remote_sha1
     * </pre>
     * 
     */
    @JsonProperty("pool_hit_file")
    public void setPoolHitFile(Handle poolHitFile) {
        this.poolHitFile = poolHitFile;
    }

    public Pool withPoolHitFile(Handle poolHitFile) {
        this.poolHitFile = poolHitFile;
        return this;
    }

    /**
     * <p>Original spec-file type: Handle</p>
     * <pre>
     * @optional hid file_name type url remote_md5 remote_sha1
     * </pre>
     * 
     */
    @JsonProperty("pool_unhit_file")
    public Handle getPoolUnhitFile() {
        return poolUnhitFile;
    }

    /**
     * <p>Original spec-file type: Handle</p>
     * <pre>
     * @optional hid file_name type url remote_md5 remote_sha1
     * </pre>
     * 
     */
    @JsonProperty("pool_unhit_file")
    public void setPoolUnhitFile(Handle poolUnhitFile) {
        this.poolUnhitFile = poolUnhitFile;
    }

    public Pool withPoolUnhitFile(Handle poolUnhitFile) {
        this.poolUnhitFile = poolUnhitFile;
        return this;
    }

    /**
     * <p>Original spec-file type: Handle</p>
     * <pre>
     * @optional hid file_name type url remote_md5 remote_sha1
     * </pre>
     * 
     */
    @JsonProperty("pool_surprise_file")
    public Handle getPoolSurpriseFile() {
        return poolSurpriseFile;
    }

    /**
     * <p>Original spec-file type: Handle</p>
     * <pre>
     * @optional hid file_name type url remote_md5 remote_sha1
     * </pre>
     * 
     */
    @JsonProperty("pool_surprise_file")
    public void setPoolSurpriseFile(Handle poolSurpriseFile) {
        this.poolSurpriseFile = poolSurpriseFile;
    }

    public Pool withPoolSurpriseFile(Handle poolSurpriseFile) {
        this.poolSurpriseFile = poolSurpriseFile;
        return this;
    }

    @JsonProperty("strains")
    public List<Strain> getStrains() {
        return strains;
    }

    @JsonProperty("strains")
    public void setStrains(List<Strain> strains) {
        this.strains = strains;
    }

    public Pool withStrains(List<Strain> strains) {
        this.strains = strains;
        return this;
    }

    @JsonProperty("counts")
    public List<Long> getCounts() {
        return counts;
    }

    @JsonProperty("counts")
    public void setCounts(List<Long> counts) {
        this.counts = counts;
    }

    public Pool withCounts(List<Long> counts) {
        this.counts = counts;
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
        return ((((((((((((((((((("Pool"+" [genome=")+ genome)+", mappedReads=")+ mappedReads)+", poolFile=")+ poolFile)+", poolHitFile=")+ poolHitFile)+", poolUnhitFile=")+ poolUnhitFile)+", poolSurpriseFile=")+ poolSurpriseFile)+", strains=")+ strains)+", counts=")+ counts)+", additionalProperties=")+ additionalProperties)+"]");
    }

}
