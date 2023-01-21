package com.axialworks.modules.legacy.openapi_tools.model.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;


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


    public String getName() {
        return name;
    }

    public List<String> getDomains() {
        return domains;
    }

    public String getVersion() {
        return version;
    }

    public Map<String, SecurityHeaderConfig> getCommonSecurityHeaders() {
        return commonSecurityHeaders;
    }

    public Map<String, HeaderConfig> getCommonHeaders() {
        return commonHeaders;
    }

    public Map<String, ApiConfig> getApis() {
        return apis;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDomains(List<String> domains) {
        this.domains = domains;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public void setCommonSecurityHeaders(Map<String, SecurityHeaderConfig> commonSecurityHeaders) {
        this.commonSecurityHeaders = commonSecurityHeaders;
    }

    public void setCommonHeaders(Map<String, HeaderConfig> commonHeaders) {
        this.commonHeaders = commonHeaders;
    }

    public void setApis(Map<String, ApiConfig> apis) {
        this.apis = apis;
    }
}