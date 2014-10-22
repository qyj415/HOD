package com.hod.pro.model.service;

import java.util.List;

import com.hod.pojo.Hod2000Region;
import com.hod.pro.model.dao.IHod2000RegionDAO;

/**
 * @author yixiang
 */
 
public class Hod2000RegionService extends Service implements IHod2000RegionService {

	private IHod2000RegionDAO hod2000RegionDAO;
	
	public void setHod2000RegionDAO(IHod2000RegionDAO hod2000RegionDAO) {
		this.hod2000RegionDAO = hod2000RegionDAO;
	}

	public List findAll() {
		return hod2000RegionDAO.findAll();
	}

	public List findByCode(String regionCode) {
		return genericDAO.findByNHQL("select region_id,region_name,region_code from hod2000_region where region_code='"+regionCode+"'");
	}
}
