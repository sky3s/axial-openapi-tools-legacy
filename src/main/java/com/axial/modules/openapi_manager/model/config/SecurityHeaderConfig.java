package com.axial.modules.openapi_manager.model.config;

public class SecurityHeaderConfig {

    private String key;

    private String name;

    private String example;

    private String description;

    public String getKey() {
        return key;
    }

    public String getName() {
        return name;
    }

    public String getExample() {
        return example;
    }

    public String getDescription() {
        return description;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setExample(String example) {
        this.example = example;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}