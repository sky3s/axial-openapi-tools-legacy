package com.axial.modules.openapi_manager.model;

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

    public void setKey(String key) {
        this.key = key;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    public void setRequired(boolean required) {
        this.required = required;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDefaultApiHeader(boolean defaultApiHeader) {
        this.defaultApiHeader = defaultApiHeader;
    }

    public void setDefaultSecurityHeader(boolean defaultSecurityHeader) {
        this.defaultSecurityHeader = defaultSecurityHeader;
    }
}
