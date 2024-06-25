package com.sp.mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sp.model.entity.RolePermission;
import org.apache.ibatis.annotations.Param;

/**
* @author 13967
* @description 针对表【role_permission】的数据库操作Mapper
* @createDate 2024-06-25 15:26:50
* @Entity com.sp.model.entity.RolePermission
*/
public interface RolePermissionMapper extends BaseMapper<RolePermission> {

    RolePermission findOneByRoleIdAndPermissionId(@Param("roleId") Long roleId, @Param("permissionId") Long permissionId);

}




