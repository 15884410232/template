package com.sp.service;

import com.sp.common.util.StringUtils;
import com.sp.model.dao.TestDao;
import com.sp.model.dto.TestDto;
import com.sp.model.dto.req.TestReq;
import com.sp.model.dto.req.base.PageReq;
import com.sp.model.dto.response.PageVo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.Map;

@Service
public class TestService {

    @Resource
    private TestDao testDao;

    @Transactional
    public PageVo<TestDto> getTestPage(PageReq<TestReq> pageReq){
        Map<String,Object> param=new HashMap<>();
        StringBuilder sql=new StringBuilder("select * from test");
        TestReq testReq = pageReq.getParam();
        if(testReq!=null){
            if(StringUtils.isNotBlank(testReq.getName())){
                sql.append(" and t01.name=:name");
                param.put("realName",testReq.getName());
            }
        }
        return testDao.findPageBySql(sql.toString(), TestDto.class, param, pageReq);
    }

}
