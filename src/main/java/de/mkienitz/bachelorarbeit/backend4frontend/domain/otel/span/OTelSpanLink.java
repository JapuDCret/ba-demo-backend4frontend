package de.mkienitz.bachelorarbeit.backend4frontend.domain.otel.span;

import de.mkienitz.bachelorarbeit.backend4frontend.domain.otel.attribute.OTelAttribute;

import java.util.List;

public class OTelSpanLink {
    private String traceId;

    private String spanId;

    private List<OTelAttribute> attributes;

    private int droppedAttributesCount;

    public OTelSpanLink() {
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
}
