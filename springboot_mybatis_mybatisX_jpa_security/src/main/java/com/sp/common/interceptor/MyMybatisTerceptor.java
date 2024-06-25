//package com.sp.common.interceptor;
//
//import com.sp.model.entity.base.BaseEntity;
//import lombok.extern.slf4j.Slf4j;
//import org.apache.ibatis.executor.Executor;
//import org.apache.ibatis.mapping.MappedStatement;
//import org.apache.ibatis.mapping.SqlCommandType;
//import org.apache.ibatis.plugin.*;
//import org.springframework.security.core.context.SecurityContextHolder;
//
//import java.util.Objects;
//import java.util.Properties;
//
//@Slf4j
//@Intercepts({
//        @Signature(type = Executor.class, method = "update", args = {MappedStatement.class, Object.class})
//})
//public class MyMybatisTerceptor implements Interceptor {
//    @Override
//    public Object intercept(Invocation invocation) throws Throwable {
//        MappedStatement mappedStatement = (MappedStatement) invocation.getArgs()[0];
//        Object parameter = invocation.getArgs()[1];
//        if (parameter instanceof BaseEntity) {
//            Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//            String currentUsername = null;
//            if (principal instanceof UserDetailsImpl) {
//                UserDetailsImpl userDetails = (UserDetailsImpl) principal;
//                currentUsername = userDetails.getUsername();
//            }
//            BaseEntity baseEntity = (BaseEntity) parameter;
//            if (Objects.equals(SqlCommandType.INSERT, mappedStatement.getSqlCommandType())) {
//                baseEntity.setCreateBy(currentUsername);
//                baseEntity.setCreateTime(new Date());
//            } else if (Objects.equals(SqlCommandType.UPDATE, mappedStatement.getSqlCommandType())) {
//                baseEntity.setUpdateBy(currentUsername);
//                baseEntity.setUpdateTime(new Date());
//            }
//        }
//        return invocation.proceed();
//    }
//
//    @Override
//    public Object plugin(Object target) {
//        return Plugin.wrap(target, this);
//    }
//
//    @Override
//    public void setProperties(Properties properties) {
////        Interceptor.super.setProperties(properties);
//    }
//}