package com.axial.modules.openapi_manager;

import com.axial.modules.openapi_manager.model.ApiCustomizer;
import com.axial.modules.openapi_manager.model.config.ApiConfig;
import com.axial.modules.openapi_manager.model.config.ApplicationApiConfig;
import com.axial.modules.openapi_manager.model.config.HeaderConfig;
import com.axial.modules.openapi_manager.model.config.SecurityHeaderConfig;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.PathItem;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.media.StringSchema;
import io.swagger.v3.oas.models.parameters.HeaderParameter;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.ListUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Created on December 2022
 */
@Component
@RequiredArgsConstructor
public class SpringDocCustomizerActions {

    private static final String HDR_PREFIX = "hdr";

    private static final String COMPONENTS_PREFIX = "#/components/parameters/" + HDR_PREFIX;

    private final ApplicationApiConfig applicationApiConfig;

    private final ApiCustomizer apiCustomizer;

    public void customizeOpenAPI(OpenAPI openAPI, ApiConfig apiConfig) {

        addAppGeneralDetails(openAPI);
        addApiSpecificDetails(openAPI, apiConfig);
        addHeaders(openAPI, apiConfig);
    }

    private void addAppGeneralDetails(OpenAPI openAPI) {

        /**
         * Normal şartlar altında domains nullable olamaz.
         * Config server yml verisi çekilemediğinde ve hatalar oluştuğunda hatayı saptayabilmek için eklendi.
         * istenirse nullable olmayan haline döndürülebilir.
         */
        final List<Server> servers = ListUtils.emptyIfNull(applicationApiConfig.getDomains()).stream().map(new Server()::url).collect(Collectors.toList());


        openAPI.servers(servers);
    }

    private void addApiSpecificDetails(OpenAPI openAPI, ApiConfig apiConfig) {

        openAPI.info(new Info().title(apiConfig.getName() + " - " + applicationApiConfig.getName()).version(applicationApiConfig.getVersion()).description(apiConfig.getDescription()));
    }

    private void addHeaders(OpenAPI openAPI, ApiConfig apiConfig) {

        /**
         * Api Headers
         */
        final Map<String, HeaderConfig> apiHeaderMap = new HashMap<>();
        apiHeaderMap.putAll(mapDefaultApiHeadersToHeaderConfig());
        apiHeaderMap.putAll(MapUtils.emptyIfNull(applicationApiConfig.getCommonHeaders()));
        apiHeaderMap.putAll(MapUtils.emptyIfNull(apiConfig.getHeaders()));

        addComponentsToApiDefinition(openAPI, createHeaderComponents(apiHeaderMap.values().stream().toList()));
        final List<PathItem> pathItems = getAllApisOfDefinition(openAPI, apiConfig);
        pathItems.forEach(pathItem -> addHeaderToPathItem(apiHeaderMap.values().stream().toList(), pathItem));


        /**
         * Security Headers
         */
        final Map<String, SecurityHeaderConfig> securityHeaderMap = new HashMap<>();
        securityHeaderMap.putAll(mapDefaultSecurityHeadersToSecurityHeaderConfig());
        securityHeaderMap.putAll(MapUtils.emptyIfNull(applicationApiConfig.getCommonSecurityHeaders()));
        securityHeaderMap.putAll(MapUtils.emptyIfNull(apiConfig.getSecurityHeaders()));

        addComponentsToApiDefinition(openAPI, createSecurityHeaderComponents(securityHeaderMap.values().stream().toList()));
        addSecurityHeadersToDefinition(openAPI, securityHeaderMap.values().stream().toList());
    }

    private void addComponentsToApiDefinition(OpenAPI openAPI, Components components) {

        if (MapUtils.isNotEmpty(components.getParameters())) {
            components.getParameters().forEach((key, parameter) -> openAPI.getComponents().addParameters(key, parameter));
        }

        if (MapUtils.isNotEmpty(components.getSecuritySchemes())) {
            components.getSecuritySchemes().forEach((key, schema) -> openAPI.getComponents().addSecuritySchemes(key, schema));
        }
    }

