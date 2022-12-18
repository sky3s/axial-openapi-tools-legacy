package com.axial.modules.openapi_manager.old;


//@Configuration
//@RequiredArgsConstructor
//// TODO CHECK LATER
////@Profile({ AppEnvironment.DEVELOPMENT, AppEnvironment.UAT, AppEnvironment.PREP })
//public class SwaggerConfig {
//
//    public static final String DEFAULT_API_DESCRIPTION = "Uygulama Servisleri";
//    private static final String HDR_PREFIX = "hdr";
//    private static final String COMPONENTS_PREFIX = "#/components/parameters/" + HDR_PREFIX;
//    private static final String API_IDENTIFIER_KEY = "x-api-id";
//
//
//    private final ApplicationApiConfig applicationApiConfig;
//
//    private final ApiCustomizer apiCustomizer;
//
//
//    @Bean
//
//
//    /**
//     * Uygulama koduna gömülecek default security header'ları varsa burada alınacaklar.
//     */
//    private void addDefaultSecurityHeaders(OpenAPI openAPI) {
//
//        final Map<String, SecurityHeaderConfig> defaultSecurityHeaders = new HashMap<>();
//
//        DataUtils.emptyIfNull(apiCustomizer.getHeaders()).stream().filter(OpenApiHeader::isDefaultSecurityHeader).forEach(header -> {
//            final SecurityHeaderConfig securityHeader = SecurityHeaderConfig.builder().key(header.getKey())
//                    .name(header.getName()).example(header.getDefaultValue()).description(header.getDescription()).build();
//            defaultSecurityHeaders.put(securityHeader.getKey(), securityHeader);
//        });
//
//        addSecurityHeaders(openAPI, defaultSecurityHeaders);
//    }
//
//    private void addAllSecurityHeadersFromYml(OpenAPI openAPI) {
//
//        final ApiConfig currentApiConfig = getApiConfigByIdentifier(openAPI);
//
//        Stream<Map.Entry<String, SecurityHeaderConfig>> securityHeaderStream = DataUtils
//                .emptyIfNull(applicationApiConfig.getCommonSecurityHeaders())
//                .entrySet()
//                .stream();
//        if (Objects.nonNull(currentApiConfig) && !DataUtils.isEmpty(currentApiConfig.getSecurityHeaders())) {
//            securityHeaderStream = Stream.concat(securityHeaderStream, currentApiConfig.getSecurityHeaders().entrySet().stream());
//        }
//
//        Map<String, SecurityHeaderConfig> securityHeaderMap = securityHeaderStream.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
//
//        addSecurityHeaders(openAPI, securityHeaderMap);
//    }
//
//    private void addSecurityHeaders(OpenAPI openAPI, Map<String, SecurityHeaderConfig> securityHeaderMap) {
//
//        final Components components = openAPI.getComponents();
//
//        /**
//         * Ek güvenlik header'larını Authorize alanında göstermek için gereken ilk adım
//         */
//        securityHeaderMap.forEach((securityHeaderKey, securityHeader) -> {
//            components.addSecuritySchemes(securityHeader.getKey(), new SecurityScheme()
//                    .type(SecurityScheme.Type.APIKEY)
//                    .in(SecurityScheme.In.HEADER)
//                    .name(securityHeader.getKey())
//                    .description(securityHeader.getDescription()));
//        });
//
//        /**
//         * Güvenlik header'larını Authorize alanında göstermek için gereken ikinci adım
//         */
//        final SecurityRequirement securityRequirement = new SecurityRequirement();
//
//        securityHeaderMap.forEach((securityHeaderKey, securityHeader) -> {
//            securityRequirement.addList(securityHeader.getKey());
//        });
//
//        if (Objects.isNull(openAPI.getSecurity())) {
//            final List<SecurityRequirement> list = new ArrayList<>();
//            list.add(securityRequirement);
//            openAPI.security(list);
//        } else {
//            openAPI.getSecurity().add(securityRequirement);
//        }
//    }
//
//    private Components createComponents(Map<String, HeaderConfig> headers) {
//
//        final Components components = new Components();
//
//        /**
//         * Burada yml dosyasında tanımlanan ortak headerlar ile apiye özel headerları oluşturuyoruz.
//         */
//        if (DataUtils.isNotEmpty(headers)) {
//            headers.forEach((headerKey, header) -> {
//                components.addParameters(HDR_PREFIX + header.getName(), new HeaderParameter()
//                        .required(header.getRequired())
//                        .name(header.getName())
//                        .example(header.getExample())
//                        .description(header.getDescription())
//                        .schema(new StringSchema()));
//            });
//        }
//
//        return components;
//    }
//
//    @Bean
//    public OpenApiCustomizer customOpenAPIHeaderCustomiser() {
//
//        /*
//        final Map<String, Pair<ApiConfig, List<PathItem>>> baseMap = new HashMap<>();
//        applicationApiConfig.getApis().entrySet().forEach(apiEntry -> {
//            final List<PathItem> pathItemList = new ArrayList<>();
//            final Pair<ApiConfig, List<PathItem>> pair = new Pair<>(apiEntry.getValue(), pathItemList);
//            baseMap.put(apiEntry.getValue().getPath().replace("*", ""), pair);
//        });
//         */
//
//        //@formatter:off
//        return openApi -> {
//
//            final ApiConfig apiConfig = getApiConfigByIdentifier(openApi);
//
//            final String apiUrlPrefix = apiConfig.getPath().replace("*", "");
//            final List<PathItem> pathItemList = new ArrayList<>();
//
//            /**
//             * Prepare header - api map for API specific header assignment
//             */
//            openApi.getPaths().forEach((openApiPathItemUrl, pathItem) -> {
//                if (openApiPathItemUrl.startsWith(apiUrlPrefix)) {
//                    pathItemList.add(pathItem);
//                }
//            });
//
//            /**
//             * Add API specific headers to related apis
//             */
//            pathItemList.stream().forEach(pathItem -> addHeaderToPathItem(apiConfig.getHeaders(), pathItem));
//
//            /**
//             * Add common headers to all apis
//             */
//            final Map<String, HeaderConfig> commonHeaders = applicationApiConfig.getCommonHeaders();
//            openApi
//                    .getPaths()
//                    .values()
//                    .stream()
//                    .forEach(pathItem -> addHeaderToPathItem(commonHeaders, pathItem));
//
//            openApi.getInfo().description(DataUtils.defaultString(Optional.ofNullable(apiConfig).map(ApiConfig::getDescription).orElse(DEFAULT_API_DESCRIPTION)));
//        };
//        //@formatter:on
//    }
//
//    private void addHeaderToPathItem(Map<String, HeaderConfig> headerMap, PathItem pathItem) {
//        pathItem.readOperations().stream().forEach(operation -> {
//            if (DataUtils.isNotEmpty(headerMap)) {
//                headerMap.forEach((headerKey, header) -> operation.addParametersItem(new HeaderParameter().$ref(COMPONENTS_PREFIX + header.getName())));
//            }
//        });
//    }
//
//
//    /*
//    private void addHeaderToPathItem(Map<String, HeaderConfig> headerMap, PathItem pathItem) {
//
//        pathItem.readOperations().stream().forEach(operation -> {
//            if (DataUtils.isNotEmpty(headerMap)) {
//                headerMap.forEach((headerKey, header) -> {
//                    final String componentRef = COMPONENTS_PREFIX + header.getName();
//                    /**
//                     Bu header zaten varsa yoksayılacak. Var olan bir header tekrar eklenince duplike oluyor. Definition değiştirince hedaerlar çoklandığı için bunu yapıyoruz.
//                     */ /*
//                    if (Optional.ofNullable(operation.getParameters()).orElse(new ArrayList<>()).stream().anyMatch(op -> StringUtils.equals(header.getKey(), op.getName()))) {
//                        return;
//                    }
//
//                    /**
//                     * Yeni header eklenecek.
//                     *//*
//                    operation.addParametersItem(new HeaderParameter().$ref(componentRef).name(header.getKey()).description(header.getDescription()).example(header.getExample()).required(header.getRequired()));
//                });
//            }
//        });
//    }
//    */
//
//    private void setApiIdentifier(OpenAPI openAPI, String apiIdentifier) {
//
//        Map<String, Object> extensions = new HashMap<>();
//        extensions.put(API_IDENTIFIER_KEY, apiIdentifier);
//        openAPI.setExtensions(extensions);
//    }
//
//    private String getApiIdentifier(OpenAPI openAPI) {
//
//        final Map<String, Object> extensions = openAPI.getExtensions();
//        if (!DataUtils.isEmpty(extensions)) {
//            return (String) extensions.get(API_IDENTIFIER_KEY);
//        }
//        return null;
//    }
//
//    private ApiConfig getApiConfigByIdentifier(OpenAPI openAPI) {
//
//        final String apiIdentifier = getApiIdentifier(openAPI);
//        return getApiConfigByIdentifier(apiIdentifier);
//    }
//
//    private ApiConfig getApiConfigByIdentifier(String apiIdentifier) {
//
//        if (DataUtils.isBlank(apiIdentifier)) {
//            return null;
//        }
//        return applicationApiConfig.getApis().values().stream().filter(api -> api.getApiId().equals(apiIdentifier)).findFirst().orElse(null);
//    }
//
//    private Map<String, HeaderConfig> mapDefaultHeadersToHeaderConfig() {
//        return DataUtils.emptyIfNull(apiCustomizer.getHeaders()).stream().filter(OpenApiHeader::isDefaultApiHeader)
//                .map(header -> HeaderConfig.builder()
//                        .name(header.getName()).required(header.isRequired())
//                        .description(header.getDescription()).defaultValue(header.getDefaultValue())
//                        .example(header.getDefaultValue()).build()).collect(Collectors.toMap(HeaderConfig::getName, Function.identity()));
//    }
//
//}
