package de.mkienitz.bachelorarbeit.backend4frontend.application;

import de.mkienitz.bachelorarbeit.backend4frontend.domain.SplunkInputEntry;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 */
@Path("log")
@Singleton
public class SplunkForwardingResource {

    private static Logger log = LoggerFactory.getLogger(SplunkForwardingResource.class.getName());

    @Inject
    private SplunkForwardingService service;

    public SplunkForwardingResource() {
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Operation(description = "Accepts a new log entry and forwards it to Splunk")
    public Response log(
            @Context HttpServletRequest servletRequest,
            @Context HttpHeaders headers,
            @NotNull SplunkInputEntry inputEntry
    ) {
        log.info("log(): inputEntry = " + inputEntry);

        String ip = getIP(servletRequest);
        String userAgent = getUserAgent(servletRequest);

        this.service.forwardLog(inputEntry, ip, userAgent);

        return Response.status(Response.Status.CREATED).build();
    }

    private static String getIP(HttpServletRequest request) {
        return request.getRemoteAddr();
    }

    private static String getUserAgent(HttpServletRequest request) {
        String userAgent = "n/a";

        try {
            userAgent = request.getHeader("User-Agent");
        } catch (Exception ignored){}

        return userAgent;
    }
}
