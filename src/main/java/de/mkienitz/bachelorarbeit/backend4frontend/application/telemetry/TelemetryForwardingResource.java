package de.mkienitz.bachelorarbeit.backend4frontend.application.telemetry;

import de.mkienitz.bachelorarbeit.backend4frontend.domain.otel.OTelExportedTrace;
import org.eclipse.microprofile.opentracing.Traced;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Path("/trace")
@RequestScoped
public class TelemetryForwardingResource {

    private static final Logger log = LoggerFactory.getLogger(TelemetryForwardingResource.class.getName());

    private static ExecutorService executor = Executors.newFixedThreadPool(10);

    @Inject
    private TraceForwardingService traceForwardingService;

    @POST
    @Traced(value = false)
    @Consumes(MediaType.APPLICATION_JSON)
    public void reportTrace(
            OTelExportedTrace trace,
            @Suspended AsyncResponse ar
    ) {
        log.info("reportTrace(): trace = " + trace);

        // use async response to finish the request once the trace has been reported
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
