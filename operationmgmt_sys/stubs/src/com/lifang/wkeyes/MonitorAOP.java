package com.lifang.wkeyes;

public class MonitorAOP {
    public Object around(org.aspectj.lang.ProceedingJoinPoint pjp) throws Throwable {
        return pjp.proceed();
    }
}
