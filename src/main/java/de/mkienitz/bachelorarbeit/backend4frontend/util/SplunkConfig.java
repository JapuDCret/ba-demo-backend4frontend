package de.mkienitz.bachelorarbeit.backend4frontend.util;

import de.mkienitz.bachelorarbeit.backend4frontend.Backend4frontendRestApplication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * SplunkConfig is used by {@link de.mkienitz.bachelorarbeit.backend4frontend.application.splunk.SplunkClient} through the {@link org.eclipse.microprofile.rest.client.annotation.ClientHeaderParam} annotation
 */
@SuppressWarnings("unused")
public class SplunkConfig {

    private static final Logger LOGGER = LoggerFactory.getLogger(SplunkConfig.class.getName());

    @SuppressWarnings("unused")
    public static String getSplunkAuthHeader() {
        String splunkHecToken = System.getenv(Backend4frontendRestApplication.ENVVAR_SPLUNK_HEC_TOKEN);

        LOGGER.debug(String.format("getSplunkAuthHeader(): env.%s = %s", Backend4frontendRestApplication.ENVVAR_SPLUNK_HEC_TOKEN, splunkHecToken));

        return "Splunk " + splunkHecToken;
    }
}
