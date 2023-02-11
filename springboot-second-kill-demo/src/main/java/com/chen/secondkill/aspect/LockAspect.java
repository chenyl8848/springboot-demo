package com.chen.secondkill.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author cyl
 * @date 2023-02-11 17:49
 * @description 秒杀加锁切面类
 */
@Component
@Aspect
public class LockAspect {

    private static Lock lock = new ReentrantLock(true); // 互斥锁 参数默认 false 不公平锁

    @Pointcut("@annotation(com.chen.secondkill.annotation.SecondKillLock)")
    public void jointPoint() {

    }

    @Around("jointPoint()")
    public Object around(ProceedingJoinPoint joinPoint) {
        lock.lock();

        Object result = null;
        try {
            result = joinPoint.proceed();
        } catch (Throwable e) {
            e.printStackTrace();
            throw new RuntimeException();
        } finally {
            lock.unlock();
        }

        return result;
    }
}