    private Components createHeaderComponents(List<HeaderConfig> headers) {

        final Components components = new Components();

        /**
         * Burada yml dosyasında tanımlanan ortak headerlar ile apiye özel headerları oluşturuyoruz.
         */
        if (CollectionUtils.isNotEmpty(headers)) {
            headers.forEach(header -> components.addParameters(HDR_PREFIX + header.getName(), new HeaderParameter().required(header.getRequired()).name(header.getName()).example(header.getExample()).description(header.getDescription()).schema(new StringSchema())));
        }

        return components;
    }

    private Components createSecurityHeaderComponents(List<SecurityHeaderConfig> securityHeaders) {

        final Components components = new Components();

        /**
         * Burada yml dosyasında tanımlanan ortak headerlar ile apiye özel headerları oluşturuyoruz.
         */
        if (CollectionUtils.isNotEmpty(securityHeaders)) {
            securityHeaders.forEach(securityHeader -> components.addSecuritySchemes(securityHeader.getKey(), new SecurityScheme().type(SecurityScheme.Type.APIKEY).in(SecurityScheme.In.HEADER).name(securityHeader.getName()).description(securityHeader.getDescription())));
        }

        return components;
    }

    private void addSecurityHeadersToDefinition(OpenAPI openAPI, List<SecurityHeaderConfig> securityHeaders) {

        /**
         * Güvenlik header'larını Authorize alanında göstermek için gereken ikinci adım
         */
        final SecurityRequirement securityRequirement = new SecurityRequirement();

        securityHeaders.forEach(securityHeader -> securityRequirement.addList(securityHeader.getKey()));

        if (Objects.isNull(openAPI.getSecurity())) {
            final List<SecurityRequirement> list = new ArrayList<>();
            list.add(securityRequirement);
            openAPI.security(list);
        } else {
            openAPI.getSecurity().add(securityRequirement);
        }
    }

    private void addHeaderToPathItem(List<HeaderConfig> headers, PathItem pathItem) {

        pathItem.readOperations().forEach(operation -> {
            if (CollectionUtils.isNotEmpty(headers)) {
                headers.forEach(header -> {
                    final String componentRef = COMPONENTS_PREFIX + header.getName();
                    /**
                     Bu header zaten varsa yoksayılacak. Var olan bir header tekrar eklenince duplike oluyor. Definition değiştirince hedaerlar çoklandığı için bunu yapıyoruz.
                     */
                    if (Optional.ofNullable(operation.getParameters()).orElse(new ArrayList<>()).stream().anyMatch(op -> StringUtils.equals(header.getName(), op.getName()))) {
                        return;
                    }

                    /**
                     * Yeni header eklenecek.
                     */
                    operation.addParametersItem(new HeaderParameter().$ref(componentRef).name(header.getName()).description(header.getDescription()).example(header.getExample()).required(header.getRequired()));
                });
            }
        });
    }

    private List<PathItem> getAllApisOfDefinition(OpenAPI openApi, ApiConfig apiConfig) {

        final String apiUrlPrefix = apiConfig.getPath().replace("*", "");
        final List<PathItem> pathItems = new ArrayList<>();

        /**
         * Prepare header - api map for API specific header assignment
         */
        openApi.getPaths().forEach((openApiPathItemUrl, pathItem) -> {
            if (openApiPathItemUrl.startsWith(apiUrlPrefix)) {
                pathItems.add(pathItem);
            }
        });

        return pathItems;
    }

    private Map<String, HeaderConfig> mapDefaultApiHeadersToHeaderConfig() {
        return ListUtils.emptyIfNull(apiCustomizer.getApiHeaders()).stream().map(header -> HeaderConfig.builder().name(header.getName()).required(header.isRequired()).description(header.getDescription()).defaultValue(header.getDefaultValue()).example(header.getDefaultValue()).build()).collect(Collectors.toMap(HeaderConfig::getName, Function.identity()));
    }

    private Map<String, SecurityHeaderConfig> mapDefaultSecurityHeadersToSecurityHeaderConfig() {
        return ListUtils.emptyIfNull(apiCustomizer.getSecurityHeaders()).stream().map(header -> SecurityHeaderConfig.builder().key(header.getKey()).name(header.getName()).example(header.getDefaultValue()).description(header.getDescription()).build()).collect(Collectors.toMap(SecurityHeaderConfig::getKey, Function.identity()));
    }

}
