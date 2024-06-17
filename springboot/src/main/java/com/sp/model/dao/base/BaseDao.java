package com.sp.model.dao.base;

import com.sp.model.dto.req.base.PageReq;
import com.sp.model.dto.response.PageVo;
import com.sp.model.entity.base.BaseEntity;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.NativeQuery;
import org.hibernate.query.Query;
import org.hibernate.query.internal.NativeQueryImpl;
import org.hibernate.transform.Transformers;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.ParameterizedType;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author chenqi
 * @date 2022/3/20
 */

@Slf4j
public class BaseDao<T extends BaseEntity> {

    @Autowired
    protected SessionFactory sessionFactory;

    private Class<?> entityClass;

    /**
     * 构造函数-获取entityClass
     */
    public BaseDao(){
        ParameterizedType genericSuperclass = (ParameterizedType)this.getClass().getGenericSuperclass();
        entityClass=(Class<T>)genericSuperclass.getActualTypeArguments()[0];
    }

    /**
     * 获取session
     * @return
     */
    public Session getSession(){
        Session session=null;
        try {
            session=sessionFactory.getCurrentSession();
        }catch (Exception ex){
            ex.printStackTrace();
        }
        if(session==null){
            session = sessionFactory.openSession();
            log.warn("sessionFactory current session is null, open new.....");
        }
        return session;

    }

    /**
     * 插入记录
     * @param t
     * @return
     */
    public T saveEntity(T t){
        Session session = this.getSession();
        t.setIsDeleted(0);
        session.save(t);
        return t;
    }

    /**
     * 获取记录
     * @param id
     * @return
     */
    public T getEntity(String id){
        return (T)this.getSession().get(entityClass, id);
    }

    /**
     * 更新记录
     * @param t
     * @return
     */
    public T updateEntity(T t){
        t.setUpdateTime(new Date());
        getSession().update(t);
        return t;
    }

    /**
     * 物理删除记录
     * @param t
     */
    public void deleteEntity(T t){
        getSession().delete(t);
    }

    /**
     * 物理删除记录
     * @param id
     */
    public void deleteEntity(String id){
        getSession().delete(getEntity(id));
    }

    /**
     * 逻辑删除记录
     * @param t
     */
    public void logicDeleteEntity(T t){
        t.setIsDeleted(1);
        t.setDeletedTime(new Date());
        getSession().update(t);
    }

    /**
     * 逻辑删除记录
     * @param id
     */
    public void logicDeleteEntity(String id){
        T t = getEntity(id);
        t.setIsDeleted(1);
        t.setDeletedTime(new Date());
        getSession().update(t);
    }

    /**
     * 反野查询数据
     * @param sql
     * @param clazz
     * @param params
     * @param <T>
     * @return
     */
    public <T> PageVo<T> findPageBySql(String sql, Class<T> clazz, Map<String,Object> params, PageVo pageVo){

        NativeQuery nativeQuery = getSession().createNativeQuery(sql);
        nativeQuery.unwrap(NativeQueryImpl.class).setResultTransformer(Transformers.aliasToBean(clazz));
        nativeQuery.setProperties(params);
        nativeQuery.setMaxResults(pageVo.getRow());
        nativeQuery.setFirstResult((pageVo.getPage()-1)* pageVo.getRow());
        pageVo.setData(nativeQuery.list());

        String countSql="select count(t.id) from ("+sql+") t ";
        NativeQuery countQuery = getSession().createNativeQuery(countSql);
        countQuery.setProperties(params);
        pageVo.setTotal((BigInteger) countQuery.list().get(0));
        return pageVo;
    }

    /**
     * 反野查询数据
     * @param sql
     * @param clazz
     * @param params
     * @param <T>
     * @return
     */
    public <T> PageVo<T> findPageBySql(String sql, Class<T> clazz, Map<String,Object> params, PageReq pageReq){

        NativeQuery nativeQuery = getSession().createNativeQuery(sql);
        nativeQuery.unwrap(NativeQueryImpl.class).setResultTransformer(Transformers.aliasToBean(clazz));
        nativeQuery.setProperties(params);
        nativeQuery.setMaxResults(pageReq.getRow());
        nativeQuery.setFirstResult((pageReq.getPage()-1)* pageReq.getRow());

        PageVo<T> pageVo=new PageVo<>();
        BeanUtils.copyProperties(pageReq,pageVo);

        pageVo.setData(nativeQuery.list());

        String countSql="select count(t.id) from ("+sql+") t ";
        NativeQuery countQuery = getSession().createNativeQuery(countSql);
        countQuery.setProperties(params);
        pageVo.setTotal((BigInteger) countQuery.list().get(0));
        return pageVo;
    }


    /**
     * 查询数据List
     * @param sql
     * @param t
     * @param params
     * @param <T>
     * @return
     */
    public <T> List<T> findListBySql(String sql, T t, Map<String,Object> params){
        NativeQuery nativeQuery = getSession().createNativeQuery(sql);
        nativeQuery.unwrap(NativeQueryImpl.class).setResultTransformer(Transformers.aliasToBean(t.getClass()));
        nativeQuery.setProperties(params);
        return nativeQuery.list();
    }

    /**
     * 查询数据List
     * @param sql
     * @param clazz
     * @param params
     * @param <T>
     * @return
     */
    public <T> List<T> findListBySqlClass(String sql, Class<T> clazz, Map<String,Object> params){
        NativeQuery nativeQuery = getSession().createNativeQuery(sql);
        nativeQuery.unwrap(NativeQueryImpl.class).setResultTransformer(Transformers.aliasToBean(clazz));
        nativeQuery.setProperties(params);
        return nativeQuery.list();
    }

    /**
     * hql查询数据List
     * @param hql
     * @param params
     * @return
     */
    protected List<T> findByHql(String hql, Map<String,Object> params){
        hql = hql
                .replaceAll("(WHERE|where)[\\s]+1[\\s]*=[\\s]*1[\\s]+(and|AND)", "WHERE");
        hql = hql
                .replaceAll("(WHERE|whre)[\\s]+1[\\s]*=[\\s]*1[\\s]", "");
        Query query = getSession().createQuery(hql);
        query.setProperties(params);
        return  query.list();
    }
}
