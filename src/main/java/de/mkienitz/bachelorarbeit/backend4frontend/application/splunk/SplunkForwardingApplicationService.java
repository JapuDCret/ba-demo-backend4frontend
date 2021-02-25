package de.mkienitz.bachelorarbeit.backend4frontend.application.splunk;

import com.blueconic.browscap.*;
import de.mkienitz.bachelorarbeit.backend4frontend.domain.SplunkInputEntry;
import de.mkienitz.bachelorarbeit.backend4frontend.domain.SplunkOutputEntry;
import org.eclipse.microprofile.opentracing.Traced;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ApplicationScoped
public class SplunkForwardingApplicationService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SplunkForwardingApplicationService.class.getName());

    private Jsonb jsonb;

    private UserAgentParser parser;

    @Inject
    private SplunkClient splunkClient;

    @PostConstruct
    public void init() throws RuntimeException {
        LOGGER.debug("init(): loading JsonbBuilder");

        this.jsonb = JsonbBuilder.create();

        LOGGER.debug("init(): successfully loaded JsonbBuilder");

        LOGGER.debug("init(): loading parser");

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

            LOGGER.debug("init(): successfully loaded parser");
        } catch(IOException | ParseException e) {
            LOGGER.error("init(): could not load parser, e = ", e);

            throw new RuntimeException("could not load parser", e);
        }
    }

    @Traced(value = false)
    public Response forwardLog(
            SplunkInputEntry inputEntry,
            String ip,
            String userAgent
    ) {
        LOGGER.info("forwardLog(): inputEntry = " + inputEntry);

        String currentTime = this.getCurrentTimeFormatted();
        Map<String, Object> capabilities = this.extractBrowserCapabilities(userAgent);

        SplunkOutputEntry outputEntry = this.createOutputEntry(inputEntry, currentTime, ip, userAgent, capabilities);
        LOGGER.debug("forwardLog(): outputEntry = " + outputEntry);

        LOGGER.info("forwardLog(): forwarding to Splunk");

        Response splunkResponse = splunkClient.postEvent(outputEntry);

        LOGGER.info("forwardLog(): splunkResponse.status = " + splunkResponse.getStatus());

        return splunkResponse;
    }

    @Traced(value = false)
    public Response forwardBatch(List<SplunkInputEntry> batch, String ip, String userAgent) {
        String batchJson = this.createBatchJson(batch, ip, userAgent);

        LOGGER.info("forwardBatch(): forwarding to Splunk");

        Response splunkResponse = splunkClient.postBatch(batchJson.toString());

        LOGGER.info("forwardBatch(): splunkResponse.status = " + splunkResponse.getStatus());

        return splunkResponse;
    }

    private String createBatchJson(List<SplunkInputEntry> batch, String ip, String userAgent) {
        LOGGER.debug("forwardBatch(): converting batch to json");

        String currentTime = this.getCurrentTimeFormatted();
        Map<String, Object> capabilities = this.extractBrowserCapabilities(userAgent);

        // format these as stacked jsons, as specified here
        // https://docs.splunk.com/Documentation/Splunk/8.1.1/Data/FormateventsforHTTPEventCollector
        StringBuilder batchJson = new StringBuilder();

        for(int i = 0; i < batch.size(); i++) {
            SplunkInputEntry inputEntry = batch.get(i);

            SplunkOutputEntry outputEntry = this.createOutputEntry(inputEntry, currentTime, ip, userAgent, capabilities);

            LOGGER.trace("forwardBatch(): converting entry #" + i + " to json");

            String entryJson = this.jsonb.toJson(outputEntry);

            if(i > 0) {
                batchJson.append('\n');
            }

            batchJson.append(entryJson);
        }

        LOGGER.debug("forwardBatch(): batchJson.length = " + batchJson.length());

        return batchJson.toString();
    }

    private SplunkOutputEntry createOutputEntry(SplunkInputEntry inputEntry, String currentTime, String ip, String userAgent, Map<String, Object> capabilities) {

        SplunkOutputEntry outputEntry = new SplunkOutputEntry();

        outputEntry.setSource(inputEntry.getSource());
        outputEntry.setSourcetype(inputEntry.getSourcetype());
        outputEntry.setTime(currentTime);
        outputEntry.setHost(ip);

        Map<String, Object> event = inputEntry.getEvent();
        event.put("User-Agent", userAgent);
        event.put("capabilities", capabilities);
        outputEntry.setEvent(event);

        return outputEntry;
    }

    private String getCurrentTimeFormatted() {
        long currentTimeMillis = System.currentTimeMillis();

        long currentTimeInSeconds = currentTimeMillis / 1000;
        long currentTimeModuloMilliseconds = currentTimeMillis % 1000;

        return String.format("%d.%d", currentTimeInSeconds, currentTimeModuloMilliseconds);
    }

    private Map<String, Object> extractBrowserCapabilities(String userAgent) {
        Map<String, Object> capabilityMap = new HashMap<>();

        if(null == userAgent || "n/a".equals(userAgent)) {
            return capabilityMap;
        }

        final Capabilities capabilities = this.parser.parse(userAgent);

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
