package limit;

import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

/**
 * 
 * @author yuezh2
 * @date 2020/09/04 17:25
 *
 */
@Aspect
@Component
public class GlobalInterceptorConfiguration {
//    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalInterceptorConfiguration.class);
//    
//
//    @Pointcut("execution(public * com.lenovo.ofp.order.middleware.service..*.*(..))")
//    public void methodPoint() {}
//    
//    
//    /**
//     * 
//     * @param pjp
//     * @return
//     */
//    @Around("methodPoint()")
//    public Object handlerServiceMethod(ProceedingJoinPoint pjp) {
//        long startTime = System.currentTimeMillis();
//
//        Object result;
//        try {
//        	
////        	MethodSignature signature = (MethodSignature) pjp.getSignature();
////            Method method = signature.getMethod();
//        	
////        	RateLimiter limiter = RateLimiterUtil.RATE_LIMITER.get(pjp.getSignature().getDeclaringTypeName()+"."+pjp.getSignature().getName());
//        	
////        	if(limiter==null) {
////        		limiter = RateLimiterUtil.RATE_LIMITER.get("DEFAULT");
////        	}
//            
////            if (limiter==null || limiter.tryAcquire(10, TimeUnit.MICROSECONDS)) {
//                result = pjp.proceed();
////            } else {
////            	LOGGER.error(pjp.getSignature().getDeclaringTypeName()+"."+pjp.getSignature().getName() + " limit error");
////            	result = CommonExceptionEnum.getLimitErrorResult();
////            }
//            
////            if (RateLimiterUtil.RATE_LIMITER.get("DEFAULT").tryAcquire(10, TimeUnit.MICROSECONDS)) {
////                result = pjp.proceed();
////            } else {
////            	result = CommonExceptionEnum.getLimitErrorResult();
////            }
//            
//            LOGGER.debug("method : {} , use time : {} " , pjp.getSignature() , (System.currentTimeMillis() - startTime));
//        } catch (Throwable e) {
//        	LOGGER.error("method : {} , error : {}",pjp.getSignature() , e.getMessage());
//            result = handlerException(e);
//        }
//
//        return result;
//    }
//    
//
//
//
//    
//    /**
//     * 
//     * @param pjp
//     * @param e
//     * @return
//     */
//    
//    private RemoteResult<Object> handlerException(Throwable e) {
//        RemoteResult<Object> result = new RemoteResult<>(false);
//
//        // 已知异常
//        if (e instanceof BizException) {
//            result.setResultCode(((BizException) e).getCode()+"");
//            result.setResultMsg(((BizException) e).getMessage());
//            result.setSuccess(false);
//            result.setT(null);
//        } else {
//            //TODO 未知的异常，应该格外注意，可以发送邮件通知等
//            result = CommonExceptionEnum.getSystemErrorResult();
//        }
//
//        return result;
//    }
//    
//    

    
}