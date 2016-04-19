package rbtnseq;

import com.fasterxml.jackson.core.type.TypeReference;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import us.kbase.auth.AuthToken;
import us.kbase.common.service.JsonClientCaller;
import us.kbase.common.service.JsonClientException;
import us.kbase.common.service.RpcContext;
import us.kbase.common.service.UnauthorizedException;

/**
 * <p>Original spec-file module name: RBTnSeq</p>
 * <pre>
 * A KBase module: RBTnSeq
 * This module wraps Morgan Price's RBTnSeq pipeline,
 * available from https://bitbucket.org/berkeleylab/feba
 * </pre>
 */
public class RBTnSeqClient {
    private JsonClientCaller caller;


    /** Constructs a client with a custom URL and no user credentials.
     * @param url the URL of the service.
     */
    public RBTnSeqClient(URL url) {
        caller = new JsonClientCaller(url);
    }
    /** Constructs a client with a custom URL.
     * @param url the URL of the service.
     * @param token the user's authorization token.
     * @throws UnauthorizedException if the token is not valid.
     * @throws IOException if an IOException occurs when checking the token's
     * validity.
     */
    public RBTnSeqClient(URL url, AuthToken token) throws UnauthorizedException, IOException {
        caller = new JsonClientCaller(url, token);
    }

    /** Constructs a client with a custom URL.
     * @param url the URL of the service.
     * @param user the user name.
     * @param password the password for the user name.
     * @throws UnauthorizedException if the credentials are not valid.
     * @throws IOException if an IOException occurs when checking the user's
     * credentials.
     */
    public RBTnSeqClient(URL url, String user, String password) throws UnauthorizedException, IOException {
        caller = new JsonClientCaller(url, user, password);
    }

    /** Get the token this client uses to communicate with the server.
     * @return the authorization token.
     */
    public AuthToken getToken() {
        return caller.getToken();
    }

    /** Get the URL of the service with which this client communicates.
     * @return the service URL.
     */
    public URL getURL() {
        return caller.getURL();
    }

    /** Set the timeout between establishing a connection to a server and
     * receiving a response. A value of zero or null implies no timeout.
     * @param milliseconds the milliseconds to wait before timing out when
     * attempting to read from a server.
     */
    public void setConnectionReadTimeOut(Integer milliseconds) {
        this.caller.setConnectionReadTimeOut(milliseconds);
    }

    /** Check if this client allows insecure http (vs https) connections.
     * @return true if insecure connections are allowed.
     */
    public boolean isInsecureHttpConnectionAllowed() {
        return caller.isInsecureHttpConnectionAllowed();
    }

    /** Deprecated. Use isInsecureHttpConnectionAllowed().
     * @deprecated
     */
    public boolean isAuthAllowedForHttp() {
        return caller.isAuthAllowedForHttp();
    }

    /** Set whether insecure http (vs https) connections should be allowed by
     * this client.
     * @param allowed true to allow insecure connections. Default false
     */
    public void setIsInsecureHttpConnectionAllowed(boolean allowed) {
        caller.setInsecureHttpConnectionAllowed(allowed);
    }

    /** Deprecated. Use setIsInsecureHttpConnectionAllowed().
     * @deprecated
     */
    public void setAuthAllowedForHttp(boolean isAuthAllowedForHttp) {
        caller.setAuthAllowedForHttp(isAuthAllowedForHttp);
    }

    /** Set whether all SSL certificates, including self-signed certificates,
     * should be trusted.
     * @param trustAll true to trust all certificates. Default false.
     */
    public void setAllSSLCertificatesTrusted(final boolean trustAll) {
        caller.setAllSSLCertificatesTrusted(trustAll);
    }
    
    /** Check if this client trusts all SSL certificates, including
     * self-signed certificates.
     * @return true if all certificates are trusted.
     */
    public boolean isAllSSLCertificatesTrusted() {
        return caller.isAllSSLCertificatesTrusted();
    }
    /** Sets streaming mode on. In this case, the data will be streamed to
     * the server in chunks as it is read from disk rather than buffered in
     * memory. Many servers are not compatible with this feature.
     * @param streamRequest true to set streaming mode on, false otherwise.
     */
    public void setStreamingModeOn(boolean streamRequest) {
        caller.setStreamingModeOn(streamRequest);
    }

