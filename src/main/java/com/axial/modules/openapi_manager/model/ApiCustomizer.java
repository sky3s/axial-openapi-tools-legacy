package com.axial.modules.openapi_manager.model;

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
                ApiHeader.builder().key("ApiKeyAuth").name("X-ApiKey")
                        .defaultValue("ABCD123456").required(true).description("Enter your apikey")
                        .defaultApiHeader(false).defaultSecurityHeader(true).build(),

                ApiHeader.builder().key("Username").name("X-Username")
                        .defaultValue("dummyUser").required(false).description("Enter your username")
                        .defaultApiHeader(false).defaultSecurityHeader(false).build(),

                ApiHeader.builder().key("AcceptLanguage").name("Accept-Language")
                        .defaultValue("tr").required(false).description("Requested language")
                        .defaultApiHeader(true).defaultSecurityHeader(false).build(),

                ApiHeader.builder().key("XForwardedFor").name("X-FORWARDED-FOR")
                        .defaultValue("0.0.0.0").required(false).description("Redirect IP address")
                        .defaultApiHeader(false).defaultSecurityHeader(false).build()
        ));

    }

    default List<ApiHeader> getHeaders() {
        return new ArrayList<>(getDefaultHeaders());
    }

    default List<ApiHeader> getApiHeaders() {
        return new ArrayList<>(getDefaultApiHeaders());
    }

    default List<ApiHeader> getSecurityHeaders() {
        return new ArrayList<>(getDefaultSecurityHeaders());
    }

    default List<ApiHeader> getDefaultApiHeaders() {
        return ListUtils.emptyIfNull(getHeaders()).stream().filter(ApiHeader::isDefaultApiHeader).collect(Collectors.toUnmodifiableList());
    }

    default List<ApiHeader> getDefaultSecurityHeaders() {
        return ListUtils.emptyIfNull(getHeaders()).stream().filter(ApiHeader::isDefaultSecurityHeader).collect(Collectors.toUnmodifiableList());
    }

}
