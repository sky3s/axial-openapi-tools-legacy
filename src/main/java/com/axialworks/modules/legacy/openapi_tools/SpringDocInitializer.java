package com.axialworks.modules.legacy.openapi_tools;

import com.axialworks.modules.legacy.openapi_tools.model.config.ApiConfig;
import com.axialworks.modules.legacy.openapi_tools.model.config.ApplicationApiConfig;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import javax.annotation.PostConstruct;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Configuration
@PropertySource("classpath:default-springdoc-config.properties")
public class SpringDocInitializer {

    private final ConfigurableListableBeanFactory beanFactory;

    private final ApplicationApiConfig applicationApiConfig;

    private final SpringDocCustomizerActions customizerActions;

    public SpringDocInitializer(ConfigurableListableBeanFactory beanFactory,
                                ApplicationApiConfig applicationApiConfig,
                                SpringDocCustomizerActions customizerActions) {
        this.beanFactory = beanFactory;
        this.applicationApiConfig = applicationApiConfig;
        this.customizerActions = customizerActions;
    }

    @PostConstruct
    private void initOps() {
        addCustomizers();
    }

    private void addCustomizers() {

        final Map<String, ApiConfig> apiMap = applicationApiConfig
                .getApis()
                .values()
                .stream()
                .collect(Collectors.toMap(ApiConfig::getName, Function.identity()));

        OpenApiDataUtils
                .emptyIfNull(beanFactory
                        .getBeansOfType(GroupedOpenApi.class))
                .values().
                forEach(groupedOpenApi ->
                        groupedOpenApi
                                .getOpenApiCustomisers()
                                .add(openApi ->
                                        customizerActions.customizeOpenAPI(openApi, apiMap.get(groupedOpenApi.getGroup())))
                );
    }

}
