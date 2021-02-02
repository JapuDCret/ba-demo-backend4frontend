package de.mkienitz.bachelorarbeit.backend4frontend.domain.otel.attribute;

public class OTelAttributeBoolType extends OTelAttributeType {
    private boolean boolValue;

    public OTelAttributeBoolType() {
    }

    public OTelAttributeBoolType(boolean boolValue) {
        this.boolValue = boolValue;
    }

    public boolean getBoolValue() {
        return boolValue;
    }

    public void setBoolValue(boolean boolValue) {
        this.boolValue = boolValue;
    }

    @Override
    public String toString() {
        return "OTelAttributeBoolType{" +
                "boolValue=" + boolValue +
                '}';
    }
}
