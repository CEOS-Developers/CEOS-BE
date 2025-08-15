package ceos.backend.global.common.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.transaction.support.TransactionSynchronizationManager;


@Slf4j
@Aspect
@Component
public class TransactionLoggingAspect {

    @Around("@annotation(ceos.backend.global.common.annotation.TransactionLog)")
    public Object logTxMethod(ProceedingJoinPoint joinPoint) throws Throwable {
        String methodName = joinPoint.getSignature().toShortString();

        log.info("[TX START] {}", methodName);
        String txName = TransactionSynchronizationManager.getCurrentTransactionName();
        log.info("[TX NAME] = {}", txName);


        try {
            Object result = joinPoint.proceed();
            log.info("[TX COMMIT] {}", methodName);
            return result;
        } catch (Exception e) {
            log.warn("[TX ROLLBACK] {} - Exception: {}", methodName, e.getMessage());
            throw e;
        }
    }
}
