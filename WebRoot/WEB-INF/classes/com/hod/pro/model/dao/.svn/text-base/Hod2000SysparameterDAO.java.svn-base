package com.hod.pro.model.dao;

import java.util.List;

import com.hod.pojo.Hod2000Sysparameter;

/**
 * Hod2000SysparameterDAO
 * @author yixiang
 */
public class Hod2000SysparameterDAO extends GenericHibernateDAO<Hod2000Sysparameter, String> implements IHod2000SysparameterDAO {

	public List findByType(int type) {
		return findByHQL("from Hod2000Sysparameter where ptype="+type);
	}
 
}
