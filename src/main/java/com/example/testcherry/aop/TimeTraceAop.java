package com.example.testcherry.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class TimeTraceAop {

  @Around("execution(* com.example.testcherry..*(..)))")
  public Object execute(ProceedingJoinPoint joinPoint) throws Throwable {
    long startTime = System.currentTimeMillis();
    System.out.println("START: " + joinPoint.toShortString());

    try {
      return joinPoint.proceed();
    } finally {
      long endTime = System.currentTimeMillis();
      System.out.println(
          "END: " + joinPoint.toShortString() + ": " + (endTime - startTime) + "ms");
    }
  }

}
