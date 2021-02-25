package de.mkienitz.bachelorarbeit.backend4frontend.application.splunk;

import de.mkienitz.bachelorarbeit.backend4frontend.domain.SplunkInputEntry;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.opentracing.Traced;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Objects;

@Path("/")
@RequestScoped
public class SplunkForwardingResource {

    private static final Logger LOGGER = LoggerFactory.getLogger(SplunkForwardingResource.class.getName());

    @Inject
    private SplunkForwardingApplicationService service;

    @POST
    @Path("log")
    @Traced(value = false)
    @Consumes(MediaType.APPLICATION_JSON)
    @Operation(description = "Accepts a new log entry and forwards it to Splunk")
    public Response log(
            @Context HttpServletRequest servletRequest,
            @Context HttpHeaders headers,
            @NotNull SplunkInputEntry inputEntry
    ) {
        LOGGER.info("log(): inputEntry = " + inputEntry);

        String ip = getIP(servletRequest);
        String userAgent = getUserAgent(servletRequest);

        this.service.forwardLog(inputEntry, ip, userAgent);

        return Response.status(Response.Status.CREATED).build();
    }

    @POST
    @Path("log-batch")
    @Traced(value = false)
    @Consumes(MediaType.APPLICATION_JSON)
    @Operation(description = "Accepts a new log entry and forwards it to Splunk")
    public Response logBatch(
            @Context HttpServletRequest servletRequest,
            @Context HttpHeaders headers,
            @NotNull List<SplunkInputEntry> batch
    ) {
        LOGGER.info("logBatch(): batch.size = " + batch.size());

        String ip = getIP(servletRequest);
        String userAgent = getUserAgent(servletRequest);

        this.service.forwardBatch(batch, ip, userAgent);

        return Response.status(Response.Status.CREATED).build();
    }

    private static String getIP(HttpServletRequest request) {
        return request.getRemoteAddr();
    }

    private static String getUserAgent(HttpServletRequest request) {
        String userAgent = request.getHeader("User-Agent");

        return Objects.isNull(userAgent) ? "n/a" : userAgent;
    }
}
