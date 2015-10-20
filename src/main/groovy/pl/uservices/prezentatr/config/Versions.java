package pl.uservices.prezentatr.config;

public final class Versions {
    private Versions() {
        throw new UnsupportedOperationException("Can't instantiate a utility class");
    }

    public static final String APP_NAME = "vnd.pl.uservices.prezentatr";
    public static final String VND_PREFIX = "application/vnd";
    public static final String JSON_TYPE_SUFFIX = "+json";

    public static final String VERSION_1 = "v1";
    public static final String TWITTER_PLACES_ANALYZER_JSON_VERSION_1 = VND_PREFIX + "." + APP_NAME + "." + VERSION_1 + JSON_TYPE_SUFFIX;

    public static final String PREZENTATR_JSON_VERSION_1 = "application/vnd.pl.uservices.prezentatr.v1+json";

    public static final String AGREGATR_CONTENT_TYPE_V1 = "application/vnd.pl.uservices.aggregatr.v1+json";
}
