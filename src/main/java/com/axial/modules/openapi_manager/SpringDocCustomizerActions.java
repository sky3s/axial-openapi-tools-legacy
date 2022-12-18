package com.axial.modules.openapi_manager;

import com.axial.modules.openapi_manager.model.ApiCustomizer;
import com.axial.modules.openapi_manager.model.OpenApiHeader;
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
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.commons.collections4.ListUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created on December 2022
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SpringDocCustomizerActions {


    public static final String DEFAULT_API_DESCRIPTION = "Uygulama Servisleri";
    private static final String HDR_PREFIX = "hdr";

    private static final String COMPONENTS_PREFIX = "#/components/parameters/" + HDR_PREFIX;
    private static final String API_IDENTIFIER_KEY = "x-api-id";


    public static OpenAPI customOpenAPI(OpenAPI openAPI, ApplicationApiConfig applicationApiConfig, ApiCustomizer apiCustomizer) {

        /**
         * Normal şartlar altında domains nullable olamaz.
         * Config server yml verisi çekilemediğinde ve hatalar oluştuğunda hatayı saptayabilmek için eklendi.
         * istenirse nullable olmayan haline döndürülebilir.
         */
        final List<Server> servers = ListUtils.emptyIfNull(applicationApiConfig.getDomains())
                .stream()
                .map(new Server()::url)
                .collect(Collectors.toList());

        /**
         * Aktif Api için yml konfigürasyonu
         * Nedense NULL geldiği durumlar oluyor, bunu kullanırken dikkat etmek lazım
         */
        final Optional<ApiConfig> currentApiConfigOpt = applicationApiConfig
                .getApis()
                .values()
                .stream()
                .filter(api -> !Boolean.TRUE.equals(api.getPointerFlag()))
                .findFirst();

        /**
         * Ortak headerları ve api bazlı headerleri tek yerde topluyoruz ve topluca oluşturuyoruz.
         * Apilere ekleme işlemi bir sonraki adımda yapılacak.
         */
        Map<String, HeaderConfig> headerMap = new HashMap<>();
        //headerMap.putAll(mapDefaultHeadersToHeaderConfig(apiCustomizer));
        headerMap.putAll(applicationApiConfig.getCommonHeaders());

        if (currentApiConfigOpt.isPresent() && MapUtils.isNotEmpty(currentApiConfigOpt.get().getHeaders())) {
            headerMap.putAll(currentApiConfigOpt.get().getHeaders());
        }

        openAPI
                .servers(servers)
                .components(createComponents(headerMap));

        if (currentApiConfigOpt.isPresent()) {
            final ApiConfig apiConfig = currentApiConfigOpt.get();

            openAPI.info(new Info()
                    .title(apiConfig.getName() + " - " + applicationApiConfig.getName())
                    .version(applicationApiConfig.getVersion())
                    .description(apiConfig.getDescription())
            );

            customizeHeaders(openAPI, applicationApiConfig, apiConfig);

            /**
             * API özelleştirmeleri için herhangi bir noktada bu API'nin hangi API olduğu anlamak için kullanılacak eşsiz tanımlayıcı.
             */
            setApiIdentifier(openAPI, apiConfig.getApiId());
            apiConfig.setPointerFlag(Boolean.TRUE);
        }

        /**
         * Authorize alanında gösterilecek headerların eklenmesi
         * Yml'ye yazılanlar setApiIdentifier() ile openApi üzerine eklendikten sonra çalışır.
         * Bu nedenle önce bu metod çalışmalı!
         */
       //addDefaultSecurityHeaders(openAPI, apiCustomizer);
       addAllSecurityHeadersFromYml(openAPI, applicationApiConfig);

        return openAPI;
    }

    /**
     * Uygulama koduna gömülecek default security header'ları varsa burada alınacaklar.
     */
    private static void addDefaultSecurityHeaders(OpenAPI openAPI, ApiCustomizer apiCustomizer) {

        final Map<String, SecurityHeaderConfig> defaultSecurityHeaders = new HashMap<>();

        ListUtils.emptyIfNull(apiCustomizer.getHeaders()).stream().filter(OpenApiHeader::isDefaultSecurityHeader).forEach(header -> {
            final SecurityHeaderConfig securityHeader = SecurityHeaderConfig.builder().key(header.getKey())
                    .name(header.getName()).example(header.getDefaultValue()).description(header.getDescription()).build();
            defaultSecurityHeaders.put(securityHeader.getKey(), securityHeader);
        });

        addSecurityHeaders(openAPI, defaultSecurityHeaders);
    }

    private static void addAllSecurityHeadersFromYml(OpenAPI openAPI, ApplicationApiConfig applicationConfig) {

        final ApiConfig currentApiConfig = getApiConfigByIdentifier(openAPI, applicationConfig);

        Stream<Map.Entry<String, SecurityHeaderConfig>> securityHeaderStream = MapUtils
                .emptyIfNull(applicationConfig.getCommonSecurityHeaders())
                .entrySet()
                .stream();
        if (Objects.nonNull(currentApiConfig) && MapUtils.isNotEmpty(currentApiConfig.getSecurityHeaders())) {
            securityHeaderStream = Stream.concat(securityHeaderStream, currentApiConfig.getSecurityHeaders().entrySet().stream());
        }

        Map<String, SecurityHeaderConfig> securityHeaderMap = securityHeaderStream.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        addSecurityHeaders(openAPI, securityHeaderMap);
    }

    private static void addSecurityHeaders(OpenAPI openAPI, Map<String, SecurityHeaderConfig> securityHeaderMap) {

        final Components components = openAPI.getComponents();

        /**
         * Ek güvenlik header'larını Authorize alanında göstermek için gereken ilk adım
         */
        securityHeaderMap.forEach((securityHeaderKey, securityHeader) -> {
            components.addSecuritySchemes(securityHeader.getKey(), new SecurityScheme()
                    .type(SecurityScheme.Type.APIKEY)
                    .in(SecurityScheme.In.HEADER)
                    .name(securityHeader.getKey())
                    .description(securityHeader.getDescription()));
        });

        /**
         * Güvenlik header'larını Authorize alanında göstermek için gereken ikinci adım
         */
        final SecurityRequirement securityRequirement = new SecurityRequirement();

        securityHeaderMap.forEach((securityHeaderKey, securityHeader) -> {
            securityRequirement.addList(securityHeader.getKey());
        });

        if (Objects.isNull(openAPI.getSecurity())) {
            final List<SecurityRequirement> list = new ArrayList<>();
            list.add(securityRequirement);
            openAPI.security(list);
        } else {
            openAPI.getSecurity().add(securityRequirement);
        }
    }

    private static Components createComponents(Map<String, HeaderConfig> headers) {

        final Components components = new Components();

        /**
         * Burada yml dosyasında tanımlanan ortak headerlar ile apiye özel headerları oluşturuyoruz.
         */
        if (MapUtils.isNotEmpty(headers)) {
            headers.forEach((headerKey, header) -> {
                components.addParameters(HDR_PREFIX + header.getName(), new HeaderParameter()
                        .required(header.getRequired())
                        .name(header.getName())
                        .example(header.getExample())
                        .description(header.getDescription())
                        .schema(new StringSchema()));
            });
        }

        return components;
    }

    public static void customizeHeaders(OpenAPI openApi, ApplicationApiConfig applicationApiConfig, ApiConfig apiConfig) {

        final String apiUrlPrefix = apiConfig.getPath().replace("*", "");
        final List<PathItem> pathItemList = new ArrayList<>();

        /**
         * Prepare header - api map for API specific header assignment
         */
        openApi.getPaths().forEach((openApiPathItemUrl, pathItem) -> {
            if (openApiPathItemUrl.startsWith(apiUrlPrefix)) {
                pathItemList.add(pathItem);
            }
        });

        /**
         * Add API specific headers to related apis
         */
        pathItemList.stream().forEach(pathItem -> addHeaderToPathItem(apiConfig.getHeaders(), pathItem));

        /**
         * Add common headers to all apis
         */
        final Map<String, HeaderConfig> commonHeaders = applicationApiConfig.getCommonHeaders();
        openApi
                .getPaths()
                .values()
                .stream()
                .forEach(pathItem -> addHeaderToPathItem(commonHeaders, pathItem));
    }

    private static void addHeaderToPathItem(Map<String, HeaderConfig> headerMap, PathItem pathItem) {

        pathItem.readOperations().stream().forEach(operation -> {
            if (MapUtils.isNotEmpty(headerMap)) {
                headerMap.forEach((headerKey, header) -> {
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

    private static void setApiIdentifier(OpenAPI openAPI, String apiIdentifier) {

        Map<String, Object> extensions = new HashMap<>();
        extensions.put(API_IDENTIFIER_KEY, apiIdentifier);
        openAPI.setExtensions(extensions);
    }

    private static String getApiIdentifier(OpenAPI openAPI) {

        final Map<String, Object> extensions = openAPI.getExtensions();
        if (MapUtils.isNotEmpty(extensions)) {
            return (String) extensions.get(API_IDENTIFIER_KEY);
        }
        return null;
    }

    private static Map<String, HeaderConfig> mapDefaultHeadersToHeaderConfig(ApiCustomizer apiCustomizer) {
        return ListUtils.emptyIfNull(apiCustomizer.getHeaders()).stream().filter(OpenApiHeader::isDefaultApiHeader)
                .map(header -> HeaderConfig.builder()
                        .name(header.getName()).required(header.isRequired())
                        .description(header.getDescription()).defaultValue(header.getDefaultValue())
                        .example(header.getDefaultValue()).build()).collect(Collectors.toMap(HeaderConfig::getName, Function.identity()));
    }

    private static ApiConfig getApiConfigByIdentifier(OpenAPI openAPI, ApplicationApiConfig applicationConfig) {

        final String apiIdentifier = getApiIdentifier(openAPI);
        return getApiConfigByIdentifier(apiIdentifier, applicationConfig);
    }

    private static ApiConfig getApiConfigByIdentifier(String apiIdentifier, ApplicationApiConfig applicationConfig) {

        if (StringUtils.isBlank(apiIdentifier)) {
            return null;
        }
        return applicationConfig.getApis().values().stream().filter(api -> api.getApiId().equals(apiIdentifier)).findFirst().orElse(null);
    }

}
