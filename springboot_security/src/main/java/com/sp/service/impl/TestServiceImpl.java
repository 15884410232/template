package com.sp.service.impl;


import com.sp.model.dao.TestDao;
import com.sp.model.entity.TestEntity;
import com.sp.service.TestService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/**
 * @author chenqi
 * @date 2022/3/20
 */
@Slf4j
@Service
public class TestServiceImpl implements TestService {

    @Autowired
    private TestDao testDao;

    @Override
    @Transactional
    public void saveTest() {
        TestEntity testEntity=new TestEntity();
        testEntity.setName("dada");
        testDao.saveEntity(testEntity);
    }

    @Override
    public TestEntity getTest() {
//        log.info("test:{}",testDao.getEntity("1"));
        testDao.query("");
        return null;

    }
}
