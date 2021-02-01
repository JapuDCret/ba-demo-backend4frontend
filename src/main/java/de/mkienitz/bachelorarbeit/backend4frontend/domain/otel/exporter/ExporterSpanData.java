package de.mkienitz.bachelorarbeit.backend4frontend.domain.otel.exporter;

import io.opentelemetry.api.common.Attributes;
import io.opentelemetry.api.trace.Span;
import io.opentelemetry.api.trace.SpanContext;
import io.opentelemetry.api.trace.TraceState;
import io.opentelemetry.sdk.common.InstrumentationLibraryInfo;
import io.opentelemetry.sdk.resources.Resource;
import io.opentelemetry.sdk.trace.data.EventData;
import io.opentelemetry.sdk.trace.data.LinkData;
import io.opentelemetry.sdk.trace.data.SpanData;
import io.opentelemetry.sdk.trace.data.StatusData;

import java.util.List;

public class ExporterSpanData implements SpanData {
    private String traceId;

    private String spanId;

    private boolean sampled;

    private TraceState traceState;

    private SpanContext parentSpanContext;

    private Resource resource;

    private InstrumentationLibraryInfo instrumentationLibraryInfo;

    private String name;

    private Span.Kind kind;

    private long startEpochNanos;

    private Attributes attributes;

    private List<EventData> events;

    private List<LinkData> links;

    private StatusData status;

    private long endEpochNanos;

    private boolean ended;

    private int totalRecordedEvents;

    private int totalRecordedLinks;

    private int totalAttributeCount;

    public ExporterSpanData() {
    }

    @Override
    public String getTraceId() {
        return traceId;
    }

    public void setTraceId(String traceId) {
        this.traceId = traceId;
    }

    @Override
    public String getSpanId() {
        return spanId;
    }

    public void setSpanId(String spanId) {
        this.spanId = spanId;
    }

    @Override
    public boolean isSampled() {
        return sampled;
    }

    public void setSampled(boolean sampled) {
        this.sampled = sampled;
    }

    @Override
    public TraceState getTraceState() {
        return traceState;
    }

    public void setTraceState(TraceState traceState) {
        this.traceState = traceState;
    }

    @Override
    public SpanContext getParentSpanContext() {
        return parentSpanContext;
    }

    public void setParentSpanContext(SpanContext parentSpanContext) {
        this.parentSpanContext = parentSpanContext;
    }

    @Override
    public Resource getResource() {
        return resource;
    }

    public void setResource(Resource resource) {
        this.resource = resource;
    }

    @Override
    public InstrumentationLibraryInfo getInstrumentationLibraryInfo() {
        return instrumentationLibraryInfo;
    }

    public void setInstrumentationLibraryInfo(InstrumentationLibraryInfo instrumentationLibraryInfo) {
        this.instrumentationLibraryInfo = instrumentationLibraryInfo;
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public Span.Kind getKind() {
        return kind;
    }

    public void setKind(Span.Kind kind) {
        this.kind = kind;
    }

    @Override
    public long getStartEpochNanos() {
        return startEpochNanos;
    }

    public void setStartEpochNanos(long startEpochNanos) {
        this.startEpochNanos = startEpochNanos;
    }

    @Override
    public Attributes getAttributes() {
        return attributes;
    }

    public void setAttributes(Attributes attributes) {
        this.attributes = attributes;
    }

    @Override
    public List<EventData> getEvents() {
        return events;
    }

    public void setEvents(List<EventData> events) {
        this.events = events;
    }

    @Override
    public List<LinkData> getLinks() {
        return links;
    }

    public void setLinks(List<LinkData> links) {
        this.links = links;
    }

    @Override
    public StatusData getStatus() {
        return status;
    }

    public void setStatus(StatusData status) {
        this.status = status;
    }

    @Override
    public long getEndEpochNanos() {
        return endEpochNanos;
    }

    public void setEndEpochNanos(long endEpochNanos) {
        this.endEpochNanos = endEpochNanos;
    }

    @Override
    public boolean hasEnded() {
        return ended;
    }

    public void setEnded(boolean ended) {
        this.ended = ended;
    }

    @Override
    public int getTotalRecordedEvents() {
        return totalRecordedEvents;
    }

    public void setTotalRecordedEvents(int totalRecordedEvents) {
        this.totalRecordedEvents = totalRecordedEvents;
    }

    @Override
    public int getTotalRecordedLinks() {
        return totalRecordedLinks;
    }

    public void setTotalRecordedLinks(int totalRecordedLinks) {
        this.totalRecordedLinks = totalRecordedLinks;
    }

    @Override
    public int getTotalAttributeCount() {
        return totalAttributeCount;
    }

    public void setTotalAttributeCount(int totalAttributeCount) {
        this.totalAttributeCount = totalAttributeCount;
    }
}
