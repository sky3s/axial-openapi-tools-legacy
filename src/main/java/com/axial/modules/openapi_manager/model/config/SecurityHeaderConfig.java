package com.axial.modules.openapi_manager.model.config;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Builder
public class SecurityHeaderConfig {

    private String key;

    private String name;

    private String example;

    private String description;

}