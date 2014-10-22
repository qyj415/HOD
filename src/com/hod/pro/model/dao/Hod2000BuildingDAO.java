package com.hod.pro.model.dao;

import java.util.List;

import com.hod.pojo.Hod2000Building;

/**
 * Hod2000BuildingDAO
 * @author yixiang
 */
public class Hod2000BuildingDAO extends GenericHibernateDAO<Hod2000Building, String> implements IHod2000BuildingDAO {

	public List findByLngLat(String lng1, String lng2, String lat1, String lat2) {
		String sqlString = "select building_name ,building_id,building_longitude,building_latitude,community_id  from hod2000_building where " +
		 "(building_longitude > "+lng1+" and  building_longitude < "+lng2+") and (building_latitude > "+lat1+" and building_latitude < " + lat2 + ") " +
		 		" and (building_latitude <> -1 and building_latitude <> -1 )";
		List list = findByNHQL(sqlString);
		return list;
	}

	public List findByCommunityId(int id) {
		return findByNHQL("select building_id,building_name,building_longitude,building_latitude,building_code from hod2000_building where community_id="+id);
	}
 
}
