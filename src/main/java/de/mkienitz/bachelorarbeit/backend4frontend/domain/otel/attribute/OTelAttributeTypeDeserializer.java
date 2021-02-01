package de.mkienitz.bachelorarbeit.backend4frontend.domain.otel.attribute;

import de.mkienitz.bachelorarbeit.backend4frontend.application.SplunkForwardingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.json.JsonObject;
import javax.json.bind.serializer.DeserializationContext;
import javax.json.bind.serializer.JsonbDeserializer;
import javax.json.stream.JsonParser;
import java.lang.reflect.Type;
import java.util.Set;

public class OTelAttributeTypeDeserializer implements JsonbDeserializer<OTelAttribute> {

    private static Logger log = LoggerFactory.getLogger(OTelAttributeTypeDeserializer.class.getName());

    @Override
    public OTelAttribute deserialize(JsonParser parser, DeserializationContext ctx, Type type) {
        OTelAttribute attribute = new OTelAttribute();

        while (parser.hasNext()) {
            JsonParser.Event event = parser.next();

            if (event == JsonParser.Event.KEY_NAME && parser.getString().equals("key")) {
                // Deserialize name property
                parser.next(); // move to VALUE
                String key = parser.getString();
                attribute.setKey(key);
            } else if (event == JsonParser.Event.KEY_NAME && parser.getString().equals("value")) {
                // Deserialize inner object
                parser.next();
                JsonObject jsonObj = parser.getObject();

                Set<String> keys = jsonObj.keySet();

                log.trace("deserialize(): keys = " + keys);

                OTelAttributeType attributeType;
                if(keys.contains("boolValue")) {
                    attributeType = ctx.deserialize(OTelAttributeBoolType.class, parser);
                } else if(keys.contains("doubleValue")) {
                    attributeType = ctx.deserialize(OTelAttributeDoubleType.class, parser);
                } else if(keys.contains("kvlistValue")) {
                    attributeType = ctx.deserialize(OTelAttributeKvListType.class, parser);
                } else if(keys.contains("longValue")) {
                    attributeType = ctx.deserialize(OTelAttributeLongType.class, parser);
                } else {
                    attributeType = ctx.deserialize(OTelAttributeStringType.class, parser);
                }

                attribute.setValue(attributeType);
            }
        }

        return attribute;
    }
}
