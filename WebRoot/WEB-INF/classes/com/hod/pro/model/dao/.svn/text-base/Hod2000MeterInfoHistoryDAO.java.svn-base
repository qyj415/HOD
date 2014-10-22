package com.hod.pro.model.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hod.pojo.Hod2000MeterInfoHistory;

/**
 * Hod2000MeterInfoHistoryDAO
 * 
 * @author yixiang
 */
public class Hod2000MeterInfoHistoryDAO extends
		GenericHibernateDAO<Hod2000MeterInfoHistory, String> implements
		IHod2000MeterInfoHistoryDAO {

	public List findMeterWarm() {
		//数据库进行了更新，表计的阀门、电池与在线状态都添加到了表计表中
		//String sql="select meter_id, isonline,meter_name,valve_status,battery_status,meter_position_name from hod2000_meter where meter_able=1 and ( valve_status=2 or battery_status=1 or isonline=2)";
		String sql="select meter_id, isonline,meter_name,valve_status,battery_status,meter_position_name,eeprom_status,flowsensor_status,tepdown_status,tepup_status from hod2000_meter where meter_able=1 and (battery_status=1 or isonline=2 or eeprom_status=1 or flowsensor_status=1 or tepdown_status=1 or tepup_status=1)";
		return findByNHQL(sql);
	}

	public List findByMeterName(String meterName, String months) {
		return findByNHQL("select current_energy,read_time,meter_name,accumulate_flow,meter_flow,accumulate_time,supply_temper,back_temper,t_id from hod2000_meterInfoHistory where substring(CONVERT(varchar(100), read_time, 23),0,8)='"+months+"' and meter_name='"+meterName+"' order by read_time");
	}

	public List findEnergyMeterName(String meterName,String hpStartTime) {
		return findByNHQL("select current_energy from hod2000_meterInfoHistory where meter_name='"+meterName+"' and read_time>='"+hpStartTime+"' order by read_time");
	}

	public List findEnergyMeterName2(String meterName, String hpEndTime) {
		return findByNHQL("select current_energy from hod2000_meterInfoHistory where meter_name='"+meterName+"' and read_time<='"+hpEndTime+"' order by read_time");
	}

	public double findEnergyMeterNameFirst(String meterName,String hpStartTime,String hpEndTime) {
		List list=findByNHQL("select current_energy from hod2000_meterInfoHistory where meter_name='"+meterName+"' and read_time>='"+hpStartTime+"' and read_time<='"+hpEndTime+"' order by read_time");
		if(list.size()>0)
			return Double.parseDouble(list.get(0).toString());
		return 0;
	}

	public double findEnergyMeterNameEnd(String meterName, String hpStartTime,
			String hpEndTime) {
		List list=findByNHQL("select current_energy from hod2000_meterInfoHistory where meter_name='"+meterName+"' and read_time>='"+hpStartTime+"' and read_time<='"+hpEndTime+"' order by read_time");
		if(list.size()>0)
			return Double.parseDouble(list.get(list.size()-1).toString());
		return 0;
	}

}
