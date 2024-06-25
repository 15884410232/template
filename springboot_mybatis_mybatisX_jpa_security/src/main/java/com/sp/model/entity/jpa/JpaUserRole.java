package com.sp.model.entity.jpa;

import com.sp.config.hibernate.Comment;
import com.sp.model.entity.base.BaseEntity;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Data
@Table(name = "user_role")
public class JpaUserRole extends BaseEntity {

    /**
     * 用户id
     */
    @Comment("用户id")
    private Long userId;

    /**
     *  角色id
     */
    @Comment("角色id")
    private Long roleId;

}
