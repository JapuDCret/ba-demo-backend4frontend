package de.mkienitz.bachelorarbeit.backend4frontend.domain.otel.attribute;

import java.util.Map;

public class OTelAttributeKvListType extends OTelAttributeType {
    private Map<String, OTelAttributeType> kvlistValue;

    public OTelAttributeKvListType() {
    }

    public Map<String, OTelAttributeType> getKvlistValue() {
        return kvlistValue;
    }

    public void setKvlistValue(Map<String, OTelAttributeType> kvlistValue) {
        this.kvlistValue = kvlistValue;
    }
}
