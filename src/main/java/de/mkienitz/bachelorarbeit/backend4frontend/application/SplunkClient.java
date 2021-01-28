package de.mkienitz.bachelorarbeit.backend4frontend.application;

import de.mkienitz.bachelorarbeit.backend4frontend.domain.SplunkOutputEntry;
import org.eclipse.microprofile.rest.client.annotation.ClientHeaderParam;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * vgl. https://docs.splunk.com/Documentation/Splunk/8.1.0/Data/UsetheHTTPEventCollector#Send_data_to_HTTP_Event_Collector_on_Splunk_Cloud_instances
 * */
@RegisterRestClient
@Path("services/collector")
public interface SplunkClient {

    @OPTIONS
    Response options();

    @POST
    @Produces("application/json")
    @Consumes("application/json")
    @Path("/event")
    @ClientHeaderParam(name="Authorization",
            value="{de.mkienitz.bachelorarbeit.backend4frontend.util.SplunkConfig.getSplunkAuthHeader}",
            required=true)
    Response postEvent(SplunkOutputEntry entry);

    @POST
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.TEXT_PLAIN)
    @Path("/event")
    @ClientHeaderParam(name="Authorization",
            value="{de.mkienitz.bachelorarbeit.backend4frontend.util.SplunkConfig.getSplunkAuthHeader}",
            required=true)
    Response postBatch(String batch);
}
