package de.mkienitz.bachelorarbeit.backend4frontend.application.splunk;

import de.mkienitz.bachelorarbeit.backend4frontend.Backend4frontendRestApplication;
import org.eclipse.microprofile.rest.client.RestClientBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.net.MalformedURLException;
import java.net.URI;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;

@ApplicationScoped
public class SplunkClientFactory {

    private static final Logger LOGGER = LoggerFactory.getLogger(SplunkClientFactory.class.getName());

    @Produces
    public SplunkClient createClient() throws MalformedURLException, NoSuchAlgorithmException, KeyManagementException {
        URI splunkHecUri = URI.create(System.getenv(Backend4frontendRestApplication.ENVVAR_SPLUNK_HEC_URL));

        LOGGER.info(String.format("createClient(): env.%s = %s", Backend4frontendRestApplication.ENVVAR_SPLUNK_HEC_URL, splunkHecUri));

        // Create a trust manager that does not validate certificate chains
        TrustManager[] trustAllCerts = new TrustManager[] {
                new X509TrustManager() {
                    public X509Certificate[] getAcceptedIssuers() {
                        return new X509Certificate[0];
                    }
                    public void checkClientTrusted(
                            X509Certificate[] certs, String authType) {
                    }
                    public void checkServerTrusted(
                            X509Certificate[] certs, String authType) {
                    }
                }
        };

        // Install the all-trusting trust manager
        SSLContext sc = SSLContext.getInstance("SSL");
        sc.init(null, trustAllCerts, new java.security.SecureRandom());

        LOGGER.debug("createClient(): creating SplunkClient");

        SplunkClient splunkClient = RestClientBuilder
                .newBuilder()
                .baseUrl(splunkHecUri.toURL())
                .hostnameVerifier((hostname, session) -> true)
                .sslContext(sc)
                .build(SplunkClient.class);

        LOGGER.debug("createClient(): successfully created SplunkClient");

        return splunkClient;
    }
}
