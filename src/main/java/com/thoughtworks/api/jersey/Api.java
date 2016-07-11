package com.thoughtworks.api.jersey;

import org.glassfish.jersey.server.ResourceConfig;

import static org.glassfish.jersey.server.ServerProperties.RESPONSE_SET_STATUS_OVER_SEND_ERROR;

public class Api extends ResourceConfig {
    public Api() {
        property(RESPONSE_SET_STATUS_OVER_SEND_ERROR, true);
        packages("com.thoughtworks.api");
//        register(RoutesFeature.class);
//        register(LoggingFilter.class);
//        register(OpenSessionInViewRequestFilter.class);
//        register(OpenSessionInViewResponseFilter.class);
    }
}
