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
@Table(name = "user",
        uniqueConstraints = @UniqueConstraint(name = "username_unique", columnNames = {"username"}))
public class JpaUser extends BaseEntity {

    /**
     * 用户名
     * columnDefinition 可以指定字段的数据库类型
     */
//    @Column(length = 12,columnDefinition = "bigint")
    @Column(length = 12)
    @Comment("用户名")
    private String username;

    /**
     * 密码
     */
    @Column(length = 128)
    @Comment("密码")
    private String password;

    /**
     * 昵称
     */
    @Column(length = 128)
    @Comment("昵称")
    private String nickname;

    /**
     *  邮箱
     */
    @Column(length = 128)
    @Comment("邮箱")
    private String email;


    /**
     *  手机号
     */
    @Column(length = 11)
    @Comment("电话")
    private String mobile;

    /**
     *  状态
     *
     *  枚举类型
     *
     *  数据库存储为数字
     *
     */
    @Column(length = 1)
    @Comment("状态")
    private Integer status;

    /**
     * token过期时间 S
     */
    @Column
    @Comment("token过期时间 S")
    private Integer JwtExpire;

    /**
     * token
     */
    @Column
    @Comment("jwtTkone用于实现同一账户只能在一个地方登录")
    private Integer JwtToekn;


}
