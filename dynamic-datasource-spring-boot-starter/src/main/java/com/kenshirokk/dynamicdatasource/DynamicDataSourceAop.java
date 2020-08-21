package com.kenshirokk.dynamicdatasource;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Method;

@Aspect
@Order(1)
public class DynamicDataSourceAop {

    @Pointcut("execution(* com..service..*.*(..))")
    public void pointCut() {}

    @Before("pointCut()")
    public void before(JoinPoint joinPoint) {

        boolean isWrite = true;

        Class<?> clazz = joinPoint.getTarget().getClass();
        Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();

        boolean methodAnnotationPresent = method.isAnnotationPresent(Transactional.class);
        if (methodAnnotationPresent) {
            //如果方法上面有注解
            Transactional annotation = method.getAnnotation(Transactional.class);
            isWrite = !annotation.readOnly();
        } else {
            //方法没注解 看类上面注解
            boolean clazzAnnotationPresent = clazz.isAnnotationPresent(Transactional.class);
            if (clazzAnnotationPresent) {
                Transactional annotation = clazz.getAnnotation(Transactional.class);
                isWrite = !annotation.readOnly();
            }
        }

        if (isWrite) {
            DynamicDataSourceHolder.setDataSourceLookupKey(DynamicDataSourceEnum.WRITE);
        } else {
            DynamicDataSourceHolder.setDataSourceLookupKey(DynamicDataSourceEnum.READ);
        }

    }

    @After("pointCut()")
    public void after() {
        DynamicDataSourceHolder.clearDataSourceLookupKey();
    }
}
