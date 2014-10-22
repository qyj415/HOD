package com.hod.pro.model.dao;

import java.util.List;

import com.hod.pojo.Hod2000Region;

/**
 * Hod2000RegionDAO
 * @author yixiang
 */
public class Hod2000RegionDAO extends GenericHibernateDAO<Hod2000Region, String> implements IHod2000RegionDAO {

	public List findAll() {
		return findByNHQL("select region_id,region_name,region_code from hod2000_region");
	}
}
