package de.mkienitz.bachelorarbeit.backend4frontend.domain.otel.instrumentation;

import de.mkienitz.bachelorarbeit.backend4frontend.domain.otel.span.OTelSpan;

import java.util.List;

public class OTelInstrumentationLibrarySpan {
    private List<OTelSpan> spans;

    private OTelInstrumentationLibraryInfo instrumentationLibrary;

    public OTelInstrumentationLibrarySpan() {
    }

    public List<OTelSpan> getSpans() {
        return spans;
    }

    public void setSpans(List<OTelSpan> spans) {
        this.spans = spans;
    }

    public OTelInstrumentationLibraryInfo getInstrumentationLibrary() {
        return instrumentationLibrary;
    }

    public void setInstrumentationLibrary(OTelInstrumentationLibraryInfo instrumentationLibrary) {
        this.instrumentationLibrary = instrumentationLibrary;
    }
}
