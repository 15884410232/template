package com.sp.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sp.model.entity.Role;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author 13967
* @description 针对表【role】的数据库操作Mapper
* @createDate 2024-06-25 15:26:50
* @Entity com.sp.model.entity.Role
*/
public interface RoleMapper extends BaseMapper<Role> {

    Role findOneByCode(@Param("code") String code);

    List<String> findAllByUserId(@Param("userId") Long userId);

}




