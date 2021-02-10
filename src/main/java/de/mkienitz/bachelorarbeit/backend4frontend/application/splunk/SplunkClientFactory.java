package de.mkienitz.bachelorarbeit.backend4frontend.application.splunk;

import de.mkienitz.bachelorarbeit.backend4frontend.application.Backend4frontendRestApplication;
import org.eclipse.microprofile.rest.client.RestClientBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.inject.Produces;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;

public class SplunkClientFactory {

    private static final Logger log = LoggerFactory.getLogger(SplunkClientFactory.class.getName());

    @Produces
    public SplunkClient createClient() throws MalformedURLException, NoSuchAlgorithmException, KeyManagementException {
        String splunkHecUrl = System.getenv(Backend4frontendRestApplication.ENVVAR_SPLUNK_HEC_URL);

        log.info(String.format("createClient(): env.%s = %s", Backend4frontendRestApplication.ENVVAR_SPLUNK_HEC_URL, splunkHecUrl));

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

        log.debug("createClient(): creating SplunkClient");

        URL splunkHecUrl2 = new URL(splunkHecUrl);

        SplunkClient splunkClient = RestClientBuilder
                .newBuilder()
                .baseUrl(splunkHecUrl2)
                .hostnameVerifier((hostname, session) -> true)
                .sslContext(sc)
                .build(SplunkClient.class);

        log.debug("createClient(): successfully created SplunkClient");

        return splunkClient;
    }
}
