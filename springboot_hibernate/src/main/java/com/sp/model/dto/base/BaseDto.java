package com.sp.model.dto.base;

import lombok.Data;

import java.util.Date;

/**
 * @author chenqi
 * @date 2022/3/21
 */
@Data
public class BaseDto {

    private String id;

    /**
     * 创建时间
     * @CreationTimestamp注解指示 Hibernate 在实体被持久化时将注解的实体属性设置为 JVM 的当前时间戳值。
     * @Source(SourceType.DB) 用于指定时间戳从哪里获取，DB从数据库中获取时间戳。虚拟机 从当前 JVM 获取时间戳。
     */
    private Date createTime;

    /**
     * 创建人
     */
    private String createBy;

    /**
     * 更新时间
     * @UpdateTimestamp注解指示 Hibernate 在实体被持久化时将注解的实体属性设置为 JVM 的当前时间戳值。
     */
    private Date updateTime;

    /**
     * 更新人
     */
    private String updateBy;


    /**
     * 删除时间
     */
    private Date deletedTime;

    /**
     * 是否删除
     */
    private Integer isDeleted;

    /**
     * 版本号
     */
    private Integer version;

}
