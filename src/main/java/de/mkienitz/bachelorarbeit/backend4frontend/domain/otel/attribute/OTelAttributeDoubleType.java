package de.mkienitz.bachelorarbeit.backend4frontend.domain.otel.attribute;

public class OTelAttributeDoubleType extends OTelAttributeType {
    private double doubleValue;

    public OTelAttributeDoubleType() {
    }

    public OTelAttributeDoubleType(double doubleValue) {
        this.doubleValue = doubleValue;
    }

    public double getDoubleValue() {
        return doubleValue;
    }

    public void setDoubleValue(double doubleValue) {
        this.doubleValue = doubleValue;
    }

    @Override
    public String toString() {
        return "OTelAttributeDoubleType{" +
                "doubleValue=" + doubleValue +
                '}';
    }
}
