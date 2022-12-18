package com.axial.modules.openapi_manager.model.config;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Builder
public class HeaderConfig {

    private String name;

    private Boolean required;

    private String defaultValue;

    private String example;

    private String description;

}
