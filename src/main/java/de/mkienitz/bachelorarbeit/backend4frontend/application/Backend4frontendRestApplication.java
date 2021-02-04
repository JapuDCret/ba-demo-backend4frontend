package de.mkienitz.bachelorarbeit.backend4frontend.application;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.bridge.SLF4JBridgeHandler;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

/**
 *
 */
@ApplicationPath("/data")
public class Backend4frontendRestApplication extends Application {

    private static Logger log = LoggerFactory.getLogger(Backend4frontendRestApplication.class.getName());

    public static final String ENVVAR_ORDER_SERVICE_URL = "BA_ORDER_SERVICE_URL";
    public static final String ENVVAR_CART_SERVICE_URL = "BA_CART_SERVICE_URL";
    public static final String ENVVAR_ADDRESSVALIDATION_SERVICE_URL = "BA_ADDRESSVALIDATION_SERVICE_URL";
    public static final String ENVVAR_LOCALIZATION_SERVICE_URL = "BA_LOCALIZATION_SERVICE_URL";
    public static final String ENVVAR_JAEGER_SERVICE_NAME = "JAEGER_SERVICE_NAME";
    public static final String ENVVAR_JAEGER_AGENT_HOST = "JAEGER_AGENT_HOST";
    public static final String ENVVAR_JAEGER_REPORTER_LOG_SPANS = "JAEGER_REPORTER_LOG_SPANS";
    public static final String ENVVAR_JAEGER_SAMPLER_TYPE = "JAEGER_SAMPLER_TYPE";
    public static final String ENVVAR_JAEGER_SAMPLE_PARAM = "JAEGER_SAMPLER_PARAM";
    public static final String ENVVAR_SPLUNK_HEC_URL = "SPLUNK_HEC_URL";
    public static final String ENVVAR_SPLUNK_HEC_TOKEN = "SPLUNK_HEC_TOKEN";
    public static final String ENVVAR_OTEL_EXPORT_HOST = "OTEL_EXPORT_HOST";
    public static final String ENVVAR_OTEL_EXPORT_PORT = "OTEL_EXPORT_PORT";

    public Backend4frontendRestApplication() {
        log.info("env." + ENVVAR_ORDER_SERVICE_URL + " = " + System.getenv(ENVVAR_ORDER_SERVICE_URL));
        log.info("env." + ENVVAR_CART_SERVICE_URL + " = " + System.getenv(ENVVAR_CART_SERVICE_URL));
        log.info("env." + ENVVAR_ADDRESSVALIDATION_SERVICE_URL + " = " + System.getenv(ENVVAR_ADDRESSVALIDATION_SERVICE_URL));
        log.info("env." + ENVVAR_LOCALIZATION_SERVICE_URL + " = " + System.getenv(ENVVAR_LOCALIZATION_SERVICE_URL));
        log.info("env." + ENVVAR_JAEGER_SERVICE_NAME + " = " + System.getenv(ENVVAR_JAEGER_SERVICE_NAME));
        log.info("env." + ENVVAR_JAEGER_AGENT_HOST + " = " + System.getenv(ENVVAR_JAEGER_AGENT_HOST));
        log.info("env." + ENVVAR_JAEGER_REPORTER_LOG_SPANS + " = " + System.getenv(ENVVAR_JAEGER_REPORTER_LOG_SPANS));
        log.info("env." + ENVVAR_JAEGER_SAMPLER_TYPE + " = " + System.getenv(ENVVAR_JAEGER_SAMPLER_TYPE));
        log.info("env." + ENVVAR_JAEGER_SAMPLE_PARAM + " = " + System.getenv(ENVVAR_JAEGER_SAMPLE_PARAM));
        log.info("env." + ENVVAR_SPLUNK_HEC_URL + " = " + System.getenv(ENVVAR_SPLUNK_HEC_URL));
        log.info("env." + ENVVAR_SPLUNK_HEC_TOKEN + " = " + System.getenv(ENVVAR_SPLUNK_HEC_TOKEN));
        log.info("env." + ENVVAR_OTEL_EXPORT_HOST + " = " + System.getenv(ENVVAR_OTEL_EXPORT_HOST));
        log.info("env." + ENVVAR_OTEL_EXPORT_PORT + " = " + System.getenv(ENVVAR_OTEL_EXPORT_PORT));

        SLF4JBridgeHandler.removeHandlersForRootLogger();
        SLF4JBridgeHandler.install();
    }

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> s = new HashSet<Class<?>>();

        s.add(CORSFilter.class);
        s.add(AddressValidationForwardingResource.class);
        s.add(CartForwardingResource.class);
        s.add(LocalizationForwardingResource.class);
        s.add(OrderForwardingResource.class);
        s.add(SplunkForwardingResource.class);
        s.add(TelemetryForwardingResource.class);

        return s;
    }
}
