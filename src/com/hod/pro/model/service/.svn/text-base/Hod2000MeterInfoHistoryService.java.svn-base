package com.hod.pro.model.service;

import java.util.List;
import java.util.Map;

import com.hod.pro.model.dao.IHod2000MeterInfoHistoryDAO;

/**
 * @author yixiang
 */
 
public class Hod2000MeterInfoHistoryService extends Service implements IHod2000MeterInfoHistoryService {

	private IHod2000MeterInfoHistoryDAO hod2000MeterInfoHistoryDAO;
	
	public void setHod2000MeterInfoHistoryDAO(
			IHod2000MeterInfoHistoryDAO hod2000MeterInfoHistoryDAO) {
		this.hod2000MeterInfoHistoryDAO = hod2000MeterInfoHistoryDAO;
	}

	public List findMeterWarm() {
		return hod2000MeterInfoHistoryDAO.findMeterWarm();
	}

	public List findByMeterName(String meterName, String months) {
		return hod2000MeterInfoHistoryDAO.findByMeterName(meterName,months);
	}
 

}
