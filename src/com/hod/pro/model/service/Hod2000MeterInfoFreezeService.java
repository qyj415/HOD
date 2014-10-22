package com.hod.pro.model.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.hod.pro.model.dao.IHod2000MeterInfoFreezeDAO;

/**
 * @author yixiang
 */
 
public class Hod2000MeterInfoFreezeService extends Service implements IHod2000MeterInfoFreezeService {

	private IHod2000MeterInfoFreezeDAO hod2000MeterInfoFreezeDAO;
	
	public void setHod2000MeterInfoFreezeDAO(
			IHod2000MeterInfoFreezeDAO hod2000MeterInfoFreezeDAO) {
		this.hod2000MeterInfoFreezeDAO = hod2000MeterInfoFreezeDAO;
	}

	public double getEndPower(String meterName, Date hpEnd) {
		return hod2000MeterInfoFreezeDAO.getEndPower(meterName, hpEnd);
	}

	public double getStartPower(String meterName, Date hpStart) {
		return hod2000MeterInfoFreezeDAO.getStartPower(meterName, hpStart);
	}
 

}
