package de.mkienitz.bachelorarbeit.backend4frontend.domain.otel.attribute;

public class OTelAttributeLongType extends OTelAttributeType {
    private double longValue;

    public OTelAttributeLongType() {
    }

    public OTelAttributeLongType(double longValue) {
        this.longValue = longValue;
    }

    public double getLongValue() {
        return longValue;
    }

    public void setLongValue(double longValue) {
        this.longValue = longValue;
    }

    @Override
    public String toString() {
        return "OTelAttributeLongType{" +
                "longValue=" + longValue +
                '}';
    }
}
