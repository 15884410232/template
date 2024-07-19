package com.sp.common.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Slf4j
@Aspect
@Component
public class SqlLogAspect {

    @Around("execution(* com.sp.mapper.*.*(..))")
    public Object logSql(ProceedingJoinPoint joinPoint) throws Throwable {
        // 获取方法签名
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        // 获取方法
        Method method = signature.getMethod();
        // 获取参数
        Object[] args = joinPoint.getArgs();

        // 构造SQL语句
        String sql = getSqlFromMethodAndArgs(method, args);

        // 打印SQL
        log.info("Executing SQL: {}", sql);

        // 继续执行方法
        return joinPoint.proceed();
    }

    private String getSqlFromMethodAndArgs(Method method, Object[] args) {
        // 这里你需要解析方法和参数，生成带有参数的SQL语句
        // 这部分逻辑可能比较复杂，取决于你的参数类型和SQL语句结构
        // 你可能需要使用反射或其他技术来获取和格式化参数
        return "";
    }
}