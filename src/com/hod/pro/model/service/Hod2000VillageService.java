package com.hod.pro.model.service;

import java.util.List;

import com.hod.pojo.Hod2000Village;
import com.hod.pro.model.dao.IHod2000VillageDAO;

/**
 * @author yixiang
 */
 
public class Hod2000VillageService extends Service implements IHod2000VillageService {

	private IHod2000VillageDAO hod2000VillageDAO;
	
	public void setHod2000VillageDAO(IHod2000VillageDAO hod2000VillageDAO) {
		this.hod2000VillageDAO = hod2000VillageDAO;
	}

	public List findByCountyId(int id) {
		// TODO Auto-generated method stub
		return hod2000VillageDAO.findByCountyId(id);
	}

	public List findByVillageCode(String code) {
		return genericDAO.findByNHQL("select village_id,village_name,village_code from view_village where addressCode='"+code+"'");
	}
 

}
