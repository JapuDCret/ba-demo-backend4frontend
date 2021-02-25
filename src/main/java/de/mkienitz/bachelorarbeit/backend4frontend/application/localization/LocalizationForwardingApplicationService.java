package de.mkienitz.bachelorarbeit.backend4frontend.application.localization;

import org.eclipse.microprofile.opentracing.Traced;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

@ApplicationScoped
public class LocalizationForwardingApplicationService {

    private static final Logger LOGGER = LoggerFactory.getLogger(LocalizationForwardingApplicationService.class.getName());

    @Inject
    private LocalizationServiceClient localizationServiceClient;

    @Traced(operationName = "LocalizationForwardingApplicationService.getTranslations")
    public Response getTranslations() {
        try {
            Response getTranslationResponse = localizationServiceClient.getTranslations();

            LOGGER.info("getTranslations(): status = " + getTranslationResponse.getStatus());

            return getTranslationResponse;
        } catch(WebApplicationException e) {
            LOGGER.warn("getTranslations(): an unknown error occurred, e = ", e);

            final String errMsg = "Anfrage zum Übersetzungsdienst war nicht erfolgreich, Fehler = " + e.getMessage();

            return Response.status(Response.Status.BAD_GATEWAY).entity(errMsg).build();
        }
    }
}
