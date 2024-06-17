package com.sp.model.entity.base;

import lombok.Data;
import org.hibernate.annotations.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

/**
 * @author chenqi
 * @date 2022/3/20
 */
@Data

@MappedSuperclass
public class BaseEntity implements Serializable {

    @Id
    @Column(length=32)
    @GenericGenerator(name="systemUUID",strategy = "uuid")
    @GeneratedValue(generator = "systemUUID")
    private String id;

    /**
     * 创建时间
     * @CreationTimestamp注解指示 Hibernate 在实体被持久化时将注解的实体属性设置为 JVM 的当前时间戳值。
     * @Source(SourceType.DB) 用于指定时间戳从哪里获取，DB从数据库中获取时间戳。虚拟机 从当前 JVM 获取时间戳。
     */
    @CreationTimestamp
    @Source(SourceType.DB)
    private Date createTime;

    /**
     * 创建人
     */
    @Column
    private String createBy;

    /**
     * 更新时间
     * @UpdateTimestamp注解指示 Hibernate 在实体被持久化时将注解的实体属性设置为 JVM 的当前时间戳值。
     */
    @UpdateTimestamp
    private Date updateTime;

    /**
     * 更新人
     */
    @Column
    private String updateBy;


    /**
     * 删除时间
     */
    @Column
    private Date deletedTime;

    /**
     * 是否删除
     */
    @Column(length = 1)
    private Integer isDeleted;

    /**
     * 版本号
     */
    @Version
    private Integer version;



}
