package com.sp.common.util.tree;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.List;

/**
 * 树父类
 * @author chenqi
 * @date 2022/3/28
 */
@Data
@JsonInclude(value= JsonInclude.Include.NON_NULL)
public class TreeNode {

    protected Long id;

    protected Long parentId;

    protected List<TreeNode> children;
}
