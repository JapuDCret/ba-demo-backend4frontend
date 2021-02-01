package de.mkienitz.bachelorarbeit.backend4frontend.domain.otel.instrumentation;

public class OTelInstrumentationLibraryInfo {
    private String name;

    private String version;

    public OTelInstrumentationLibraryInfo() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVersion() {
        return null;
    }

    public void setVersion(String version) {
        this.version = version;
    }
}
