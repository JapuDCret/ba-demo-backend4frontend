package de.mkienitz.bachelorarbeit.backend4frontend.application;

import de.mkienitz.bachelorarbeit.backend4frontend.domain.SplunkApi;
import de.mkienitz.bachelorarbeit.backend4frontend.domain.SplunkOutputEntry;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.rest.client.RestClientBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URL;
import java.security.cert.X509Certificate;
import java.util.Map;

@Path("log")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class SplunkForwardingResource {

    private static Logger log = LoggerFactory.getLogger(SplunkForwardingResource.class.getName());

    private final SplunkApi splunkApi;

    public SplunkForwardingResource() throws Exception {
        // Create a trust manager that does not validate certificate chains
        TrustManager[] trustAllCerts = new TrustManager[] {
                new X509TrustManager() {
                    public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                        return new X509Certificate[0];
                    }
                    public void checkClientTrusted(
                            java.security.cert.X509Certificate[] certs, String authType) {
                    }
                    public void checkServerTrusted(
                            java.security.cert.X509Certificate[] certs, String authType) {
                    }
                }
        };

        // Install the all-trusting trust manager
        SSLContext sc = SSLContext.getInstance("SSL");
        sc.init(null, trustAllCerts, new java.security.SecureRandom());
        // HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());

        String splunkHecUrl = System.getenv(Backend4frontendRestApplication.ENVVAR_SPLUNK_HEC_URL);

        log.info(String.format("AddressValidationForwardingResource(): env.%s = %s", Backend4frontendRestApplication.ENVVAR_SPLUNK_HEC_URL, splunkHecUrl));

        log.debug("AddressValidationForwardingResource(): creating AddressValidationServiceClient");

        URL splunkHecUrl2 = new URL(splunkHecUrl);

        this.splunkApi = RestClientBuilder
                .newBuilder()
                .baseUrl(splunkHecUrl2)
                .hostnameVerifier((hostname, session) -> true)
                .sslContext(sc)
                .build(SplunkApi.class);
    }

    @POST
    @Operation(description = "Accepts a new log entry and forwards it to Splunk")
    public Response forwardLog(
            @Context HttpServletRequest servletRequest,
            @Context HttpHeaders headers,
            @NotNull SplunkOutputEntry inputEntry
    ) {
        log.info("forwardLog(): inputEntry = " + inputEntry);

        SplunkOutputEntry outputEntry = new SplunkOutputEntry();
        outputEntry.setSource(inputEntry.getSource());
        outputEntry.setSourcetype(inputEntry.getSourcetype());
        outputEntry.setEvent(inputEntry.getEvent());

        long currentTimeInSeconds = System.currentTimeMillis() / 1000;
        long currentTimeModuloMilliseconds = System.currentTimeMillis() % 1000;
        outputEntry.setTime(String.format("%d.%d", currentTimeInSeconds, currentTimeModuloMilliseconds));

        outputEntry.setHost(getIP(servletRequest));

        Map<String, Object> event = outputEntry.getEvent();
        event.put("User-Agent", getUserAgent(servletRequest));

        log.info("forwardLog(): outputEntry = " + outputEntry);

        Response response = splunkApi.postEvent(outputEntry);

        log.info("forwardLog(): response.status = " + response.getStatus());

        return Response.status(Response.Status.CREATED).build();
    }

    private static String getIP(HttpServletRequest request) {
        return request.getRemoteAddr();
    }

    private static String getUserAgent(HttpServletRequest request) {
        String userAgent = "n/a";

        try {
            userAgent = request.getHeader("User-Agent");
        } catch (Exception ignored){}

        return userAgent;
    }
}
