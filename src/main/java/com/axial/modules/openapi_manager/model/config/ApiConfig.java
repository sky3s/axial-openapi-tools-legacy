package com.axial.modules.openapi_manager.model.config;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
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

}