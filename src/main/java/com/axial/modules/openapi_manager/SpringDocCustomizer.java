package com.axial.modules.openapi_manager;

import com.axial.modules.openapi_manager.model.config.ApiConfig;
import com.axial.modules.openapi_manager.model.config.ApplicationApiConfig;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.util.List;

/**
 * Created on December 2022
 */
@PropertySource("classpath:default-springdoc-config.properties")
@Configuration
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


    private GroupedOpenApi createGroupedOpenApi(int apiIndex) {

        final List<ApiConfig> apis = OpenApiUtils.emptyIfNull(applicationConfig.getApis()).values().stream().toList();
        final int apiSize = apis.size();

        if (apiIndex >= apiSize) {
            return null;
        }

        final ApiConfig api = apis.get(apiIndex);

        return GroupedOpenApi.builder()
                .group(api.getGroupName())
                .pathsToMatch(api.getPath())
                .addOpenApiCustomizer(openApi ->
                        customizerActions.customizeOpenAPI(openApi, api))
                .build();
    }

}
