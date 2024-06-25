package com.sp.model.entity.base;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.Version;
import com.sp.config.hibernate.Comment;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.util.Date;

/**
 * @author chenqi
 * @date 2022/3/20
 */
@Data
@MappedSuperclass
public class BaseEntity implements Serializable {

    @Id
//    @GenericGenerator(name="systemUUID",strategy = "uuid")
//    @GeneratedValue(generator = "systemUUID")
//    @JsonSerialize(using = ToStringSerializer.class)
    @Comment("id")
    private Long id;

    /**
     * 创建时间
     * @CreationTimestamp注解指示 Hibernate 在实体被持久化时将注解的实体属性设置为 JVM 的当前时间戳值。
     * @Source(SourceType.DB) 用于指定时间戳从哪里获取，DB从数据库中获取时间戳。虚拟机 从当前 JVM 获取时间戳。
     */
//    @CreationTimestamp
//    @Source(SourceType.DB)
    @TableField(fill = FieldFill.INSERT)
    @Comment("创建时间")
    private Date createTime;

    /**
     * 创建人
     */
    @Column
    @TableField(fill = FieldFill.INSERT)
    @Comment("创建人id")
    private Long createBy;

    /**
     * 更新时间
     * @UpdateTimestamp注解指示 Hibernate 在实体被持久化时将注解的实体属性设置为 JVM 的当前时间戳值。
     */
//    @UpdateTimestamp
    @TableField(fill = FieldFill.INSERT_UPDATE)
    @Comment("更新时间")
    private Date updateTime;

    /**
     * 更新人
     */
    @Column
    @TableField(fill = FieldFill.INSERT_UPDATE)
    @Comment("更新人id")
    private Long updateBy;


    /**
     * 删除时间
     */
    @Column
    @Comment("删除时间")
    private Date deletedTime;

    /**
     * 是否删除
     */
    @Column(length = 1)
    @Comment("是否删除 0:未删除 1:已删除")
    private Integer isDeleted;

    /**
     * 版本号
     */
    @Version
    @TableField(fill = FieldFill.INSERT)
    @Comment("integer类型版本号字段，用于处理并发问题")
    private Integer version;



}
