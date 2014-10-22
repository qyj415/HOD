package com.hod.pro.model.dao;

import java.util.List;

import com.hod.pojo.Hod2000County;

/**
 * Hod2000CountyDAO
 * @author yixiang
 */
public class Hod2000CountyDAO extends GenericHibernateDAO<Hod2000County, String> implements IHod2000CountyDAO {

	public List findByCityId(int id) {
		// TODO Auto-generated method stub
		return findByNHQL("select county_id,county_name,county_code from hod2000_county where city_id="+id);
	}
 
}
