package de.mkienitz.bachelorarbeit.backend4frontend.domain.otel.attribute;

public class OTelAttributeLongType extends OTelAttributeType {
    private double longValue;

    public OTelAttributeLongType() {
    }

    public double getLongValue() {
        return longValue;
    }

    public void setLongValue(double longValue) {
        this.longValue = longValue;
    }
}
