package com.axial.modules.openapi_manager.model.config;

public class HeaderConfig {

    private String name;

    private Boolean required;

    private String defaultValue;

    private String example;

    private String description;

    public String getName() {
        return name;
    }

    public Boolean getRequired() {
        return required;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public String getExample() {
        return example;
    }

    public String getDescription() {
        return description;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setRequired(Boolean required) {
        this.required = required;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    public void setExample(String example) {
        this.example = example;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
