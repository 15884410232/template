package com.sp.model.entity.jpa;

import com.sp.model.entity.base.BaseEntity;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Data
@Table(name = "test")
public class JpaTest extends BaseEntity {

    /**
     * 用户名
     * columnDefinition 可以指定字段的数据库类型
     */
//    @Column(length = 12,columnDefinition = "bigint")
    @Column(length = 12)
    private String username;

    /**
     * 密码
     */
    @Column(length = 128)
    private String password;

}
