package com.hod.pro.model.service;

import com.hod.pro.model.dao.IHod2000BatchMeterErrorDAO;

public class Hod2000BatchMeterErrorService extends Service implements IHod2000BatchMeterErrorService{
	
	private IHod2000BatchMeterErrorDAO hod2000BatchMeterErrorDAO;

	public int findByCardNo(String meterName) {
		return Integer.parseInt(hod2000BatchMeterErrorDAO.findByNHQL("select count(*) from hod2000_batchMeterError where meter_name='"+meterName+"'").get(0).toString());
	}

	public void setHod2000BatchMeterErrorDAO(
			IHod2000BatchMeterErrorDAO hod2000BatchMeterErrorDAO) {
		this.hod2000BatchMeterErrorDAO = hod2000BatchMeterErrorDAO;
	}
	
}
