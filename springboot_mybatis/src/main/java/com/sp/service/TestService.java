package com.sp.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sp.common.util.StringUtils;
import com.sp.model.dto.TestDto;
import com.sp.model.dto.req.TestReq;
import com.sp.model.dto.response.PageVo;
import com.sp.model.entity.Test;
import com.sp.model.mapper.TestMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class TestService {

    @Resource
    private TestMapper testMapper;

    public PageVo<TestDto> getTestPage(TestReq testReq){
        Page<Test> page=new Page<>();
        page.setCurrent(testReq.getPageReq().getPage());
        page.setSize(testReq.getPageReq().getRow());
        LambdaQueryWrapper<Test> lambdaQueryWrapper=new LambdaQueryWrapper();
        lambdaQueryWrapper.eq(StringUtils.isNotBlank(testReq.getName()),Test::getUserName,testReq.getName());
        testMapper.selectPage(page, lambdaQueryWrapper);
        List<TestDto> list=new ArrayList<>();
        page.getRecords().stream().forEach(test->{
            TestDto testDto=new TestDto();
            BeanUtils.copyProperties(test,testDto);
            list.add(testDto);
        });
        PageVo pageVo=new PageVo();
        pageVo.init(page,list);
        return pageVo;
    }

}
