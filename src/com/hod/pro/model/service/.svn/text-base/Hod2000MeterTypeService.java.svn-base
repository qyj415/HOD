package com.hod.pro.model.service;

import com.hod.pojo.Hod2000MeterType;
import com.hod.pro.model.dao.IHod2000MeterTypeDAO;

/**
 * @author yixiang
 */
 
public class Hod2000MeterTypeService extends Service implements IHod2000MeterTypeService {

	private IHod2000MeterTypeDAO hod2000MeterTypeDAO;
	
	public void setHod2000MeterTypeDAO(IHod2000MeterTypeDAO hod2000MeterTypeDAO) {
		this.hod2000MeterTypeDAO = hod2000MeterTypeDAO;
	}

	public Hod2000MeterType findByTypeCode(String meterType) {
		return hod2000MeterTypeDAO.findByTypeCode(meterType);
	}

	public int findByName(String typeName) {
		return Integer.parseInt(hod2000MeterTypeDAO.findByNHQL("select count(*) from hod2000_meterType where type_name='"+typeName+"'").get(0).toString());
	}

	public int findByName(String typeName, Integer typeIndex) {
		return Integer.parseInt(hod2000MeterTypeDAO.findByNHQL("select count(*) from hod2000_meterType where type_name='"+typeName+"' and type_index!="+typeIndex).get(0).toString());
	}
 

}
