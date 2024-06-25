package com.sp.model.entity.jpa;

import com.sp.config.hibernate.Comment;
import com.sp.model.entity.base.BaseEntity;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Data
@Table(name = "permission",
        uniqueConstraints = @UniqueConstraint(name = "code_unique", columnNames = {"code"}))
public class JpaPermission extends BaseEntity {

    /**
     * 权限代码
     */
    @Column(length = 32)
    @Comment("角色代码")
    private String code;

    /**
     *  权限名称
     */
    @Column(length = 32)
    @Comment("角色名称")
    private String name;

    /**
     *  权限类型
     */
    @Column(length = 32)
    @Comment("权限类型")
    private String type;

    /**
     * 资源路径
     */
    @Column(length = 256)
    @Comment("资源路径")
    private String url;

    /**
     * 所属系统(用于区分前后台)
     */
    @Column(length = 32)
    @Comment("所属系统(用于区分前后台)")
    private String sourcePlat;

    /**
     * 父级id
     */
    @Column(length = 32)
    @Comment("父级id")
    private String parentId;

    /**
     * 排序
     */
    @Column(length = 4)
    @Comment("排序")
    private Integer listSort=0;


}
