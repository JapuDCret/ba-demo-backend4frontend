package de.mkienitz.bachelorarbeit.backend4frontend.application.localization;

import de.mkienitz.bachelorarbeit.backend4frontend.application.Backend4frontendRestApplication;
import org.eclipse.microprofile.rest.client.RestClientBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.inject.Produces;
import java.net.MalformedURLException;
import java.net.URL;

public class LocalizationServiceClientFactory {

    private static final Logger log = LoggerFactory.getLogger(LocalizationServiceClientFactory.class.getName());

    @Produces
    public LocalizationServiceClient createClient() throws MalformedURLException {
        String localizationServiceUrl = System.getenv(Backend4frontendRestApplication.ENVVAR_LOCALIZATION_SERVICE_URL);

        log.info(String.format("createClient(): env.%s = %s", Backend4frontendRestApplication.ENVVAR_LOCALIZATION_SERVICE_URL, localizationServiceUrl));

        log.debug("createClient(): creating LocalizationServiceClient");

        URL localizationServiceUrl2 = new URL(localizationServiceUrl);

        LocalizationServiceClient localizationServiceClient = RestClientBuilder
                .newBuilder()
                .baseUrl(localizationServiceUrl2)
                .build(LocalizationServiceClient.class);

        log.debug("createClient(): successfully created LocalizationServiceClient");

        return localizationServiceClient;
    }
}
