package com.sp.model.entity;

import com.sp.model.entity.base.BaseEntity;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author chenqi
 * @date 2022/3/20
 */
@Data
@Entity
@Table(name="test")
public class TestEntity extends BaseEntity {

    /**
     * 名字
     */
    @Column(length = 10)
    private String name;


}
