package com.axial.modules.openapi_manager.model;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created on December 2022
 */
public interface ApiCustomizer {

    default List<OpenApiHeader> getDefaultHeaders() {

        return Collections.unmodifiableList(Arrays.asList(
                OpenApiHeader.builder().key("ApiKeyAuth").name("X-ApiKey")
                        .defaultValue("ABCD123456").required(true).description("Enter your apikey")
                        .defaultApiHeader(false).defaultSecurityHeader(true).build(),

                OpenApiHeader.builder().key("Username").name("X-Username")
                        .defaultValue("dummyUser").required(false).description("Enter your username")
                        .defaultApiHeader(false).defaultSecurityHeader(false).build(),

                OpenApiHeader.builder().key("AcceptLanguage").name("Accept-Language")
                        .defaultValue("tr").required(false).description("Requested language")
                        .defaultApiHeader(true).defaultSecurityHeader(false).build(),

                OpenApiHeader.builder().key("XForwardedFor").name("X-FORWARDED-FOR")
                        .defaultValue("0.0.0.0").required(false).description("Redirect IP address")
                        .defaultApiHeader(false).defaultSecurityHeader(false).build()
        ));

    }

    default List<OpenApiHeader> getHeaders() {

        return getDefaultHeaders();
    }

}
