package com.hod.pro.model.dao;

import java.util.List;

import com.hod.pojo.Hod2000Village;

/**
 * Hod2000VillageDAO
 * @author yixiang
 */
public class Hod2000VillageDAO extends GenericHibernateDAO<Hod2000Village, String> implements IHod2000VillageDAO {

	public List findByCountyId(int id) {
		// TODO Auto-generated method stub
		return findByNHQL("select village_id,village_name,village_code from hod2000_village where county_id="+id);
	}
 
}
