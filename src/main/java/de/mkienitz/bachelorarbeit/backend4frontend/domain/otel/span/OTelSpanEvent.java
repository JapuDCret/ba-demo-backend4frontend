package de.mkienitz.bachelorarbeit.backend4frontend.domain.otel.span;

import de.mkienitz.bachelorarbeit.backend4frontend.domain.otel.attribute.OTelAttribute;

import java.util.List;

public class OTelSpanEvent {
    private long timeUnixNano;

    private String name;

    private List<OTelAttribute> attributes;

    private int droppedAttributesCount;

    public OTelSpanEvent() {
    }

    public long getTimeUnixNano() {
        return timeUnixNano;
    }

    public void setTimeUnixNano(long timeUnixNano) {
        this.timeUnixNano = timeUnixNano;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
