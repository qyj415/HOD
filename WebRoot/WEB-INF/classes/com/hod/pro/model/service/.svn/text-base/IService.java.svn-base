package com.hod.pro.model.service;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;

/**
 * @author yixiang
 */
 
public interface IService {

	public abstract Object findById(Serializable id) throws Exception;

	public List findByCriteria(int firstResult, int maxResults,
			DetachedCriteria criteria) throws Exception;
	
	public abstract List findByCriteria(DetachedCriteria criteria);
	
	public abstract int getRowCount(DetachedCriteria criteria) throws Exception;

	public abstract boolean deleteByParam(Serializable[] args) throws Exception;

	public abstract void save(Object entity) throws Exception;

	public abstract void update(Object entity) throws Exception;

	public abstract List findByNHQL(String sql) throws Exception;

	public  abstract void deleteByKey(Serializable id) throws Exception;
	
	public abstract List findByHQL(String queryString);
	
	public abstract List find(String queryString, Object[] values);
	
	public abstract void delete(Object entity) throws Exception;
	
	public abstract void flush();
	
	public abstract void saveOrUpdate(Object entity);
	
	public abstract List findByNHQL(int currPage,int pagesize,String hql);
	
	public abstract List findByHQL(int currPage,int pagesize,String hql);
	
	public abstract void saveOrUpdateAll(List entities);
	
	public abstract int executeUpdate(String hql);
	
}
