package de.mkienitz.bachelorarbeit.backend4frontend.domain.otel.resource;

import de.mkienitz.bachelorarbeit.backend4frontend.domain.otel.instrumentation.OTelInstrumentationLibrarySpan;

import java.util.List;

public class OTelResourceSpan {
    private OTelResource resource;

    private List<OTelInstrumentationLibrarySpan> instrumentationLibrarySpans;

    public OTelResourceSpan() {
    }

    public OTelResource getResource() {
        return resource;
    }

    public void setResource(OTelResource resource) {
        this.resource = resource;
    }

    public List<OTelInstrumentationLibrarySpan> getInstrumentationLibrarySpans() {
        return instrumentationLibrarySpans;
    }

    public void setInstrumentationLibrarySpans(List<OTelInstrumentationLibrarySpan> instrumentationLibrarySpans) {
        this.instrumentationLibrarySpans = instrumentationLibrarySpans;
    }
}
