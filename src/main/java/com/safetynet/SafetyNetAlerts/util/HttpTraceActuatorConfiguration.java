package com.safetynet.SafetyNetAlerts.util;

import org.springframework.boot.actuate.trace.http.HttpTraceRepository;
import org.springframework.boot.actuate.trace.http.InMemoryHttpTraceRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * With Spring Boot 2.2.0 the "httptrace" Actuator endpoint doesn't exist anymore.
 * The functionality has been removed by default in Spring Boot 2.2.0. To fix it, add this configuration to the Spring environment:
 * management.endpoints.web.exposure.include: httptrace
 * and provide a HttpTraceRepository bean like this:
 */
@Configuration
public class HttpTraceActuatorConfiguration {
    @Bean
    public HttpTraceRepository httpTraceRepository() {
        return new InMemoryHttpTraceRepository();
    }

}
