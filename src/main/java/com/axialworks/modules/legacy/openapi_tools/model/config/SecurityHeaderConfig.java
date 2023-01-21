package com.axialworks.modules.legacy.openapi_tools.model.config;

public class SecurityHeaderConfig {
    private String key;

    private String name;

    private String example;

    private String description;


    public SecurityHeaderConfig() { }

    private SecurityHeaderConfig(Builder builder) {
        key = builder.key;
        name = builder.name;
        example = builder.example;
        description = builder.description;
    }

    public static Builder builder() {
        return new Builder();
    }


    public static final class Builder {
        private String key;
        private String name;
        private String example;
        private String description;

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

        public Builder example(String val) {
            example = val;
            return this;
        }

        public Builder description(String val) {
            description = val;
            return this;
        }

        public SecurityHeaderConfig build() {
            return new SecurityHeaderConfig(this);
        }
    }

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