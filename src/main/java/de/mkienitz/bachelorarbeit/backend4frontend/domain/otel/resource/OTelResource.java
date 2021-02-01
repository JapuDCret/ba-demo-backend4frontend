package de.mkienitz.bachelorarbeit.backend4frontend.domain.otel.resource;

import de.mkienitz.bachelorarbeit.backend4frontend.domain.otel.attribute.OTelAttribute;
import io.opentelemetry.sdk.resources.Resource;

import java.util.List;

public class OTelResource {
    private List<OTelAttribute> attributes;

    private int droppedAttributesCount;

    public OTelResource() {
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

    @Override
    public int hashCode() {
        return 0;
    }
}
