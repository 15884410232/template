package com.sp.model.dto.response;

import com.sp.common.util.tree.TreeNode;
import lombok.Data;

@Data
public class PermissionVo extends TreeNode {
    /**
     * 角色代码
     */
    private String code;

    /**
     *
     */
    private String icon;

    /**
     * 排序
     */
    private Integer listSort;

    /**
     * 角色名称
     */
    private String name;

    /**
     * 父级id
     */
    private Long parentId;

    /**
     * 所属系统(用于区分前后台)
     */
    private String sourcePlat;

    /**
     * 权限类型
     */
    private String type;

    /**
     * 资源路径
     */
    private String url;


}
