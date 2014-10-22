package com.hod.pro.model.service;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;

import com.hod.pro.model.dao.GenericDAO;

/**
 * ���������������ɣ�����ӿں�ʵ�֡� ������ԭ������һЩ�����ȵ��࣬�����������־ò��и�ϸ���ȵ����ݷ��ʷ�����װ������
 * ���ǵ�Ҫ�����ǵķ��������κ��ض������ݿ����Ϣ�����뿪��������Ҫ��ȡһЩ����Ĵ�ʩ�Ա�֤���ʵĳ���
 * ��Ϊ�������Ҫ�������ݷ��ʲ㲢�������񻮽磬���Լ򵥵��ڷ�����ȡһ�����ݿ����Ӳ�ʹ��������ݿ��������������񻮽��ƺ����������ס�
 * ������������Ļ������ǾͻὫ�ض���JDBC���������뵽�������,���Υ�������ǵ�Ŀ�ꡣ
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
