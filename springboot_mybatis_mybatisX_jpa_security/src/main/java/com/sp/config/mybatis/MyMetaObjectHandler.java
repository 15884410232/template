package com.sp.config.mybatis;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.sp.common.util.SecurityContextUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;

@Slf4j
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {
    // 插入时的填充策略
    @Override
    public void insertFill(MetaObject metaObject) {
        this.setFieldValByName("createTime", new Date(), metaObject);
        this.setFieldValByName("updateTime", new Date(), metaObject);
        this.setFieldValByName("version", 0, metaObject);
        try {
            this.setFieldValByName("createBy", SecurityContextUtil.getCurrentUser().getId(), metaObject);
            this.setFieldValByName("updateBy", SecurityContextUtil.getCurrentUser().getId(), metaObject);
        }catch (NullPointerException ex){
            log.info("初始化系统数据时，拿不到当前登录用户，正常现象不用处理");
        }
    }
    // 更新时的填充策略
    @Override
    public void updateFill(MetaObject metaObject) {
        this.setFieldValByName("updateTime", new Date(), metaObject);
        try {
            this.setFieldValByName("updateBy", SecurityContextUtil.getCurrentUser().getId(), metaObject);
        }catch (NullPointerException ex){
            log.info("初始化系统数据时，拿不到当前登录用户，正常现象不用处理");
        }
    }
}
