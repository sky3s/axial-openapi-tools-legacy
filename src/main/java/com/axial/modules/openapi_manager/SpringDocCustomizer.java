package com.axial.modules.openapi_manager;

import com.axial.modules.openapi_manager.model.ApiCustomizer;
import com.axial.modules.openapi_manager.model.config.ApiConfig;
import com.axial.modules.openapi_manager.model.config.ApplicationApiConfig;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created on December 2022
 */
@Configuration
@RequiredArgsConstructor
public class SpringDocCustomizer {

    private final ApplicationApiConfig applicationConfig;

    private final ApiCustomizer apiCustomizer;

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

        final List<ApiConfig> apis = applicationConfig.getApis().values().stream().collect(Collectors.toList());
        final int apiSize = apis.size();

        if (apiIndex >= apiSize) {
            return null;
        }

        final ApiConfig api =  apis.get(apiIndex);

        return GroupedOpenApi.builder()
                .group(api.getGroupName())
                .pathsToMatch(api.getPath())
                .addOpenApiCustomizer(openApi -> SpringDocCustomizerActions.customOpenAPI(openApi, applicationConfig, apiCustomizer))
                .build();
    }



}
