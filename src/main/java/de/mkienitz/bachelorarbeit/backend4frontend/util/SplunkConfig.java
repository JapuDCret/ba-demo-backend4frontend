package de.mkienitz.bachelorarbeit.backend4frontend.util;

import de.mkienitz.bachelorarbeit.backend4frontend.application.Backend4frontendRestApplication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Base64;

public class SplunkConfig {

    private static final Logger log = LoggerFactory.getLogger(SplunkConfig.class.getName());

    public static String getSplunkAuthHeader() {
        String splunkHecToken = System.getenv(Backend4frontendRestApplication.ENVVAR_SPLUNK_HEC_TOKEN);

        log.debug(String.format("getSplunkAuthHeader(): env.%s = %s", Backend4frontendRestApplication.ENVVAR_SPLUNK_HEC_TOKEN, splunkHecToken));

        return "Splunk " + splunkHecToken;
    }
}
