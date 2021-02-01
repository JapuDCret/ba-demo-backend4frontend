package de.mkienitz.bachelorarbeit.backend4frontend.application;

import de.mkienitz.bachelorarbeit.backend4frontend.domain.otel.OTelExportedTrace;
import de.mkienitz.bachelorarbeit.backend4frontend.domain.otel.attribute.*;
import de.mkienitz.bachelorarbeit.backend4frontend.domain.otel.exporter.ExporterSpanData;
import de.mkienitz.bachelorarbeit.backend4frontend.domain.otel.instrumentation.OTelInstrumentationLibraryInfo;
import de.mkienitz.bachelorarbeit.backend4frontend.domain.otel.instrumentation.OTelInstrumentationLibrarySpan;
import de.mkienitz.bachelorarbeit.backend4frontend.domain.otel.resource.OTelResource;
import de.mkienitz.bachelorarbeit.backend4frontend.domain.otel.resource.OTelResourceSpan;
import de.mkienitz.bachelorarbeit.backend4frontend.domain.otel.span.OTelSpan;
import de.mkienitz.bachelorarbeit.backend4frontend.domain.otel.span.OTelSpanEvent;
import de.mkienitz.bachelorarbeit.backend4frontend.domain.otel.span.OTelSpanLink;
import io.opentelemetry.api.common.Attributes;
import io.opentelemetry.api.common.AttributesBuilder;
import io.opentelemetry.api.trace.*;
import io.opentelemetry.exporter.jaeger.JaegerGrpcSpanExporter;
import io.opentelemetry.sdk.common.CompletableResultCode;
import io.opentelemetry.sdk.common.InstrumentationLibraryInfo;
import io.opentelemetry.sdk.resources.Resource;
import io.opentelemetry.sdk.trace.data.EventData;
import io.opentelemetry.sdk.trace.data.LinkData;
import io.opentelemetry.sdk.trace.data.SpanData;
import io.opentelemetry.sdk.trace.data.StatusData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.core.Response;
import java.util.*;

/**
 * */
@ApplicationScoped
public class JaegerForwardingService {

    private static Logger log = LoggerFactory.getLogger(JaegerForwardingService.class.getName());

    public static final int JAEGER_AGENT_DEFAULT_PORT = 14268;
    private final JaegerGrpcSpanExporter exporter;

    public JaegerForwardingService() throws Exception {
        log.debug("JaegerForwardingService(): initializing JaegerGrpcSpanExporter");

        String exportHost = System.getenv(Backend4frontendRestApplication.ENVVAR_OTEL_EXPORT_HOST);
        String exportPort = System.getenv(Backend4frontendRestApplication.ENVVAR_OTEL_EXPORT_PORT);

        String exportEndpoint = exportHost + ":" + exportPort;

        log.info("JaegerForwardingService(): exportEndpoint = " + exportEndpoint);

        JaegerGrpcSpanExporter exporter =
                JaegerGrpcSpanExporter.builder()
                        .setEndpoint(exportEndpoint)
                        .setServiceName("frontend")
                        .build();

        log.debug("JaegerForwardingService(): successfully initialized JaegerGrpcSpanExporter");

        this.exporter = exporter;
    }

    private void insertAttributeValue(final AttributesBuilder attributesBuilder, String key, OTelAttributeType attributeType) {
        if(attributeType instanceof OTelAttributeBoolType) {
            attributesBuilder.put(key, ((OTelAttributeBoolType) attributeType).getBoolValue());
        } else if(attributeType instanceof OTelAttributeDoubleType) {
            attributesBuilder.put(key, ((OTelAttributeDoubleType) attributeType).getDoubleValue());
        } else if(attributeType instanceof OTelAttributeKvListType) {
            AttributesBuilder innerAttributesBuilder = Attributes.builder();

            Map<String, OTelAttributeType> kvlistValue = ((OTelAttributeKvListType) attributeType).getKvlistValue();
            Set<String> keys = kvlistValue.keySet();
            for(String innerKey : keys) {
                OTelAttributeType innerAttributeType = kvlistValue.get(innerKey);

                this.insertAttributeValue(innerAttributesBuilder, innerKey, innerAttributeType);
            }

            attributesBuilder.putAll(innerAttributesBuilder.build());
        }
    }

