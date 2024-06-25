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
@Table(name = "role",
        uniqueConstraints = @UniqueConstraint(name = "code_unique", columnNames = {"code"}))
public class JpaRole  extends BaseEntity {

    /**
     * 角色代码
     */
    @Column(length = 32)
    @Comment("角色代码")
    private String code;

    /**
     *  角色名称
     */
    @Column(length = 32)
    @Comment("角色名称")
    private String name;

}
