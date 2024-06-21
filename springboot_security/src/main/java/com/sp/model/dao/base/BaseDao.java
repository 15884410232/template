package com.sp.model.dao.base;

import com.sp.common.exception.CommonException;
import com.sp.common.util.ObjectHelper;
import com.sp.model.dto.response.PageVo;
import com.sp.model.entity.base.BaseEntity;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.NativeQuery;
import org.hibernate.query.Query;
import org.hibernate.query.internal.NativeQueryImpl;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
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
        Type genericSuperclass = this.getClass().getGenericSuperclass();
        if(genericSuperclass instanceof ParameterizedType){
            ParameterizedType parameterizedType = (ParameterizedType) genericSuperclass;
            entityClass=(Class<T>)parameterizedType.getActualTypeArguments()[0];
        }

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
            throw  new CommonException("获取session失败");
        }
//        if(session==null){
//            session = sessionFactory.openSession();
//            log.warn("sessionFactory current session is null, open new.....");
//        }
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
     * 分页查询数据
     * @param sql
     * @param clazz
     * @param params
     * @param pageVo
     * @param <T>
     * @return
     */
    protected  <T> PageVo<T> queryForPage(String sql,Map<String,Object> params, Class<T> clazz,  PageVo pageVo){
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
     * 查询数据List
     * @param sql
     * @param params
     * @param clazz
     * @param <T>
     * @return
     */
    public <T> List<T> queryForList(String sql, Map<String,Object> params, Class<T> clazz){
        NativeQuery nativeQuery = getSession().createNativeQuery(sql);
        nativeQuery.unwrap(NativeQueryImpl.class).setResultTransformer(Transformers.aliasToBean(clazz));
        nativeQuery.setProperties(params);
        return nativeQuery.list();
    }

    public <T> T queryForObject(String sql, Map<String,Object> params, Class<T> clazz){
        NativeQuery nativeQuery = getSession().createNativeQuery(sql);
        nativeQuery.unwrap(NativeQueryImpl.class).setResultTransformer(Transformers.aliasToBean(clazz));
        nativeQuery.setProperties(params);
        List list = nativeQuery.list();
        if(list.size()>0){
            return (T)list.get(0);
        }
        return null;
    }

    public BigInteger queryForBigInteger(String sql, Map<String,Object> params){
        NativeQuery nativeQuery = getSession().createNativeQuery(sql);
        nativeQuery.unwrap(NativeQueryImpl.class);
        nativeQuery.setProperties(params);
        List<BigInteger> list = nativeQuery.list();
        if(list.size()>0){
            return list.get(0);
        }
        return null;
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


    /**
     * insert or update or delete sql
     * @param sql
     * @param params
     */
    protected void executeSql(String sql, Map<String,Object> params){
        Query query = getSession().createNativeQuery(sql);
        query.setProperties(params);
        query.executeUpdate();
    }

    /**
     * 添加或修改实体
     *
     * @param obj
     * @return
     * @throws Exception
     */
    public T saveOrUpdateEntity(T obj) {
        obj.setIsDeleted(0);
        if (ObjectHelper.isEmpty(obj.getId())) {
            obj.setCreateTime(new Date());
            obj.setUpdateTime(new Date());
            getSession().save(obj);
        } else {
            obj.setUpdateTime(new Date());
            getSession().update(obj);
        }
        getSession().flush();
        return obj;
    }

    protected Long countAll(String hql, Map<String, Object> condition) {
        if (hql == null) {
            return 0L;
        }
        String tmpHql = hql.toLowerCase();
        hql="select count(*) "+hql.substring(tmpHql.indexOf("from"));
        log.debug("count(*) hql ---->" + hql);
        org.hibernate.Query q = this.getSession().createQuery(hql);
        if (condition != null&&condition.size()>0) {
            q.setProperties(condition);
        }
        return (Long) q.uniqueResult();
    }

}
