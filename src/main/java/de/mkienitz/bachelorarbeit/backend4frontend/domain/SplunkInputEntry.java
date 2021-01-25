package de.mkienitz.bachelorarbeit.backend4frontend.domain;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

import javax.validation.constraints.NotNull;
import java.util.Map;

/**
 * see https://docs.splunk.com/Documentation/Splunk/latest/Data/FormateventsforHTTPEventCollector#Event_metadata
 */
public class SplunkInputEntry {
    // The source value to assign to the event data. For example, if you're sending data from an app you're developing, you could set this key to the name of the app.
    @NotNull
    @Schema(required = true)
    protected String source;

    // The sourcetype value to assign to the event data.
    @NotNull
    @Schema(required = true)
    protected String sourcetype;

    @NotNull
    @Schema(required = true)
    protected Map<String, Object> event;

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getSourcetype() {
        return sourcetype;
    }

    public void setSourcetype(String sourcetype) {
        this.sourcetype = sourcetype;
    }

    public Map<String, Object> getEvent() {
        return event;
    }

    public void setEvent(Map<String, Object> event) {
        this.event = event;
    }

    @Override
    public String toString() {
        StringBuilder eventAsString = new StringBuilder();

        eventAsString.append("{");

        boolean isFirst = true;
        for (Map.Entry<String, Object> entry : event.entrySet()) {
            if(!isFirst) eventAsString.append(", ");
            else isFirst = false;

            String value = "null";
            if(entry.getValue() != null) value = entry.getValue().toString();

            eventAsString.append(entry.getKey() + "=" + value);
        }

        eventAsString.append("}");

        return "SplunkInputEntry{" +
                "source='" + source + '\'' +
                ", sourcetype='" + sourcetype + '\'' +
                ", event=" + event +
                '}';
    }
}
