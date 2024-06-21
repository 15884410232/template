package com.sp.model.dto.response;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 分页查询返回参数
 * Created by   on 2021/6/6.
 */
@Data
public class PageVo<T> implements Serializable {

    private long page=1;

    private long row=20L;

    private long total=0L;

    private List<T> Data;


    public void init(Page<T> page,List<T> data){
        Data=data;

        total=page.getTotal();

        row=page.getSize();
    }
}
