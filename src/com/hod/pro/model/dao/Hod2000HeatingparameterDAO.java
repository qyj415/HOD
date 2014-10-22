package com.hod.pro.model.dao;

import java.util.List;

import com.hod.pojo.Hod2000Heatingparameter;

/**
 * Hod2000HeatingparameterDAO
 * @author yixiang
 */
public class Hod2000HeatingparameterDAO extends GenericHibernateDAO<Hod2000Heatingparameter, String> implements IHod2000HeatingparameterDAO {

	public int findReceiveParams() {
		List list=findByNHQL("select count(*) from hod2000_heatingparameter where hp_start is not null and hp_end is not null and hp_type is not null");
		if(list.size()>0)
			return Integer.parseInt(list.get(0).toString());
		else
			return 0;
	}
 
}
