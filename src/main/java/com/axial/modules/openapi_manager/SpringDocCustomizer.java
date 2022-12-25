package com.axial.modules.openapi_manager;

import com.axial.modules.openapi_manager.model.config.ApiConfig;
import com.axial.modules.openapi_manager.model.config.ApplicationApiConfig;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created on December 2022
 */
@Configuration
@PropertySource("classpath:default-springdoc-config.properties")
public class SpringDocCustomizer {

    private final ApplicationApiConfig applicationConfig;

    private final SpringDocCustomizerActions customizerActions;

    public SpringDocCustomizer(ApplicationApiConfig applicationConfig, SpringDocCustomizerActions customizerActions) {
        this.applicationConfig = applicationConfig;
        this.customizerActions = customizerActions;
    }

    @Bean
    GroupedOpenApi addApi0() {
        return createGroupedOpenApi(0);
    }

    @Bean
    GroupedOpenApi addApi1() {
        return createGroupedOpenApi(1);
    }

    @Bean
    GroupedOpenApi addApi2() {
        return createGroupedOpenApi(2);
    }

    @Bean
    GroupedOpenApi addApi3() {
        return createGroupedOpenApi(3);
    }

    @Bean
    GroupedOpenApi addApi4() {
        return createGroupedOpenApi(4);
    }

    @Bean
    GroupedOpenApi addApi5() {
        return createGroupedOpenApi(5);
    }

    @Bean
    GroupedOpenApi addApi6() {
        return createGroupedOpenApi(6);
    }

    @Bean
    GroupedOpenApi addApi7() {
        return createGroupedOpenApi(7);
    }

    private GroupedOpenApi createGroupedOpenApi(int apiIndex) {

        final List<ApiConfig> apis = OpenApiUtils.emptyIfNull(applicationConfig.getApis()).values().stream().collect(Collectors.toList());
        final int apiSize = apis.size();

        if (apiIndex >= apiSize) {
            return null;
        }

        final ApiConfig api = apis.get(apiIndex);

        return GroupedOpenApi.builder()
                .group(api.getGroupName())
                .pathsToMatch(api.getPath())
                .addOpenApiCustomiser(openApi ->
                        customizerActions.customizeOpenAPI(openApi, api))
                .build();
    }

}
