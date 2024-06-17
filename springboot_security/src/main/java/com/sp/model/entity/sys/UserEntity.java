package com.sp.model.entity.sys;

import com.sp.model.entity.base.BaseEntity;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Data
@Entity
@Table(name="user")
public class UserEntity extends BaseEntity {

    /**
     * 账号
     */
    @Column(length = 12)
    private String username;

    /**
     * 密码
     */
    @Column(length = 256)
    private String password;


}
