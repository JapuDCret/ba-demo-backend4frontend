package de.mkienitz.bachelorarbeit.backend4frontend.application;

import com.blueconic.browscap.*;
import de.mkienitz.bachelorarbeit.backend4frontend.domain.SplunkInputEntry;
import de.mkienitz.bachelorarbeit.backend4frontend.domain.SplunkOutputEntry;
import org.eclipse.microprofile.rest.client.RestClientBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.net.URL;
import java.security.cert.X509Certificate;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 *
 */
@ApplicationScoped
public class SplunkForwardingService {

    private static Logger log = LoggerFactory.getLogger(SplunkForwardingService.class.getName());

    private final SplunkClient splunkClient;

    private final UserAgentParser parser;

    public SplunkForwardingService() throws Exception {
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
        // HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());

        String splunkHecUrl = System.getenv(Backend4frontendRestApplication.ENVVAR_SPLUNK_HEC_URL);

        log.info(String.format("SplunkForwardingService(): env.%s = %s", Backend4frontendRestApplication.ENVVAR_SPLUNK_HEC_URL, splunkHecUrl));

        log.debug("SplunkForwardingService(): creating SplunkClient");

        URL splunkHecUrl2 = new URL(splunkHecUrl);

        this.splunkClient = RestClientBuilder
                .newBuilder()
                .baseUrl(splunkHecUrl2)
                .hostnameVerifier((hostname, session) -> true)
                .sslContext(sc)
                .build(SplunkClient.class);

        log.debug("SplunkForwardingService(): loading parser");

        try {
            this.parser =
                    new UserAgentService().loadParser(Arrays.asList(
                            BrowsCapField.BROWSER,
                            BrowsCapField.BROWSER_TYPE,
                            BrowsCapField.BROWSER_MAJOR_VERSION,
                            BrowsCapField.BROWSER_MINOR_VERSION,
                            BrowsCapField.DEVICE_TYPE,
                            BrowsCapField.DEVICE_NAME,
                            BrowsCapField.DEVICE_MAKER,
                            BrowsCapField.PLATFORM,
                            BrowsCapField.PLATFORM_VERSION,
                            BrowsCapField.PLATFORM_MAKER,
                            BrowsCapField.RENDERING_ENGINE_VERSION,
                            BrowsCapField.RENDERING_ENGINE_NAME,
                            BrowsCapField.RENDERING_ENGINE_MAKER,
                            BrowsCapField.IS_MOBILE_DEVICE,
                            BrowsCapField.IS_TABLET,
                            BrowsCapField.IS_CRAWLER,
                            BrowsCapField.IS_FAKE,
                            BrowsCapField.IS_ANONYMIZED,
                            BrowsCapField.IS_MODIFIED
                    ));
        } catch(IOException | ParseException e) {
            log.error("SplunkForwardingService(): could not load parser, e = ", e);

            throw e;
        }

        log.debug("SplunkForwardingService(): successfully loaded parser");
    }

    public Response forwardLog(
            SplunkInputEntry inputEntry,
            String ip,
            String userAgent
    ) {
        log.info("forwardLog(): inputEntry = " + inputEntry);

        SplunkOutputEntry outputEntry = new SplunkOutputEntry();
        outputEntry.setSource(inputEntry.getSource());
        outputEntry.setSourcetype(inputEntry.getSourcetype());
        outputEntry.setEvent(inputEntry.getEvent());

        long currentTimeInSeconds = System.currentTimeMillis() / 1000;
        long currentTimeModuloMilliseconds = System.currentTimeMillis() % 1000;
        outputEntry.setTime(String.format("%d.%d", currentTimeInSeconds, currentTimeModuloMilliseconds));

        outputEntry.setHost(ip);

        Map<String, Object> event = outputEntry.getEvent();

        event.put("User-Agent", userAgent);

        if(userAgent != "n/a") {
            Map<String, Object> capabilities = this.extractBrowserCapabilities(userAgent);
            event.put("capabilities", capabilities);
        }

        log.debug("forwardLog(): outputEntry = " + outputEntry);

        log.info("forwardLog(): forwarding to Splunk");

        Response splunkResponse = splunkClient.postEvent(outputEntry);

        log.info("forwardLog(): splunkResponse.status = " + splunkResponse.getStatus());

        return splunkResponse;
    }

    private Map<String, Object> extractBrowserCapabilities(String userAgent) {
        final Capabilities capabilities = this.parser.parse(userAgent);

        Map<String, Object> capabilityMap = new HashMap<>();

        String browser = capabilities.getValue(BrowsCapField.BROWSER);
        String browserType = capabilities.getValue(BrowsCapField.BROWSER_TYPE);
        String browserMajorVersion = capabilities.getValue(BrowsCapField.BROWSER_MAJOR_VERSION);
        String browserMinorVersion = capabilities.getValue(BrowsCapField.BROWSER_MINOR_VERSION);
        String deviceType = capabilities.getValue(BrowsCapField.DEVICE_TYPE);
        String deviceName = capabilities.getValue(BrowsCapField.DEVICE_NAME);
        String deviceMaker = capabilities.getValue(BrowsCapField.DEVICE_MAKER);
        String platform = capabilities.getValue(BrowsCapField.PLATFORM);
        String platformVersion = capabilities.getValue(BrowsCapField.PLATFORM_VERSION);
        String platformMaker = capabilities.getValue(BrowsCapField.PLATFORM_MAKER);
        String renderingEngineName = capabilities.getValue(BrowsCapField.RENDERING_ENGINE_NAME);
        String renderingEngineVersion = capabilities.getValue(BrowsCapField.RENDERING_ENGINE_VERSION);
        String renderingEngineMaker = capabilities.getValue(BrowsCapField.RENDERING_ENGINE_MAKER);
        String isMobile = capabilities.getValue(BrowsCapField.IS_MOBILE_DEVICE);
        String isTablet = capabilities.getValue(BrowsCapField.IS_TABLET);
        String isCrawler = capabilities.getValue(BrowsCapField.IS_CRAWLER);
        String isFake = capabilities.getValue(BrowsCapField.IS_FAKE);
        String isAnonymized = capabilities.getValue(BrowsCapField.IS_ANONYMIZED);
        String isModified = capabilities.getValue(BrowsCapField.IS_MODIFIED);

        capabilityMap.put("browser", browser);
        capabilityMap.put("browserType", browserType);
        capabilityMap.put("browserMajorVersion", browserMajorVersion);
        capabilityMap.put("browserMinorVersion", browserMinorVersion);
        capabilityMap.put("deviceType", deviceType);
        capabilityMap.put("deviceName", deviceName);
        capabilityMap.put("deviceMaker", deviceMaker);
        capabilityMap.put("platform", platform);
        capabilityMap.put("platformVersion", platformVersion);
        capabilityMap.put("platformMaker", platformMaker);
        capabilityMap.put("renderingEngineName", renderingEngineName);
        capabilityMap.put("renderingEngineVersion", renderingEngineVersion);
        capabilityMap.put("renderingEngineMaker", renderingEngineMaker);
        capabilityMap.put("isMobile", isMobile);
        capabilityMap.put("isTablet", isTablet);
        capabilityMap.put("isCrawler", isCrawler);
        capabilityMap.put("isFake", isFake);
        capabilityMap.put("isAnonymized", isAnonymized);
        capabilityMap.put("isModified", isModified);

        return capabilityMap;
    }
}
