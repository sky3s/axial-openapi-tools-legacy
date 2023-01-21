package com.axialworks.modules.legacy.openapi_tools.model.config;

public class HeaderConfig {

    private String name;

    private Boolean required;

    private String defaultValue;

    private String example;

    private String description;


    public HeaderConfig() { }

    private HeaderConfig(Builder builder) {
        setName(builder.name);
        setRequired(builder.required);
        setDefaultValue(builder.defaultValue);
        setExample(builder.example);
        setDescription(builder.description);
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private String name;
        private Boolean required;
        private String defaultValue;
        private String example;
        private String description;

        private Builder() {
        }

        public Builder name(String val) {
            name = val;
            return this;
        }

        public Builder required(Boolean val) {
            required = val;
            return this;
        }

        public Builder defaultValue(String val) {
            defaultValue = val;
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

        public HeaderConfig build() {
            return new HeaderConfig(this);
        }
    }

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
