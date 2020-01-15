package com.xiaobai.sql.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * @author XinHuiChen
 */
@Component
@Aspect
@Slf4j
public class ParameterLogAOP {
    @Pointcut(value = "execution(public * com.xiaobai.sql.validate.*.*(..))")
    public void validate() { }

    @Before(value = "validate()")
    public void doBefore(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        String name = joinPoint.getSignature()
                               .getName();
        log.info("method: [{}], args: [{}]", name, Arrays.toString(args));
    }
}