    /** Returns true if streaming mode is on.
     * @return true if streaming mode is on.
     */
    public boolean isStreamingModeOn() {
        return caller.isStreamingModeOn();
    }

    public void _setFileForNextRpcResponse(File f) {
        caller.setFileForNextRpcResponse(f);
    }

    /**
     * <p>Original spec-file function name: runTnSeq</p>
     * <pre>
     * runs TnSeq part of pipeline
     * </pre>
     * @param   input   instance of type {@link rbtnseq.TnSeqInput TnSeqInput}
     * @return   parameter "output" of type {@link rbtnseq.TnSeqOutput TnSeqOutput}
     * @throws IOException if an IO exception occurs
     * @throws JsonClientException if a JSON RPC exception occurs
     */
    public TnSeqOutput runTnSeq(TnSeqInput input, RpcContext... jsonRpcContext) throws IOException, JsonClientException {
        List<Object> args = new ArrayList<Object>();
        args.add(input);
        TypeReference<List<TnSeqOutput>> retType = new TypeReference<List<TnSeqOutput>>() {};
        List<TnSeqOutput> res = caller.jsonrpcCall("RBTnSeq.runTnSeq", args, retType, true, true, jsonRpcContext);
        return res.get(0);
    }

    /**
     * <p>Original spec-file function name: makeTnSeqPool</p>
     * <pre>
     * runs TnSeq part of pipeline
     * </pre>
     * @param   input   instance of type {@link rbtnseq.TnSeqPoolInput TnSeqPoolInput}
     * @return   parameter "output" of type {@link rbtnseq.TnSeqOutput TnSeqOutput}
     * @throws IOException if an IO exception occurs
     * @throws JsonClientException if a JSON RPC exception occurs
     */
    public TnSeqOutput makeTnSeqPool(TnSeqPoolInput input, RpcContext... jsonRpcContext) throws IOException, JsonClientException {
        List<Object> args = new ArrayList<Object>();
        args.add(input);
        TypeReference<List<TnSeqOutput>> retType = new TypeReference<List<TnSeqOutput>>() {};
        List<TnSeqOutput> res = caller.jsonrpcCall("RBTnSeq.makeTnSeqPool", args, retType, true, true, jsonRpcContext);
        return res.get(0);
    }

    /**
     * <p>Original spec-file function name: getEssentialGenes</p>
     * <pre>
     * gets list of essential (un-hit) genes from a Pool
     * </pre>
     * @param   input   instance of type {@link rbtnseq.EssentialGenesInput EssentialGenesInput}
     * @return   parameter "output" of type {@link rbtnseq.EssentialGenesOutput EssentialGenesOutput}
     * @throws IOException if an IO exception occurs
     * @throws JsonClientException if a JSON RPC exception occurs
     */
    public EssentialGenesOutput getEssentialGenes(EssentialGenesInput input, RpcContext... jsonRpcContext) throws IOException, JsonClientException {
        List<Object> args = new ArrayList<Object>();
        args.add(input);
        TypeReference<List<EssentialGenesOutput>> retType = new TypeReference<List<EssentialGenesOutput>>() {};
        List<EssentialGenesOutput> res = caller.jsonrpcCall("RBTnSeq.getEssentialGenes", args, retType, true, true, jsonRpcContext);
        return res.get(0);
    }

    /**
     * <p>Original spec-file function name: version</p>
     * <pre>
     * returns version number of service
     * </pre>
     * @return   parameter "version" of String
     * @throws IOException if an IO exception occurs
     * @throws JsonClientException if a JSON RPC exception occurs
     */
    public String version(RpcContext... jsonRpcContext) throws IOException, JsonClientException {
        List<Object> args = new ArrayList<Object>();
        TypeReference<List<String>> retType = new TypeReference<List<String>>() {};
        List<String> res = caller.jsonrpcCall("RBTnSeq.version", args, retType, true, false, jsonRpcContext);
        return res.get(0);
    }
}
