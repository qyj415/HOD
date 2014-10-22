package com.hod.pro.model.service;

import java.util.List;

import com.hod.pojo.Hod2000City;
import com.hod.pro.model.dao.IHod2000CityDAO;

/**
 * @author yixiang
 */
 
public class Hod2000CityService extends Service implements IHod2000CityService {

	private IHod2000CityDAO cityDAO;
	
	public void setCityDAO(IHod2000CityDAO cityDAO) {
		this.cityDAO = cityDAO;
	}

	public List findByRegionId(int id) {
		// TODO Auto-generated method stub
		return cityDAO.findByRegionId(id);
	}

	public List findByCityCode(String code) {
		return genericDAO.findByNHQL("select city_id,city_name,city_code from hod2000_city c inner join hod2000_region r on c.region_id=r.region_id where region_code+city_code='"+code+"'");
	}
 

}
