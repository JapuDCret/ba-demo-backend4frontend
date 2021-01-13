package de.mkienitz.bachelorarbeit.backend4frontend.application;

import org.eclipse.microprofile.rest.client.RestClientBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Singleton;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.MalformedURLException;
import java.net.URL;

/**
 *
 */
@Path("/translation")
@Singleton
public class LocalizationForwardingResource {

    public static final String SYS_ENV_VAR_LOCALIZATION_SERVICE_URL = "BA_LOCALIZATION_SERVICE_URL";

    private static Logger log = LoggerFactory.getLogger(LocalizationForwardingResource.class.getName());

    private LocalizationServiceClient localizationServiceClient;

    public LocalizationForwardingResource() throws MalformedURLException {
        String localizationServiceUrl = System.getenv(SYS_ENV_VAR_LOCALIZATION_SERVICE_URL);

        log.info(String.format("TranslationForwardingResource(): env.%s = %s", SYS_ENV_VAR_LOCALIZATION_SERVICE_URL, localizationServiceUrl));

        log.debug("LocalizationForwardingResource(): creating LocalizationServiceClient");

        URL localizationServiceUrl2 = new URL(localizationServiceUrl);

        LocalizationServiceClient localizationServiceClient = RestClientBuilder
                .newBuilder()
                .baseUrl(localizationServiceUrl2)
                .build(LocalizationServiceClient.class);

        log.debug("LocalizationForwardingResource(): successfully created LocalizationServiceClient");

        this.localizationServiceClient = localizationServiceClient;
    }

    @GET
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
    public Response getTranslations() {
        try {
            Response getTranslationResponse = localizationServiceClient.getTranslations();

            log.info("getTranslations(): status = " + getTranslationResponse.getStatus());

            return getTranslationResponse;
        } catch(Exception e) {
            log.warn("getTranslations(): an unknown error occurred, e = ", e);

            throw e;
        }
    }
}
