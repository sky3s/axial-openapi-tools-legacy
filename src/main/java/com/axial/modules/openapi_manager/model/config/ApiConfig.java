package com.axial.modules.openapi_manager.model.config;

import java.util.Map;


public class ApiConfig {

    /**
     * Unique name for Api
     */
    private String name;

    /**
     * Visible name for Api
     */
    private String groupName;

    private String path;

    private Map<String, SecurityHeaderConfig> securityHeaders;

    private Map<String, HeaderConfig> headers;

    private String description;

    public String getName() {
        return name;
    }

    public String getGroupName() {
        return groupName;
    }

    public String getPath() {
        return path;
    }

    public Map<String, SecurityHeaderConfig> getSecurityHeaders() {
        return securityHeaders;
    }

    public Map<String, HeaderConfig> getHeaders() {
        return headers;
    }

    public String getDescription() {
        return description;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void setSecurityHeaders(Map<String, SecurityHeaderConfig> securityHeaders) {
        this.securityHeaders = securityHeaders;
    }

    public void setHeaders(Map<String, HeaderConfig> headers) {
        this.headers = headers;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}