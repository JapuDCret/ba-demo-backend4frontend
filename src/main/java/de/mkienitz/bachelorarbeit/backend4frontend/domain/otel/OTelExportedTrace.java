package de.mkienitz.bachelorarbeit.backend4frontend.domain.otel;

import de.mkienitz.bachelorarbeit.backend4frontend.domain.otel.resource.OTelResourceSpan;

import java.util.List;

public class OTelExportedTrace {
    List<OTelResourceSpan> resourceSpans;

    public OTelExportedTrace() {
    }

    public List<OTelResourceSpan> getResourceSpans() {
        return resourceSpans;
    }

    public void setResourceSpans(List<OTelResourceSpan> resourceSpans) {
        this.resourceSpans = resourceSpans;
    }
}
