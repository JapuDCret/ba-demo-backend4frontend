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
public class TranslationForwardingResource {

    public static final String SYS_ENV_VAR_TRANSLATION_SERVICE_URL = "BA_TRANSLATION_SERVICE_URL";

    private static Logger log = LoggerFactory.getLogger(TranslationForwardingResource.class.getName());

    private TranslationServiceClient translationServiceClient;

    public TranslationForwardingResource() throws MalformedURLException {
        String translationServiceUrl = System.getenv(SYS_ENV_VAR_TRANSLATION_SERVICE_URL);

        log.info(String.format("TranslationForwardingResource(): env.%s = %s", SYS_ENV_VAR_TRANSLATION_SERVICE_URL, translationServiceUrl));

        log.debug("TranslationForwardingResource(): creating TransactionServiceClient");

        URL translationServiceUrl2 = new URL(translationServiceUrl);

        TranslationServiceClient translationServiceClient = RestClientBuilder
                .newBuilder()
                .baseUrl(translationServiceUrl2)
                .build(TranslationServiceClient.class);

        log.debug("TranslationForwardingResource(): successfully created TransactionServiceClient");

        this.translationServiceClient = translationServiceClient;
    }

    @GET
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
    public Response getTranslations() {
        try {
            Response getTranslationResponse = translationServiceClient.getTranslations();

            log.info("getTranslations(): status = " + getTranslationResponse.getStatus());

            return getTranslationResponse;
        } catch(Exception e) {
            log.warn("getTranslations(): an unknown error occurred, e = ", e);

            throw e;
        }
    }
}
