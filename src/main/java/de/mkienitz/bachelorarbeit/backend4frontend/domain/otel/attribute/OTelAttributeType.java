package de.mkienitz.bachelorarbeit.backend4frontend.domain.otel.attribute;

import javax.json.bind.annotation.JsonbTypeDeserializer;

@JsonbTypeDeserializer(value = OTelAttributeTypeTypeDeserializer.class)
public abstract class OTelAttributeType {
}
