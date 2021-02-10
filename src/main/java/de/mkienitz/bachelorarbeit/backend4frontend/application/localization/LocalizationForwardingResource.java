package de.mkienitz.bachelorarbeit.backend4frontend.application.localization;

import org.eclipse.microprofile.opentracing.Traced;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/translation")
@RequestScoped
public class LocalizationForwardingResource {

    private static final Logger log = LoggerFactory.getLogger(LocalizationForwardingResource.class.getName());

    @Inject
    private LocalizationServiceClient localizationServiceClient;

    @GET
    @Traced(operationName = "LocalizationForwardingResource.getTranslations")
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
