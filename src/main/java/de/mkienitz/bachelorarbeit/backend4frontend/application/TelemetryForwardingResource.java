package de.mkienitz.bachelorarbeit.backend4frontend.application;

import de.mkienitz.bachelorarbeit.backend4frontend.domain.otel.OTelExportedTrace;
import org.eclipse.microprofile.opentracing.Traced;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 *
 */
@Path("/")
@Singleton
public class TelemetryForwardingResource {

    private static Logger log = LoggerFactory.getLogger(TelemetryForwardingResource.class.getName());

    private static ExecutorService executor = Executors.newFixedThreadPool(10);

    @Inject
    private TraceForwardingService traceForwardingService;

    public TelemetryForwardingResource() { }

    @POST
    @Path("/trace")
    @Traced(value = false)
    @Consumes(MediaType.APPLICATION_JSON)
    public void reportTrace(
            OTelExportedTrace trace,
            @Suspended AsyncResponse ar
    ) {
        log.info("reportTrace(): trace = " + trace);

        executor.execute(() -> {
            try {
                traceForwardingService.reportTrace(trace, ar);
            } catch(Exception e) {
                log.warn("reportTrace(): an unknown error occurred, e = ", e);

                ar.resume(Response.serverError().entity(e).build());
            }
        });
    }
}
