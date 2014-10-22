package com.hod.pro.model.service;

import java.util.List;

import com.hod.pro.model.dao.IHod2000MeterInfoTempDAO;

/**
 * @author yixiang
 */
 
public class Hod2000MeterInfoTempService extends Service implements IHod2000MeterInfoTempService {

	private IHod2000MeterInfoTempDAO hod2000MeterInfoTempDAO;
	
	public void setHod2000MeterInfoTempDAO(
			IHod2000MeterInfoTempDAO hod2000MeterInfoTempDAO) {
		this.hod2000MeterInfoTempDAO = hod2000MeterInfoTempDAO;
	}

	public List findByMeterName(String meterName) {
		return hod2000MeterInfoTempDAO.findByMeterName(meterName);
	}

	public List getDataByIds(String ids) {
		return genericDAO.findByNHQL("select read_time,temp.meter_name,current_energy,accumulate_flow,meter_flow,accumulate_time,supply_temper,back_temper,valve_status,battery_status,isonline from hod2000_meter meter right join hod2000_meterInfoTemp temp on meter.meter_name=temp.meter_name where meter.meter_id in ("+ids+") order by temp.read_time desc");
	}
 

}
