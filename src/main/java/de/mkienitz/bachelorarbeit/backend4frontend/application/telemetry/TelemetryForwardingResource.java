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

    private static final Logger LOGGER = LoggerFactory.getLogger(TelemetryForwardingResource.class.getName());

    private static ExecutorService executor = Executors.newFixedThreadPool(10);

    @Inject
    private TraceForwardingApplicationService service;

    @POST
    @Traced(value = false)
    @Consumes(MediaType.APPLICATION_JSON)
    public void reportTrace(
            OTelExportedTrace trace,
            @Suspended AsyncResponse ar
    ) {
        LOGGER.info("reportTrace(): trace = " + trace);

        // use async response to finish the request once the trace has been reported
        executor.execute(() -> {
            try {
                service.reportTrace(trace, ar);
            } catch(Exception e) {
                LOGGER.warn("reportTrace(): an unknown error occurred, e = ", e);

                final String errMsg = "Anfrage zum Tracedienst war nicht erfolgreich, Fehler = " + e.getMessage();

                ar.resume(Response.status(Response.Status.BAD_GATEWAY).entity(errMsg).build());
            }
        });
    }
}
