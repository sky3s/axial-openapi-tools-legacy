package com.axial.modules.openapi_manager.model;

import com.axial.modules.openapi_manager.model.config.SecurityHeaderConfig;
import org.apache.commons.collections4.ListUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created on December 2022
 */
public interface ApiCustomizer {

    default List<ApiHeader> getDefaultHeaders() {

        return Collections.unmodifiableList(Arrays.asList(

                ApiHeader.builder().key("XForwardedFor").name("X-FORWARDED-FOR")
                        .defaultValue("0.0.0.0").required(false).description("Redirect IP address")
                        .defaultApiHeader(false).defaultSecurityHeader(false).build()
        ));

    }

    default List<ApiHeader> getHeaders() {
        return new ArrayList<>(getDefaultHeaders());
    }

    default List<ApiHeader> getApiHeaders() {
        return ListUtils.emptyIfNull(getHeaders()).stream().filter(ApiHeader::isDefaultApiHeader).collect(Collectors.toUnmodifiableList());
    }

    default List<SecurityHeaderConfig> getSecurityHeaders() {
        return ListUtils.emptyIfNull(getHeaders()).stream()
                .filter(ApiHeader::isDefaultSecurityHeader)
                .map(header ->
                        SecurityHeaderConfig.builder()
                                .key(header.getKey())
                                .name(header.getName())
                                .example(header.getDefaultValue())
                                .description(header.getDescription())
                                .build())
                .collect(Collectors.toUnmodifiableList());
    }

}
