package de.mkienitz.bachelorarbeit.backend4frontend.application.localization;

import de.mkienitz.bachelorarbeit.backend4frontend.application.Backend4frontendRestApplication;
import org.eclipse.microprofile.rest.client.RestClientBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import java.net.MalformedURLException;
import java.net.URI;

// can't use ApplicationScoped directly on class, since it's a CDI-managed Producer
public class LocalizationServiceClientFactory {

    private static final Logger LOGGER = LoggerFactory.getLogger(LocalizationServiceClientFactory.class.getName());

    @javax.enterprise.inject.Produces
    @ApplicationScoped
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
