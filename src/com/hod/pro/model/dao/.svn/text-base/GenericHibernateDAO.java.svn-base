package com.hod.pro.model.dao;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.beanutils.PropertyUtils;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.impl.CriteriaImpl;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

/**
 * @author yixiang
 */
public class GenericHibernateDAO<T extends Serializable, PK extends Serializable> extends HibernateDaoSupport implements GenericDAO<T, PK>
	{

	private Class<T> entityClass;

	// 构造方法，根据实例类自动获取实体类类型
	public GenericHibernateDAO() {
		this.entityClass = null;
		Class c = getClass();
		Type t = c.getGenericSuperclass();
		if (t instanceof ParameterizedType) {
			Type[] p = ((ParameterizedType) t).getActualTypeArguments();
			this.entityClass = (Class<T>) p[0];
		}
	}

	/* (non-Javadoc)
	 * @see dao.GenericDAO#bulkUpdate(java.lang.String)
	 */
	public int bulkUpdate(String queryString) {
		return getHibernateTemplate().bulkUpdate(queryString);
	}

	/* (non-Javadoc)
	 * @see dao.GenericDAO#bulkUpdate(java.lang.String, java.lang.Object[])
	 */
	public int bulkUpdate(String queryString, Object[] values) {
		return getHibernateTemplate().bulkUpdate(queryString, values);
	}

	/* (non-Javadoc)
	 * @see dao.GenericDAO#findById(PK)
	 */
	public T findById(Serializable id) {
		return (T) getHibernateTemplate().get(entityClass, id);
	}

	/* (non-Javadoc)
	 * @see dao.GenericDAO#getRowCount(java.lang.String)
	 */
	public Integer getRowCount(String HQL) {
		return (Integer) getHibernateTemplate().find(HQL).size();
	}

	/* (non-Javadoc)
	 * @see dao.GenericDAO#getRowCount(org.hibernate.criterion.DetachedCriteria)
	 */
	public Integer getRowCount(DetachedCriteria criteria) {
		criteria.setProjection(Projections.rowCount());
		List list = this.findByCriteria(criteria, 0, 1);
		return (Integer) list.get(0);
//		try {
//			Criteria dc = criteria.getExecutableCriteria(this.getSession());  
//			List orderEntrys = new ArrayList();  
//			Field field = CriteriaImpl.class.getDeclaredField("orderEntries");  
//			field.setAccessible(true);  //私有属性
//			orderEntrys = (List) field.get(dc);  
//			field.set(dc,new ArrayList());  
//			dc.setProjection(Projections.rowCount());  
//			int totalCount = Integer.valueOf(dc.uniqueResult().toString());  
//			dc.setProjection(null);  
//			field.set(dc,orderEntrys);  
//			return totalCount;    
//		} catch (Exception e) {
//			e.printStackTrace();
//			return 0;
//		}
	}

	/* (non-Javadoc)
	 * @see dao.GenericDAO#getStatValue(org.hibernate.criterion.DetachedCriteria, java.lang.String, java.lang.String)
	 */
	public Object getStatValue(DetachedCriteria criteria, String propertyName,
			String StatName) {
		if (StatName.toLowerCase().equals("max"))
			criteria.setProjection(Projections.max(propertyName));
		else if (StatName.toLowerCase().equals("min"))
			criteria.setProjection(Projections.min(propertyName));
		else if (StatName.toLowerCase().equals("avg"))
			criteria.setProjection(Projections.avg(propertyName));
		else if (StatName.toLowerCase().equals("sum"))
			criteria.setProjection(Projections.sum(propertyName));
		else
			return null;
		List list = this.findByCriteria(criteria, 0, 1);
		return list.get(0);
	}

	/* (non-Javadoc)
	 * @see dao.GenericDAO#findLikeByEntity(T, java.lang.String[])
	 */
	public List<T> findLikeByEntity(T entity, String[] propertyNames) {
		Criteria criteria = this.createCriteria();
		for (String property : propertyNames) {
			try {
				Object value = PropertyUtils.getProperty(entity, property);
				if (value instanceof String) {
					// MatchMode.ANYWHERE 相当于 %value%
					criteria.add(Restrictions.like(property, (String) value,
							MatchMode.ANYWHERE));
					criteria.addOrder(Order.asc(property));
				} else {
					criteria.add(Restrictions.eq(property, value));
					criteria.addOrder(Order.asc(property));
				}
			} catch (Exception ex) {
				// 忽略无效的检索参考数据。
			}
		}
		return (List<T>) criteria.list();
	}

	/* (non-Javadoc)
	 * @see dao.GenericDAO#findCacheByHQL(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	public List findCacheByHQL(final String HQL) {
		return this.getHibernateTemplate().executeFind(new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				return session.createQuery(HQL).setCacheable(true).list();
			}
		});
	}

	/* (non-Javadoc)
	 * @see dao.GenericDAO#findByHQL(java.lang.String)
	 */
	public List findByHQL(String queryString) {
		return getHibernateTemplate().find(queryString);
	}

	/* (non-Javadoc)
	 * @see dao.GenericDAO#findByHQL(int, int, java.lang.String)
	 */
	public List<T> findByHQL(final int currPage, final int pagesize,
			final String hql) {
		List list = this.getHibernateTemplate().executeFind(
				new HibernateCallback() {
					public Object doInHibernate(Session session)
							throws HibernateException, SQLException {
						List result = session.createQuery(hql).setFirstResult(
								currPage > 1 ? (currPage - 1) * pagesize : 0)
								.setMaxResults(pagesize).list();
						return result;
					}
				});
		return list;
	}
	
	/**
	 * 
	 * @param currPage 当前页数
	 * @param pagesize 条数
	 * @param SQL sql语句
	 * @return
	 */
	public List findByNHQL(final int currPage, final int pagesize,final String SQL) {
		List list = this.getHibernateTemplate().executeFind(
				new HibernateCallback() {
					public Object doInHibernate(Session session)
							throws HibernateException, SQLException {
						SQLQuery query = session.createSQLQuery(SQL);
						query.setFirstResult(
								currPage > 1 ? (currPage - 1) * pagesize : 0)
								.setMaxResults(pagesize);
						return query.list();
					}
				});
		return list;

	}

	/* (non-Javadoc)
	 * @see dao.GenericDAO#findByNHQL(java.lang.String)
	 */
	public List findByNHQL(final String SQL) {
		List list = this.getHibernateTemplate().executeFind(
				new HibernateCallback() {
					public Object doInHibernate(Session session)
							throws HibernateException, SQLException {
						SQLQuery query = session.createSQLQuery(SQL);
						return query.list();
					}
				});
		return list;

	}

	/* (non-Javadoc)
	 * @see dao.GenericDAO#findByCriteria(org.hibernate.criterion.DetachedCriteria)
	 */
	public List findByCriteria(DetachedCriteria criteria) {
		return getHibernateTemplate().findByCriteria(criteria);
	}

	/* (non-Javadoc)
	 * @see dao.GenericDAO#findByCriteria(org.hibernate.criterion.DetachedCriteria, int, int)
	 */
	public List findByCriteria(DetachedCriteria criteria, int firstResult,int maxResults) {
		return getHibernateTemplate().findByCriteria(criteria, firstResult,
				maxResults);
	}

	/* (non-Javadoc)
	 * @see dao.GenericDAO#findEqualByEntity(T, java.lang.String[])
	 */
	public List<T> findEqualByEntity(T entity, String[] propertyNames) {
		Criteria criteria = this.createCriteria();
		Example exam = Example.create(entity);
		exam.excludeZeroes();
		String[] defPropertys = getSessionFactory().getClassMetadata(
				entityClass).getPropertyNames();
		for (String defProperty : defPropertys) {
			int ii = 0;
			for (ii = 0; ii < propertyNames.length; ++ii) {
				if (defProperty.equals(propertyNames[ii])) {
					criteria.addOrder(Order.asc(defProperty));
					break;
				}
			}
			if (ii == propertyNames.length) {
				exam.excludeProperty(defProperty);
			}
		}
		criteria.add(exam);
		return (List<T>) criteria.list();
	}

	/* (non-Javadoc)
	 * @see dao.GenericDAO#find(java.lang.String, java.lang.Object[])
	 */
	public List find(String queryString, Object[] values) {
		return getHibernateTemplate().find(queryString, values);
	}

	/* (non-Javadoc)
	 * @see dao.GenericDAO#findByNamedParam(java.lang.String, java.lang.String[], java.lang.Object[])
	 */
	public List findByNamedParam(String queryString, String[] paramNames,
			Object[] values) {
		return getHibernateTemplate().findByNamedParam(queryString, paramNames,
				values);
	}

	/* (non-Javadoc)
	 * @see dao.GenericDAO#findByNamedQuery(java.lang.String)
	 */
	public List findByNamedQuery(String queryName) {
		return getHibernateTemplate().findByNamedQuery(queryName);
	}

	/* (non-Javadoc)
	 * @see dao.GenericDAO#findByNamedQuery(java.lang.String, java.lang.Object[])
	 */
	public List findByNamedQuery(String queryName, Object[] values) {
		return getHibernateTemplate().findByNamedQuery(queryName, values);
	}

	/* (non-Javadoc)
	 * @see dao.GenericDAO#findByNamedQueryAndNamedParam(java.lang.String, java.lang.String[], java.lang.Object[])
	 */
	public List<T> findByNamedQueryAndNamedParam(String queryName,
			String[] paramNames, Object[] values) {
		return getHibernateTemplate().findByNamedQueryAndNamedParam(queryName,
				paramNames, values);
	}

	/* (non-Javadoc)
	 * @see dao.GenericDAO#iterate(java.lang.String)
	 */
	public Iterator<T> iterate(String queryString) {
		return getHibernateTemplate().iterate(queryString);
	}

	/* (non-Javadoc)
	 * @see dao.GenericDAO#iterate(java.lang.String, java.lang.Object[])
	 */
	public Iterator<T> iterate(String queryString, Object[] values) {
		return getHibernateTemplate().iterate(queryString, values);
	}

	/* (non-Javadoc)
	 * @see dao.GenericDAO#closeIterator(java.util.Iterator)
	 */
	public void closeIterator(Iterator it) {
		getHibernateTemplate().closeIterator(it);
	}

	/* (non-Javadoc)
	 * @see dao.GenericDAO#createDetachedCriteria()
	 */
	public DetachedCriteria createDetachedCriteria() {
		return DetachedCriteria.forClass(this.entityClass);
	}

	/* (non-Javadoc)
	 * @see dao.GenericDAO#createCriteria()
	 */
	public Criteria createCriteria() {
		return this.createDetachedCriteria().getExecutableCriteria(
				this.getSession());
	}

	/* (non-Javadoc)
	 * @see dao.GenericDAO#update(T)
	 */
	public void update(T entity) {
		getHibernateTemplate().update(entity);
	}

	/* (non-Javadoc)
	 * @see dao.GenericDAO#save(T)
	 */
	public void save(T entity) throws Exception {
		getSession().save(entity);
	}

	/* (non-Javadoc)
	 * @see dao.GenericDAO#saveOrUpdate(T)
	 */
	public void saveOrUpdate(T entity) {
		getHibernateTemplate().saveOrUpdate(entity);
	}

	/* (non-Javadoc)
	 * @see dao.GenericDAO#saveOrUpdateAll(java.util.Collection)
	 */
	public void saveOrUpdateAll(Collection<T> entities) {
		getHibernateTemplate().saveOrUpdateAll(entities);
	}

	/* (non-Javadoc)
	 * @see dao.GenericDAO#delete(T)
	 */
	public void delete(T entity) throws Exception {
		getHibernateTemplate().delete(entity);
	}

	/* (non-Javadoc)
	 * @see dao.GenericDAO#deleteByKey(PK)
	 */
	public void deleteByKey(PK id) throws Exception {
		if (this.getHibernateTemplate().get(entityClass,id)!=null) {
			this.delete(this.load(id));	
		}
	}

 

	/* (non-Javadoc)
	 * @see dao.GenericDAO#deleteAll(java.util.Collection)
	 */
	public void deleteAll(Collection<T> entities) {
		getHibernateTemplate().deleteAll(entities);
	}

	/* (non-Javadoc)
	 * @see dao.GenericDAO#load(PK)
	 */
	public T load(PK id) {
		return (T) getHibernateTemplate().load(entityClass, id);
	}

	/* (non-Javadoc)
	 * @see dao.GenericDAO#loadAll()
	 */
	public List<T> loadAll() {
		return (List<T>) getHibernateTemplate().loadAll(entityClass);
	}
	
	/* (non-Javadoc)
	 * @see dao.GenericDAO#getWithLock(PK, org.hibernate.LockMode)
	 */
	public T getWithLock(PK id, LockMode lock) {
		T t = (T) getHibernateTemplate().get(entityClass, id, lock);
		if (t != null) {
			this.flush(); // 立即刷新，否则锁不会生效。
		}
		return t;
	}

	/* (non-Javadoc)
	 * @see dao.GenericDAO#loadWithLock(PK, org.hibernate.LockMode)
	 */
	public T loadWithLock(PK id, LockMode lock) {
		T t = (T) getHibernateTemplate().load(entityClass, id, lock);
		if (t != null) {
			this.flush(); // 立即刷新，否则锁不会生效。
		}
		return t;
	}

	/* (non-Javadoc)
	 * @see dao.GenericDAO#updateWithLock(T, org.hibernate.LockMode)
	 */
	public void updateWithLock(T entity, LockMode lock) {
		getHibernateTemplate().update(entity, lock);
		this.flush(); // 立即刷新，否则锁不会生效。
	}

	/* (non-Javadoc)
	 * @see dao.GenericDAO#deleteWithLock(T, org.hibernate.LockMode)
	 */
	public void deleteWithLock(T entity, LockMode lock) {
		getHibernateTemplate().delete(entity, lock);
		this.flush(); // 立即刷新，否则锁不会生效。
	}

	/* (non-Javadoc)
	 * @see dao.GenericDAO#deleteByKeyWithLock(PK, org.hibernate.LockMode)
	 */
	public void deleteByKeyWithLock(PK id, LockMode lock) {
		this.deleteWithLock(this.load(id), lock);

	}

	/* (non-Javadoc)
	 * @see dao.GenericDAO#lock(T, org.hibernate.LockMode)
	 */
	public void lock(T entity, LockMode lock) {
		getHibernateTemplate().lock(entity, lock);
	}

	/* (non-Javadoc)
	 * @see dao.GenericDAO#initialize(java.lang.Object)
	 */
	public void initialize(Object proxy) {
		getHibernateTemplate().initialize(proxy);
	}

	/* (non-Javadoc)
	 * @see dao.GenericDAO#flush()
	 */
	public void flush() {
		getHibernateTemplate().flush();
	}
	
	public int executeUpdate(String updateString) {
		  return getSession().createQuery(updateString).executeUpdate();
	}

	public void updateByHql(final String HQL) {
		new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
					Query query = session.createQuery(HQL);
					return query.executeUpdate();
				}
			};
		
	}

}
