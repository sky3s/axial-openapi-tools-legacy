package com.axialworks.modules.legacy.openapi_tools.model.config;

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


    public ApiConfig() { }

    private ApiConfig(Builder builder) {
        name = builder.name;
        groupName = builder.groupName;
        path = builder.path;
        securityHeaders = builder.securityHeaders;
        headers = builder.headers;
        description = builder.description;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private String name;
        private String groupName;
        private String path;
        private Map<String, SecurityHeaderConfig> securityHeaders;
        private Map<String, HeaderConfig> headers;
        private String description;

        private Builder() {
        }

        public Builder name(String val) {
            name = val;
            return this;
        }

        public Builder groupName(String val) {
            groupName = val;
            return this;
        }

        public Builder path(String val) {
            path = val;
            return this;
        }

        public Builder securityHeaders(Map<String, SecurityHeaderConfig> val) {
            securityHeaders = val;
            return this;
        }

        public Builder headers(Map<String, HeaderConfig> val) {
            headers = val;
            return this;
        }

        public Builder description(String val) {
            description = val;
            return this;
        }

        public ApiConfig build() {
            return new ApiConfig(this);
        }
    }

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