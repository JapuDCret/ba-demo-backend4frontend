package de.mkienitz.bachelorarbeit.backend4frontend.domain.otel.attribute;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.json.JsonObject;
import javax.json.bind.serializer.DeserializationContext;
import javax.json.bind.serializer.JsonbDeserializer;
import javax.json.stream.JsonParser;
import java.lang.reflect.Type;
import java.util.Map;
import java.util.Set;

public class OTelAttributeTypeTypeDeserializer implements JsonbDeserializer<OTelAttributeType> {

    private static final Logger log = LoggerFactory.getLogger(OTelAttributeTypeTypeDeserializer.class.getName());

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
                Map value = ctx.deserialize(Map.class, parser);
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
