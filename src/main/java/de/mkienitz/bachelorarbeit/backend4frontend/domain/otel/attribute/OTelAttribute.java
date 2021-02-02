package de.mkienitz.bachelorarbeit.backend4frontend.domain.otel.attribute;

import javax.json.bind.annotation.JsonbTypeDeserializer;

@JsonbTypeDeserializer(value = OTelAttributeTypeDeserializer.class)
public class OTelAttribute {
    private String key;
    private OTelAttributeType value;

    protected OTelAttribute() {
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public OTelAttributeType getValue() {
        return value;
    }

    public void setValue(OTelAttributeType value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "OTelAttribute{" +
                "key='" + key + '\'' +
                ", value=" + value +
                '}';
    }
}
