package de.mkienitz.bachelorarbeit.backend4frontend.application.localization;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/data/translations")
public interface LocalizationServiceClient {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    Response getTranslations();
}
