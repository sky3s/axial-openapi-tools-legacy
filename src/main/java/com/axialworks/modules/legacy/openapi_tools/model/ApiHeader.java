package com.axialworks.modules.legacy.openapi_tools.model;

/**
 * Created on December 2022
 */
public class ApiHeader {

    /**
     * Unique definition of header
     */
    private String key;

    /**
     * Key pass via Header, actual key of header
     */
    private String name;

    private String defaultValue;

    private boolean required;

    private String description;

    /**
     * Add this header as service header, default action
     */
    private boolean defaultApiHeader;

    /**
     * Add this header as security header, default action
     */
    private boolean defaultSecurityHeader;

    private ApiHeader(Builder builder) {
        key = builder.key;
        name = builder.name;
        defaultValue = builder.defaultValue;
        required = builder.required;
        description = builder.description;
        defaultApiHeader = builder.defaultApiHeader;
        defaultSecurityHeader = builder.defaultSecurityHeader;
    }

    public static Builder builder() {
        return new Builder();
    }


    public static final class Builder {
        private String key;
        private String name;
        private String defaultValue;
        private boolean required;
        private String description;
        private boolean defaultApiHeader;
        private boolean defaultSecurityHeader;

        private Builder() {
        }

        public Builder key(String val) {
            key = val;
            return this;
        }

        public Builder name(String val) {
            name = val;
            return this;
        }

        public Builder defaultValue(String val) {
            defaultValue = val;
            return this;
        }

        public Builder required(boolean val) {
            required = val;
            return this;
        }

        public Builder description(String val) {
            description = val;
            return this;
        }

        public Builder defaultApiHeader(boolean val) {
            defaultApiHeader = val;
            return this;
        }

        public Builder defaultSecurityHeader(boolean val) {
            defaultSecurityHeader = val;
            return this;
        }

        public ApiHeader build() {
            return new ApiHeader(this);
        }
    }

    public String getKey() {
        return key;
    }

    public String getName() {
        return name;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public boolean isRequired() {
        return required;
    }

    public String getDescription() {
        return description;
    }

    public boolean isDefaultApiHeader() {
        return defaultApiHeader;
    }

    public boolean isDefaultSecurityHeader() {
        return defaultSecurityHeader;
    }
}
