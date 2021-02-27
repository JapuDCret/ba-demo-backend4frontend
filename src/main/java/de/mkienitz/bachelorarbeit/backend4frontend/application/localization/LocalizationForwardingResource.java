package de.mkienitz.bachelorarbeit.backend4frontend.application.localization;

import org.eclipse.microprofile.opentracing.Traced;

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

    @Inject
    private LocalizationForwardingApplicationService service;

    @GET
    @Traced(operationName = "LocalizationForwardingResource.forwardGetTranslations")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
    public Response forwardGetTranslations() {
        Response translationResponse = service.getTranslations();

        return translationResponse;
    }
}
