package de.mkienitz.bachelorarbeit.backend4frontend.domain.otel.attribute;

public class OTelAttributeStringType extends OTelAttributeType {
    private String stringValue;

    public OTelAttributeStringType() {
    }

    public OTelAttributeStringType(String stringValue) {
        this.stringValue = stringValue;
    }

    public String getStringValue() {
        return stringValue;
    }

    public void setStringValue(String stringValue) {
        this.stringValue = stringValue;
    }

    @Override
    public String toString() {
        return "OTelAttributeStringType{" +
                "stringValue='" + stringValue + '\'' +
                '}';
    }
}
