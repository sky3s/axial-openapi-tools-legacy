package com.axial.modules.openapi_manager.old;

import lombok.extern.slf4j.Slf4j;

/**
 * Created on December, 2022
 */
@Slf4j
//@Aspect
//@Component
public class ServiceAspect {
//
//    private static final String RESPONSE_TIME = "X-ResponseTime";
//
//    private static final String API_POINT_CUT = "execution(* (@org.springframework.web.bind.annotation.RestController *).*(..))";
//
//    private static final String CLIENT_POINT_CUT = "execution(* org.springframework.web.client.RestTemplate.*(..))";
//
//
//    /**
//     * ApiPointCut for catching controller methods
//     */
//    @Pointcut(API_POINT_CUT)
//    private void controller() { }
//
//    /**
//     * ClientPointCut for catching Rest Template Client methods
//     */
//    @Pointcut(CLIENT_POINT_CUT)
//    private void restTemplateClient() { }
//
//
//    /**
//     * On chain processing
//     *
//     * @param joinPoint
//     * @return
//     * @throws Throwable
//     */
//    @Around("controller()")
//    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
//
//        final StopWatch stopWatch = StopWatch.createStarted();
//
//        final Object[] args = joinPoint.getArgs();
//
//        try {
//            final Object proceed = joinPoint.proceed();
//
//            /*
//            if (proceed instanceof ResponseEntity) {
//                final ResponseEntity<?> responseEntity = (ResponseEntity<?>) proceed;
//                if (responseEntity.hasBody() && responseEntity.getBody() instanceof SuccessResponse<?>) {
//
//                }
//            }
//             */
//
//            addExecutionTimeToResponseHeader(stopWatch);
//            return proceed;
//        } catch (Exception ex) {
//            addExecutionTimeToResponseHeader(stopWatch);
//            log.error(ex.getMessage(), ex);
//            throw ex;
//        }
//
//    }
//
//    private static void addExecutionTimeToResponseHeader(StopWatch stopWatch) {
//
//        final long time = stopWatch.getTime(TimeUnit.MILLISECONDS);
//
//        // @formatter:off
//        Optional.ofNullable(RequestContextHolder.currentRequestAttributes())
//                .map(ra -> (ServletRequestAttributes) ra)
//                .map(ServletRequestAttributes::getResponse)
//                .ifPresent(httpServletResponse -> httpServletResponse.addHeader(RESPONSE_TIME, String.valueOf(time)));
//        // @formatter:on
//    }
//
//    /**
//     * After service returned this pointcut will be triggered
//     *
//     * @param retVal
//     */
//    @AfterReturning(value = "controller()", returning = "retVal")
//    private void controllerReturn(ResponseEntity<?> retVal) {
//
//        if (Objects.nonNull(retVal) && retVal.hasBody()) {
//
//            final boolean isSuccessResponse = retVal.getBody() instanceof SuccessResponse;
//
//            if (!isSuccessResponse) {
//                return;
//            }
//
//            final SuccessResponse<?> response = (SuccessResponse<?>) retVal.getBody();
//
//            // TODO Add response related actions
//
//        }
//
//    }
//
//    /**
//     * After an exception occurred.
//     *
//     * @param joinPoint
//     * @param exception
//     */
//    @AfterThrowing(value = API_POINT_CUT, throwing = "exception")
//    public void logErrors(JoinPoint joinPoint, Throwable exception) {
//
//        log.info("========================== We have Error here ==========================");
//        // for log the controller name
//        log.info(joinPoint.getSignature().getName());
//        // for know what the exception message
//        log.info(exception.getMessage());
//        log.info("==========================================================================");
//    }

}
