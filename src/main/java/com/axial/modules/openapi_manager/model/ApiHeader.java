package com.axial.modules.openapi_manager.model;

import lombok.Builder;
import lombok.Getter;

/**
 * Created on December 2022
 */
@Getter
@Builder
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

}
