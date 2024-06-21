package com.sp.model.mapper;

import com.sp.model.entity.UserBa;
import com.sp.model.entity.UserBaExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserBaMapper {
    long countByExample(UserBaExample example);

    int deleteByExample(UserBaExample example);

    int deleteByPrimaryKey(Long id);

    int insert(UserBa record);

    int insertSelective(UserBa record);

    List<UserBa> selectByExample(UserBaExample example);

    UserBa selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") UserBa record, @Param("example") UserBaExample example);

    int updateByExample(@Param("record") UserBa record, @Param("example") UserBaExample example);

    int updateByPrimaryKeySelective(UserBa record);

    int updateByPrimaryKey(UserBa record);
}