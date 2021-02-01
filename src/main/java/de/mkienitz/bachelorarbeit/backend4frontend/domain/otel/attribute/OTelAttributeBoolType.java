package de.mkienitz.bachelorarbeit.backend4frontend.domain.otel.attribute;

public class OTelAttributeBoolType extends OTelAttributeType {
    private boolean boolValue;

    public OTelAttributeBoolType() {
    }

    public boolean getBoolValue() {
        return boolValue;
    }

    public void setBoolValue(boolean boolValue) {
        this.boolValue = boolValue;
    }
}