    public void reportTrace(OTelExportedTrace trace, AsyncResponse ar) {
        List<OTelResourceSpan> rSpans = trace.getResourceSpans();

        Collection<SpanData> spanDataList = new ArrayList<>();

        for (OTelResourceSpan rSpan : rSpans) {
            OTelResource r = rSpan.getResource();

            AttributesBuilder rAttributesBuilder = Attributes.builder();
            for(OTelAttribute rAttribute : r.getAttributes()) {
                OTelAttributeType rAttributesValue = rAttribute.getValue();

                this.insertAttributeValue(rAttributesBuilder, rAttribute.getKey(), rAttributesValue);
            }
            Resource resource = Resource.create(rAttributesBuilder.build());

            List<OTelInstrumentationLibrarySpan> ilSpans = rSpan.getInstrumentationLibrarySpans();

            for (OTelInstrumentationLibrarySpan ilSpan : ilSpans) {
                OTelInstrumentationLibraryInfo ilInfo = ilSpan.getInstrumentationLibrary();

                InstrumentationLibraryInfo instrumentationLibraryInfo = InstrumentationLibraryInfo.create(ilInfo.getName(), ilInfo.getVersion());

                List<OTelSpan> spans = ilSpan.getSpans();

                for (OTelSpan span : spans) {
                    ExporterSpanData spanData = new ExporterSpanData();

                    TraceState traceState = TraceState.getDefault();
                    byte traceFlags = TraceFlags.getDefault();

                    String parentSpanId = null == span.getParentSpanId() ? "" : span.getParentSpanId();
                    SpanContext parentSpanContext = SpanContext.create(span.getTraceId(), parentSpanId, traceFlags, traceState);

                    AttributesBuilder sAttributesBuilder = Attributes.builder();
                    for(OTelAttribute rAttribute : span.getAttributes()) {
                        OTelAttributeType rAttributesValue = rAttribute.getValue();

                        this.insertAttributeValue(sAttributesBuilder, rAttribute.getKey(), rAttributesValue);
                    }
                    Attributes sAttributes = sAttributesBuilder.build();

                    List<EventData> sEvents = new ArrayList<>();
                    for(OTelSpanEvent event : span.getEvents()) {
                        AttributesBuilder eAttributesBuilder = Attributes.builder();
                        for(OTelAttribute rAttribute : event.getAttributes()) {
                            OTelAttributeType rAttributesValue = rAttribute.getValue();

                            this.insertAttributeValue(sAttributesBuilder, rAttribute.getKey(), rAttributesValue);
                        }

                        EventData.create(event.getTimeUnixNano(), event.getName(), eAttributesBuilder.build());
                    }

                    List<LinkData> sLinks = new ArrayList<>();
                    for(OTelSpanLink link : span.getLinks()) {
                        TraceState lTraceState = TraceState.getDefault();
                        byte lTraceFlags = TraceFlags.getDefault();

                        SpanContext spanContext = SpanContext.create(link.getTraceId(), link.getSpanId(), lTraceFlags, lTraceState);

                        AttributesBuilder lAttributesBuilder = Attributes.builder();
                        for(OTelAttribute rAttribute : link.getAttributes()) {
                            OTelAttributeType rAttributesValue = rAttribute.getValue();

                            this.insertAttributeValue(sAttributesBuilder, rAttribute.getKey(), rAttributesValue);
                        }

                        LinkData.create(spanContext, lAttributesBuilder.build());
                    }

                    StatusCode statusCode = StatusCode.values()[span.getStatus().getCode()];
                    StatusData statusData = StatusData.create(statusCode, null);

                    spanData.setTraceId(span.getTraceId());
                    spanData.setSpanId(span.getSpanId());
                    spanData.setSampled(true);
                    spanData.setTraceState(traceState);
                    spanData.setParentSpanContext(parentSpanContext);
                    spanData.setResource(resource);
                    spanData.setInstrumentationLibraryInfo(instrumentationLibraryInfo);
                    spanData.setName(span.getName());
                    spanData.setKind(Span.Kind.CLIENT);
                    spanData.setStartEpochNanos(span.getStartTimeUnixNano());
                    spanData.setAttributes(sAttributes);
                    spanData.setEvents(sEvents);
                    spanData.setLinks(sLinks);
                    spanData.setStatus(statusData);
                    spanData.setEndEpochNanos(span.getEndTimeUnixNano());
                    spanData.setEnded(true);
                    spanData.setTotalRecordedEvents(sEvents.size());
                    spanData.setTotalRecordedLinks(sLinks.size());
                    spanData.setTotalAttributeCount(sAttributes.size());

                    spanDataList.add(spanData);
                }
            }
        }

        this.sendTrace(spanDataList, ar);
    }

    public void sendTrace(
            Collection<SpanData> spanDataList,
            AsyncResponse ar
    ) {
        log.info("sendTrace(): spanDataList = " + spanDataList);

        CompletableResultCode resultCode = exporter.export(spanDataList);

        resultCode.whenComplete(() -> {
            log.info("sendTrace(): resultCode.done = " + resultCode.isDone() + ", resultCode.success = " + resultCode.isSuccess());

            ar.resume(Response.ok().entity(resultCode).build());
        });
    }
}
