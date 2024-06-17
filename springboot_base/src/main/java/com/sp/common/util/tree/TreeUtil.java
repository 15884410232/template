package com.sp.common.util.tree;

import java.util.LinkedList;
import java.util.List;

/**
 * @author chenqi
 * @date 2022/3/28
 */
public class TreeUtil{

    /**
     * 双重循环生成树
     * @param list
     * @param root
     * @param <T>
     * @return
     */
    public static <T extends TreeNode> List<T> buildTree(List<T> list,String root){
        List<T> tree=new LinkedList<>();
        for (T t : list) {
            if(t.getParentId().equals(root)) {
                tree.add(t);
            }
            for (T t1 : list) {
                if(t1.getParentId().equals(t.getId())){
                    if(t.getChildren()==null){
                        t.setChildren(new LinkedList<>());
                    }
                    t.getChildren().add(t1);
                }
            }
        }

        return tree;
    }

}
