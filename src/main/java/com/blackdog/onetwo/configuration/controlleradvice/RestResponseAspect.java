package com.blackdog.onetwo.configuration.controlleradvice;

import com.blackdog.onetwo.configuration.response.success.RestResponse;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@Aspect
public class RestResponseAspect {
    @Around("execution(* com.blackdog.onetwo.domain.*.controller.*Controller.*(..))")
    public RestResponse<?> handlerRestResponse(ProceedingJoinPoint joinPoint) throws Throwable {
        return RestResponse.of("OK", joinPoint.proceed(), LocalDateTime.now());
    }
}
