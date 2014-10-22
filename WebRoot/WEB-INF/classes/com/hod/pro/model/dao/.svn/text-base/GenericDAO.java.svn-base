package com.hod.pro.model.dao;

import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.LockMode;
import org.hibernate.criterion.DetachedCriteria;

/**
 * @author yixiang
 */
public interface GenericDAO<T extends Serializable, PK extends Serializable> {

	/**
	 * 使用HSQL语句直接增加、更新、删除实体;提高执行效率
	 * 
	 * @param queryString
	 * @return
	 */
	public abstract int bulkUpdate(String queryString);

	/**
	 * 使用带参数的HSQL语句增加、更新、删除实体
	 * 
	 * @param queryString
	 * @param values
	 * @return
	 */
	public abstract int bulkUpdate(String queryString, Object[] values);

	/**
	 * 按ID查找
	 * 
	 * @param id
	 * @return
	 */
	public abstract T findById(Serializable id);

	/**
	 * 求满足条件的行数
	 * 
	 * @param criteria
	 * @return
	 */
	public abstract Integer getRowCount(String HQL);

	/**
	 * 求满足条件的行数
	 * 
	 * @param criteria
	 * @return
	 */
	public abstract Integer getRowCount(DetachedCriteria criteria);

	/**
	 * 常用聚合查询
	 * 
	 * @param criteria
	 * @param propertyName
	 * @param StatName
	 * @return
	 */
	public abstract Object getStatValue(DetachedCriteria criteria,
			String propertyName, String StatName);

	/**
	 * 多条件组合查询
	 */
	public abstract List<T> findLikeByEntity(T entity, String[] propertyNames);

	/**
	 * 启用hibernate二级缓存查询..详见配置文件
	 * 
	 */
	@SuppressWarnings("unchecked")
	public abstract List findCacheByHQL(final String HQL);

	/**
	 * HQL查询
	 * 
	 * @param queryString
	 * @return
	 */
	public abstract List findByHQL(String queryString);

	/**
	 * 通过HQL语句进行分页查询
	 */
	public abstract List<T> findByHQL(final int currPage, final int pagesize,
			final String hql);

	/**
	 * 原生SQL
	 * @param SQL
	 * @return
	 */
	public abstract List findByNHQL(final String SQL);
	
	public abstract List findByNHQL(int currPage,int pagesize, String SQL);

	public abstract List findByCriteria(DetachedCriteria criteria);

	/**
	 * DetachedCriteria分页
	 * 
	 * @param criteria
	 * @param firstResult
	 * @param maxResults
	 * @return
	 */
	public abstract List findByCriteria(DetachedCriteria criteria,
			int firstResult, int maxResults);

	public abstract List<T> findEqualByEntity(T entity, String[] propertyNames);

	public abstract List find(String queryString, Object[] values);

	public abstract List findByNamedParam(String queryString,
			String[] paramNames, Object[] values);

	public abstract List findByNamedQuery(String queryName);

	public abstract List findByNamedQuery(String queryName, Object[] values);

	public abstract List<T> findByNamedQueryAndNamedParam(String queryName,
			String[] paramNames, Object[] values);

	public abstract Iterator<T> iterate(String queryString);

	public abstract Iterator<T> iterate(String queryString, Object[] values);

	public abstract void closeIterator(Iterator it);

	public abstract DetachedCriteria createDetachedCriteria();

	public abstract Criteria createCriteria();

	/**
	 * 更新实体
	 * 
	 * @param entity
	 */
	public abstract void update(T entity);
	
	/**
	 * 根据自定义HQL语句更新
	 * @param HQL
	 */
	public abstract void updateByHql(String HQL);

	public abstract void save(T entity) throws Exception;

	public abstract void saveOrUpdate(T entity);

	public abstract void saveOrUpdateAll(Collection<T> entities);

	/**
	 * 按实体删除
	 * 
	 * @param entity
	 * @throws Exception
	 */
	public abstract void delete(T entity) throws Exception;

	/**
	 * 按主键删除
	 * 
	 * @param id
	 * @throws Exception
	 */
	public abstract void deleteByKey(PK id) throws Exception;

	/**
	 * 按实体集合删除
	 * 
	 * @param entities
	 */
	public abstract void deleteAll(Collection<T> entities);

	public abstract T load(PK id);

	public abstract List<T> loadAll();

	/**
	 * 根据主键获取实体并加锁。如果没有相应的实体，返回 null。
	 * 
	 */
	public abstract T getWithLock(PK id, LockMode lock);

	/**
	 * 根据主键获取实体并加锁。如果没有相应的实体，抛出异常
	 * @param id
	 * @param lock
	 * @return
	 */
	public abstract T loadWithLock(PK id, LockMode lock);

	public abstract void updateWithLock(T entity, LockMode lock);

	public abstract void deleteWithLock(T entity, LockMode lock);

	public abstract void deleteByKeyWithLock(PK id, LockMode lock);

	/**
	 * 加锁指定的实体
	 * 
	 * @param entity
	 * @param lock
	 */
	public abstract void lock(T entity, LockMode lock);

	/**
	 * 强制初始化指定的实体
	 * 
	 * @param proxy
	 */
	public abstract void initialize(Object proxy);

	/**
	 * 强制立即更新缓冲数据到数据库（否则仅在事务提交时才更新）
	 */
	public abstract void flush();
	
	/**
	 * 根据hql语句执行
	 * @param hql
	 * @return
	 */
	public abstract int executeUpdate(String hql);

}
