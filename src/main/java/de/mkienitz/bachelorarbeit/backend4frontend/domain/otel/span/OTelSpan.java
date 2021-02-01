package de.mkienitz.bachelorarbeit.backend4frontend.domain.otel.span;

import de.mkienitz.bachelorarbeit.backend4frontend.domain.otel.attribute.OTelAttribute;

import java.util.List;

public class OTelSpan {
    private String traceId;

    private String spanId;

    private String parentSpanId;

    private String name;

    private int kind;

    private long startTimeUnixNano;

    private long endTimeUnixNano;

    private List<OTelAttribute> attributes;

    private int droppedAttributesCount;

    private List<OTelSpanEvent> events;

    private int droppedEventsCount;

    private OTelSpanStatus status;

    private List<OTelSpanLink> links;

    private int droppedLinksCount;

    public OTelSpan() {
    }

    public String getTraceId() {
        return traceId;
    }

    public void setTraceId(String traceId) {
        this.traceId = traceId;
    }

    public String getSpanId() {
        return spanId;
    }

    public void setSpanId(String spanId) {
        this.spanId = spanId;
    }

    public String getParentSpanId() {
        return parentSpanId;
    }

    public void setParentSpanId(String parentSpanId) {
        this.parentSpanId = parentSpanId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getKind() {
        return kind;
    }

    public void setKind(int kind) {
        this.kind = kind;
    }

    public long getStartTimeUnixNano() {
        return startTimeUnixNano;
    }

    public void setStartTimeUnixNano(long startTimeUnixNano) {
        this.startTimeUnixNano = startTimeUnixNano;
    }

    public long getEndTimeUnixNano() {
        return endTimeUnixNano;
    }

    public void setEndTimeUnixNano(long endTimeUnixNano) {
        this.endTimeUnixNano = endTimeUnixNano;
    }

    public List<OTelAttribute> getAttributes() {
        return attributes;
    }

    public void setAttributes(List<OTelAttribute> attributes) {
        this.attributes = attributes;
    }

    public int getDroppedAttributesCount() {
        return droppedAttributesCount;
    }

    public void setDroppedAttributesCount(int droppedAttributesCount) {
        this.droppedAttributesCount = droppedAttributesCount;
    }

    public List<OTelSpanEvent> getEvents() {
        return events;
    }

    public void setEvents(List<OTelSpanEvent> events) {
        this.events = events;
    }

    public int getDroppedEventsCount() {
        return droppedEventsCount;
    }

    public void setDroppedEventsCount(int droppedEventsCount) {
        this.droppedEventsCount = droppedEventsCount;
    }

    public OTelSpanStatus getStatus() {
        return status;
    }

    public void setStatus(OTelSpanStatus status) {
        this.status = status;
    }

    public List<OTelSpanLink> getLinks() {
        return links;
    }

    public void setLinks(List<OTelSpanLink> links) {
        this.links = links;
    }

    public int getDroppedLinksCount() {
        return droppedLinksCount;
    }

    public void setDroppedLinksCount(int droppedLinksCount) {
        this.droppedLinksCount = droppedLinksCount;
    }
}
