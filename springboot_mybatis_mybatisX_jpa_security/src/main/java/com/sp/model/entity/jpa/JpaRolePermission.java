package com.sp.model.entity.jpa;

import com.sp.config.hibernate.Comment;
import com.sp.model.entity.base.BaseEntity;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Data
@Table(name = "role_permission")
public class JpaRolePermission extends BaseEntity {
    /**
     * 权限id
     */
    @Comment("权限id")
    private Long permissionId;

    /**
     *  角色id
     */
    @Comment("角色id")
    private Long roleId;

}
