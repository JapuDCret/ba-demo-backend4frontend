package de.mkienitz.bachelorarbeit.backend4frontend.domain.otel.attribute;

import javax.json.bind.serializer.DeserializationContext;
import javax.json.bind.serializer.JsonbDeserializer;
import javax.json.stream.JsonParser;
import java.lang.reflect.Type;
import java.util.Map;

public class OTelAttributeTypeTypeDeserializer implements JsonbDeserializer<OTelAttributeType> {

    @Override
    public OTelAttributeType deserialize(JsonParser parser, DeserializationContext ctx, Type type) {
        OTelAttributeType attribute = null;

        while (parser.hasNext()) {
            JsonParser.Event event1 = parser.next();

            if (event1 == JsonParser.Event.KEY_NAME && parser.getString().equals("boolValue")) {
                // Deserialize name property
                parser.next(); // move to VALUE
                String strValue = parser.getString();
                boolean value = "true".equals(strValue);
                attribute = new OTelAttributeBoolType(value);
            } else if (event1 == JsonParser.Event.KEY_NAME && parser.getString().equals("doubleValue")) {
                parser.next(); // move to VALUE
                double value = parser.getBigDecimal().doubleValue();
                attribute = new OTelAttributeDoubleType(value);
            } else if (event1 == JsonParser.Event.KEY_NAME && parser.getString().equals("kvlistValue")) {
                parser.next(); // move to VALUE
                Map<String, OTelAttributeType> value = ctx.deserialize(Map.class, parser);
                attribute = new OTelAttributeKvListType(value);
            } else if (event1 == JsonParser.Event.KEY_NAME && parser.getString().equals("longValue")) {
                parser.next(); // move to VALUE
                long value = parser.getLong();
                attribute = new OTelAttributeLongType(value);
            } else if (event1 == JsonParser.Event.KEY_NAME && parser.getString().equals("stringValue")) {
                parser.next(); // move to VALUE
                String value = parser.getString();
                attribute = new OTelAttributeStringType(value);
            }
        }

        return attribute;
    }
}
