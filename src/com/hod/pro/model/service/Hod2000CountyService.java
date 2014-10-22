package com.hod.pro.model.service;

import java.util.List;

import com.hod.pojo.Hod2000County;
import com.hod.pro.model.dao.IHod2000CountyDAO;

/**
 * @author yixiang
 */
 
public class Hod2000CountyService extends Service implements IHod2000CountyService {

	private IHod2000CountyDAO countyDAO;
	
	public void setCountyDAO(IHod2000CountyDAO countyDAO) {
		this.countyDAO = countyDAO;
	}

	public List findByCityId(int id) {
		// TODO Auto-generated method stub
		return countyDAO.findByCityId(id);
	}

	public List findByCountyCode(String code) {
		return genericDAO.findByNHQL("select county_id,county_name,county_code from view_county where addressCode='"+code+"'");
	}
 

}
