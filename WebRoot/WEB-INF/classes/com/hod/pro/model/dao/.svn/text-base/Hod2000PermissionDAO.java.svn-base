package com.hod.pro.model.dao;

import java.util.List;

import com.hod.pojo.Hod2000Permission;

/**
 * Hod2000PermissionDAO
 * @author yixiang
 */
public class Hod2000PermissionDAO extends GenericHibernateDAO<Hod2000Permission, String> implements IHod2000PermissionDAO {

	public List findByPermCodeName(String[] str) {
		List list = find("from Hod2000Permission where permCodeName=?",str);
		return list;
	}
 
}
