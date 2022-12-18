package com.axial.modules.openapi_manager.old;


//@Configuration
//@RequiredArgsConstructor
//// TODO CHECK LATER
//// @Profile({ AppEnvironment.DEVELOPMENT, AppEnvironment.UAT, AppEnvironment.PREP })
//public class GroupedOpenApiConfig {
//
//    private final ApplicationApiConfig applicationConfig;
//
//    private final OpenApiCustomizer openApiCustomizer;
//
//
//    @Bean
//    GroupedOpenApi addApi1() {
//        return createGroupedOpenApi(1);
//    }
//
//    @Bean
//    GroupedOpenApi addApi2() {
//        return createGroupedOpenApi(2);
//    }
//
//    @Bean
//    GroupedOpenApi addApi3() {
//        return createGroupedOpenApi(3);
//    }
//
//    @Bean
//    GroupedOpenApi addApi4() {
//        return createGroupedOpenApi(4);
//    }
//
//    @Bean
//    GroupedOpenApi addApi5() {
//        return createGroupedOpenApi(5);
//    }
//
//    @Bean
//    GroupedOpenApi addApi6() {
//        return createGroupedOpenApi(6);
//    }
//
//    @Bean
//    GroupedOpenApi addApi7() {
//        return createGroupedOpenApi(7);
//    }
//
//
//    private GroupedOpenApi createGroupedOpenApi(int apiIndex) {
//
//        final List<ApiConfig> apis = applicationConfig.getApis().values().stream().collect(Collectors.toList());
//        final int apiSize = apis.size();
//
//        if (apiIndex >= apiSize) {
//            return null;
//        }
//
//        final ApiConfig api =  apis.get(apiIndex);
//
//        return GroupedOpenApi.builder()
//                .group(api.getGroupName())
//                .pathsToMatch(api.getPath())
//                .addOpenApiCustomizer(openApiCustomizer)
//                .build();
//    }
//
//
//    /*
//    @Bean
//    GroupedOpenApi createGroupedOpenApi1() {
//
//        if (applicationConfig.getApis().values().size() < 1) {
//            return null;
//        }
//
//        return createGroupedOpenApi();
//    }
//
//    @Bean
//    GroupedOpenApi createGroupedOpenApi2() {
//
//        if (applicationConfig.getApis().values().size() < 2) {
//            return null;
//        }
//
//        return createGroupedOpenApi();
//    }
//
//    @Bean
//    GroupedOpenApi createGroupedOpenApi3() {
//
//        if (applicationConfig.getApis().values().size() < 3) {
//            return null;
//        }
//
//        return createGroupedOpenApi();
//    }
//
//    @Bean
//    GroupedOpenApi createGroupedOpenApi4() {
//
//        if (applicationConfig.getApis().values().size() < 4) {
//            return null;
//        }
//
//        return createGroupedOpenApi();
//    }
//
//    @Bean
//    GroupedOpenApi createGroupedOpenApi5() {
//
//        if (applicationConfig.getApis().values().size() < 5) {
//            return null;
//        }
//
//        return createGroupedOpenApi();
//    }
//
//    @Bean
//    GroupedOpenApi createGroupedOpenApi6() {
//
//        if (applicationConfig.getApis().values().size() < 6) {
//            return null;
//        }
//
//        return createGroupedOpenApi();
//    }
//
//    GroupedOpenApi createGroupedOpenApi() {
//
//        final List<ApiConfig> apis = applicationConfig.getApis().values().stream()
//                .filter(api -> !Boolean.TRUE.equals(api.getPointerFlag())).collect(Collectors.toList());
//
//        if (!CollectionUtils.isEmpty(apis)) {
//            if (apis.size() > 1) {
//                apis.get(0).setProcessApiFlag(Boolean.TRUE);
//            } else {
//                applicationConfig.getApis().values().stream().forEach(api -> api.setProcessApiFlag(Boolean.FALSE));
//            }
//
//            final Api api = apis.get(0);
//            return GroupedOpenApi
//                    .builder()
//                    .group(api.getGroupName())
//                    .pathsToMatch(api.getPath())
//                    .addOpenApiCustomiser(openApiCustomiser)
//                    .build();
//        }
//
//        return null;
//    }
//     */
//
//}
