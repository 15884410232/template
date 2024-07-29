package com.sp.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sp.model.entity.Permission;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author 13967
* @description 针对表【permission】的数据库操作Mapper
* @createDate 2024-06-25 15:26:51
* @Entity com.sp.model.entity.Permission
*/
public interface PermissionMapper extends BaseMapper<Permission> {

    Permission findOneByCode(@Param("code") String code);

    List<String>  findAllByUserId(@Param("userId") Long userId);


    List<Permission> getMenu(@Param("userId") Long userId,@Param("roleId") Long roleId);

    List<Permission> getAllBySourcePlat(@Param("sourcePlat") String sourcePlat);

}




