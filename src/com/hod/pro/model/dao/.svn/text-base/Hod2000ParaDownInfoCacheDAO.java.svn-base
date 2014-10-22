package com.hod.pro.model.dao;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.hod.pojo.Hod2000ParaDownInfoCache;

/**
 * Hod2000ParaDownInfoCacheDAO
 * @author JSmart Tools
 */
public class Hod2000ParaDownInfoCacheDAO extends GenericHibernateDAO<Hod2000ParaDownInfoCache, String> implements IHod2000ParaDownInfoCacheDAO {

	public Hod2000ParaDownInfoCache findById(Integer downId) {
		DetachedCriteria de=DetachedCriteria.forClass(Hod2000ParaDownInfoCache.class);
		de.add(Restrictions.eq("downId", downId));
		return (Hod2000ParaDownInfoCache) findByCriteria(de).get(0);
	}
 
}
