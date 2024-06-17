package com.sp.model.dao;

import com.sp.model.dao.base.BaseDao;
import com.sp.model.dto.TestDto;
import com.sp.model.entity.sys.UserEntity;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.query.NativeQuery;
import org.hibernate.query.internal.NativeQueryImpl;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author chenqi
 * @date 2022/3/20
 */
@Slf4j
@Repository
public class UserDao extends BaseDao<UserEntity> {

    public List<UserEntity> selectByUsername(String username){
        Map<String,Object> param=new HashMap<>();
        StringBuilder sql=new StringBuilder(" select * from user ");
        sql.append("where username=:username");
        param.put("username", username);
        NativeQuery sqlQuery=getSession().createNativeQuery(sql.toString());
        sqlQuery.setProperties(param);
        sqlQuery.unwrap(NativeQueryImpl.class)
                .setResultTransformer(Transformers.aliasToBean(TestDto.class));
        List<UserEntity> list=sqlQuery.list();
        for (UserEntity o : list) {
            log.info("------------:{}",o);
        }

        return list;
    }


}
