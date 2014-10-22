package com.hod.pro.model.service;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;

import com.hod.pro.model.dao.GenericDAO;

/**
 * 服务层由两部分组成：服务接口和实现。 服务类原本就是一些粗粒度的类，由它们来将持久层中更细粒度的数据访问方法组装起来。
 * 考虑到要将我们的服务类与任何特定于数据库的信息都隔离开，我们需要采取一些额外的措施以保证合适的抽象。
 * 因为服务层需要调用数据访问层并处理事务划界，所以简单地在服务层获取一个数据库连接并使用这个数据库连接来管理事务划界似乎将更加容易。
 * 但如果这样做的话，我们就会将特定于JDBC的语义引入到服务层中,这会违背了我们的目标。
 * 
 * @author JSmart
 */
public class Service implements IService {
	public GenericDAO genericDAO;

	public void setGenericDAO(GenericDAO genericDAO) {
		this.genericDAO = genericDAO;
	}

	public boolean deleteByParam(Serializable[] args) throws Exception {
		for (int i = 0; i < args.length; i++) {
			genericDAO.deleteByKey(Integer.parseInt(args[i].toString()));
		}
		return true;
	}

	public void save(Object entity) throws Exception {
		genericDAO.save((Serializable) entity);
	}

	public void update(Object entity) throws Exception {
		genericDAO.update((Serializable) entity);
	}

	public Object findById(Serializable id) throws Exception {
		return genericDAO.findById(id);
	}

	public List findByCriteria(int firstResult, int maxResults,DetachedCriteria criteria) throws Exception {
		return genericDAO.findByCriteria(criteria, (firstResult - 1)* maxResults, maxResults);
	}

	public int getRowCount(DetachedCriteria criteria) throws Exception {
		return genericDAO.getRowCount(criteria);
	}

	public  List findByNHQL(String queryString) throws Exception {
		return genericDAO.findByNHQL(queryString);
	}
	public void deleteByKey(Serializable id) throws Exception {
		 genericDAO.deleteByKey(id);
	}

	public List findByHQL(String queryString) {
		return genericDAO.findByHQL(queryString);
	}

	public List find(String queryString, Object[] values) {
		return genericDAO.find(queryString, values);
	}

	public List findByCriteria(DetachedCriteria criteria) {
		return genericDAO.findByCriteria(criteria);
	}

	public void delete(Object entity) throws Exception {
		genericDAO.delete((Serializable) entity);
		
	}

	public void flush() {
		genericDAO.flush();
	}
	
	public void saveOrUpdate(Object entity)
	{
		genericDAO.saveOrUpdate((Serializable) entity);
	}

	public List findByNHQL(int currPage, int pagesize, String SQL) {
		return genericDAO.findByNHQL(currPage, pagesize, SQL);
	}

	public List findByHQL(int currPage, int pagesize, String hql) {
		return genericDAO.findByHQL(currPage, pagesize, hql);
	}

	public void saveOrUpdateAll(List entities) {
		genericDAO.saveOrUpdateAll(entities);
		
	}

	public int executeUpdate(String hql) {
		return genericDAO.executeUpdate(hql);
	}
}
