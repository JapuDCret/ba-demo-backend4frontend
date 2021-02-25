package de.mkienitz.bachelorarbeit.backend4frontend.application.localization;

import de.mkienitz.bachelorarbeit.backend4frontend.Backend4frontendRestApplication;
import org.eclipse.microprofile.rest.client.RestClientBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import java.net.MalformedURLException;
import java.net.URI;

@ApplicationScoped
public class LocalizationServiceClientFactory {

    private static final Logger LOGGER = LoggerFactory.getLogger(LocalizationServiceClientFactory.class.getName());

    @Produces
    public LocalizationServiceClient createClient() throws MalformedURLException {
        URI localizationServiceUri = URI.create(System.getenv(Backend4frontendRestApplication.ENVVAR_LOCALIZATION_SERVICE_URL));

        LOGGER.info(String.format("createClient(): env.%s = %s", Backend4frontendRestApplication.ENVVAR_LOCALIZATION_SERVICE_URL, localizationServiceUri));

        LOGGER.debug("createClient(): creating LocalizationServiceClient");

        LocalizationServiceClient localizationServiceClient = RestClientBuilder
                .newBuilder()
                .baseUrl(localizationServiceUri.toURL())
                .build(LocalizationServiceClient.class);

        LOGGER.debug("createClient(): successfully created LocalizationServiceClient");

        return localizationServiceClient;
    }
}
