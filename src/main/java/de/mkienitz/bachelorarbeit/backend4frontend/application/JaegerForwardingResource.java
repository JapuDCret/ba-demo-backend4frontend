package de.mkienitz.bachelorarbeit.backend4frontend.application;

import de.mkienitz.bachelorarbeit.backend4frontend.domain.otel.OTelExportedTrace;
import io.opentelemetry.proto.trace.v1.ResourceSpans;
import io.opentelemetry.sdk.common.CompletableResultCode;
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
import java.io.InputStream;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 *
 */
@Path("/trace")
@Singleton
public class JaegerForwardingResource {

    private static Logger log = LoggerFactory.getLogger(JaegerForwardingResource.class.getName());

    private static ExecutorService executor = Executors.newFixedThreadPool(10);

    @Inject
    private JaegerForwardingService jaegerForwardingService;

    public JaegerForwardingResource() { }

    @POST
    @Traced(value = false)
    @Consumes(MediaType.APPLICATION_JSON)
    public void reportTrace(
            OTelExportedTrace trace,
            @Suspended AsyncResponse ar
    ) throws IOException {
        log.info("reportTrace(): trace = " + trace);

        executor.execute(() -> {
            try {
                jaegerForwardingService.reportTrace(trace, ar);
            } catch(Exception e) {
                log.warn("reportTrace(): an unknown error occurred, e = ", e);

                ar.resume(Response.serverError().entity(e).build());
            }
        });
    }
}
