package com.sp.common.util.tree;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
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

    @JsonSerialize(using = ToStringSerializer.class)
    protected Long id;

    protected Long parentId;

    protected List<TreeNode> children;
}
