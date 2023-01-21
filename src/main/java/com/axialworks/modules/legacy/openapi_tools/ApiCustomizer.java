package com.axialworks.modules.legacy.openapi_tools;

import com.axialworks.modules.legacy.openapi_tools.model.ApiHeader;
import com.axialworks.modules.legacy.openapi_tools.model.config.SecurityHeaderConfig;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * This interface can be using for defining or modifying default headers,
 * also can be using in your project modules such as security modules.
 */
public interface ApiCustomizer {

    default List<ApiHeader> getDefaultHeaders() {

        final List<ApiHeader> apiHeaders = Arrays.asList(
                ApiHeader
                        .builder()
                        .key("XForwardedFor")
                        .name("X-FORWARDED-FOR")
                        .defaultValue("0.0.0.0")
                        .required(false)
                        .description("Redirect IP address")
                        .defaultApiHeader(false)
                        .defaultSecurityHeader(false)
                        .build()
        );

        return Collections.unmodifiableList(apiHeaders);
    }

    default List<ApiHeader> getHeaders() {
        return new ArrayList<>(getDefaultHeaders());
    }

    default List<ApiHeader> getApiHeaders() {
        return Collections.unmodifiableList(OpenApiDataUtils.emptyIfNull(getHeaders())
                .stream().filter(ApiHeader::isDefaultApiHeader).collect(Collectors.toList()));
    }

    default List<SecurityHeaderConfig> getSecurityHeaders() {
        return Collections.unmodifiableList(OpenApiDataUtils.emptyIfNull(getHeaders()).stream()
                .filter(ApiHeader::isDefaultSecurityHeader)
                .map(OpenApiMapper::convertApiHeaderToSecurityHeaderConfig)
                .collect(Collectors.toList()));
    }

}
