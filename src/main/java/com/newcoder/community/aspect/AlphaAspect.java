package com.newcoder.community.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

/**
 * @Description: 切面组件的demo
 * @ClassName: AlphaAspect
 * @author: jinhua
 */
//@Component
//@Aspect
public class AlphaAspect {

    @Pointcut("execution(* com.newcoder.community.service.*.*(..))")
    public void pointcut() {

    }

    @Before("pointcut()")
    public void before() {
        System.out.println("before");
    }

    @After("pointcut()")
    public void after() {
        System.out.println("after");
    }

    @AfterReturning("pointcut()")
    public void afterReturning() {
        System.out.println("afterReturning");
    }

    @AfterThrowing("pointcut()")
    public void afterThrowing() {
        System.out.println("afterThrowing");
    }

    @Around("pointcut()")
    public Object around(ProceedingJoinPoint procee) throws Throwable {
        System.out.println("around before");
        Object obj = procee.proceed();
        System.out.println("around after");
        return obj;
    }
}
