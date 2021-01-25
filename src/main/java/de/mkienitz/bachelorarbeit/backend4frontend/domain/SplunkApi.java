package de.mkienitz.bachelorarbeit.backend4frontend.domain;

import org.eclipse.microprofile.rest.client.annotation.ClientHeaderParam;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;

/**
 * vgl. https://docs.splunk.com/Documentation/Splunk/8.1.0/Data/UsetheHTTPEventCollector#Send_data_to_HTTP_Event_Collector_on_Splunk_Cloud_instances
 * */
@Path("services/collector")
@Produces("application/json")
@Consumes("application/json")
public interface SplunkApi {
    @OPTIONS
    Response options();

    @POST
    @Path("/event")
    @ClientHeaderParam(name="Authorization",
            value="{de.mkienitz.bachelorarbeit.backend4frontend.util.SplunkConfig.getSplunkAuthHeader}",
            required=true)
    Response postEvent(SplunkOutputEntry entry);
}
