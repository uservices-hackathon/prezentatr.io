package pl.uservices.prezentatr.config;

public final class Versions {
    private Versions() {
        throw new UnsupportedOperationException("Can't instantiate a utility class");
    }

    public static final String PRESENTING_JSON_VERSION_1 = "application/vnd.pl.uservices.presenting.v1+json";

    public static final String AGGREGATING_CONTENT_TYPE_V1 = "application/vnd.pl.uservices.aggregating.v1+json";
}
