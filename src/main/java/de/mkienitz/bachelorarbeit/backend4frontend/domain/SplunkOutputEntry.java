package de.mkienitz.bachelorarbeit.backend4frontend.domain;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

import javax.validation.constraints.NotNull;
import java.util.Map;

/**
 * see https://docs.splunk.com/Documentation/Splunk/latest/Data/FormateventsforHTTPEventCollector#Event_metadata
 */
public class SplunkOutputEntry extends SplunkInputEntry {
    // The event time. The default time format is epoch time format, in the format <sec>.<ms>. For example, 1433188255.500 indicates 1433188255 seconds and 500 milliseconds after epoch, or Monday, June 1, 2015, at 7:50:55 PM GMT.
    protected String time;

    // The host value to assign to the event data. This is typically the hostname of the client from which you're sending data.
    @NotNull
    @Schema(required = true)
    protected String host;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    @Override
    public String toString() {
        StringBuilder eventAsString = new StringBuilder();

        eventAsString.append("{");

        boolean isFirst = true;
        for (Map.Entry<String, Object> entry : this.event.entrySet()) {
            if(!isFirst) eventAsString.append(", ");
            else isFirst = false;

            String value = "null";
            if(entry.getValue() != null) value = entry.getValue().toString();

            eventAsString.append(entry.getKey() + "=" + value);
        }

        eventAsString.append("}");

        return "SplunkEntry{" +
                "time=" + time +
                ", host='" + host + '\'' +
                ", source='" + source + '\'' +
                ", sourcetype='" + sourcetype + '\'' +
                ", event=" + event +
                '}';
    }
}
