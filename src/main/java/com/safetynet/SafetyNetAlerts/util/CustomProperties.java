package com.safetynet.SafetyNetAlerts.util;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/*

 */
@Configuration
@ConfigurationProperties(prefix = "com.safetynet.alerts")
public class CustomProperties {
    private String fileJson;

    public String getFileJson() {
        return fileJson;
    }

    public void setFileJson(String fileJson) {
        this.fileJson = fileJson;
    }
}
