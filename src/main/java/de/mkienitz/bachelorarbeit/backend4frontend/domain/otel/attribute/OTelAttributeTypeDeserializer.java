package de.mkienitz.bachelorarbeit.backend4frontend.domain.otel.attribute;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.json.bind.serializer.DeserializationContext;
import javax.json.bind.serializer.JsonbDeserializer;
import javax.json.stream.JsonParser;
import java.lang.reflect.Type;

public class OTelAttributeTypeDeserializer implements JsonbDeserializer<OTelAttribute> {

    private static Logger log = LoggerFactory.getLogger(OTelAttributeTypeDeserializer.class.getName());

    @Override
    public OTelAttribute deserialize(JsonParser parser, DeserializationContext ctx, Type type) {
        OTelAttribute attribute = new OTelAttribute();

        while (parser.hasNext()) {
            JsonParser.Event event1 = parser.next();

            if (event1 == JsonParser.Event.KEY_NAME && parser.getString().equals("key")) {
                // Deserialize name property
                parser.next(); // move to VALUE
                String key = parser.getString();
                attribute.setKey(key);
            } else if (event1 == JsonParser.Event.KEY_NAME && parser.getString().equals("value")) {
                // Deserialize inner object
                JsonParser.Event event2 = parser.next();
                log.trace("deserialize(): event2 = " + event2);

                OTelAttributeType attributeType = ctx.deserialize(OTelAttributeType.class, parser);

                log.trace("deserialize(): attributeType = " + attributeType);

                attribute.setValue(attributeType);
            }
        }

        return attribute;
    }
}
