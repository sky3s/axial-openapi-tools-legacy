package com.axial.modules.openapi_manager.model.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;


@Getter
@Setter
@Component
@ConfigurationProperties("api-config")
public class ApplicationApiConfig {

    /**
     * Application Name
     */
    private String name;

    /**
     * Custom server's domain names
     */
    private List<String> domains;

    private String version;

    private Map<String, SecurityHeaderConfig> commonSecurityHeaders;

    private Map<String, HeaderConfig> commonHeaders;

    private Map<String, ApiConfig> apis;

}