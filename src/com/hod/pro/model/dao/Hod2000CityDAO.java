package com.hod.pro.model.dao;

import java.util.List;

import com.hod.pojo.Hod2000City;

/**
 * Hod2000CityDAO
 * @author yixiang
 */
public class Hod2000CityDAO extends GenericHibernateDAO<Hod2000City, String> implements IHod2000CityDAO {

	public List findByRegionId(int id) {
		// TODO Auto-generated method stub
		return findByNHQL("select city_id,city_name,city_code from hod2000_city where region_id="+id);
	}
 
}
