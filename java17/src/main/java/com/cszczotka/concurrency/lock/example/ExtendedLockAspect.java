package com.cszczotka.concurrency.lock.example;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

/**
 * https://koziolekweb.pl/2024/01/31/extended-lock/
 */
@Aspect
public class ExtendedLockAspect {

    ExtendedLockHandler lockHandler = new ExtendedLockHandler();

    @Around("@annotation(pl.koziolekweb.extendedlock.ExtendedLock)")
    public Object callWithLock(ProceedingJoinPoint thisJoinPoint) throws Throwable {
        var args = thisJoinPoint.getArgs();
        try {
            while (!lockHandler.lock(args)) {
            }
            final Object proceed = thisJoinPoint.proceed();
            while (!lockHandler.unlock(args)) {
            }
            return proceed;
        } catch (Throwable t) {
            while (!lockHandler.unlock(args)) {
            }
            throw t;
        } finally {
            while (!lockHandler.unlock(args)) {
            }
        }
    }

}
