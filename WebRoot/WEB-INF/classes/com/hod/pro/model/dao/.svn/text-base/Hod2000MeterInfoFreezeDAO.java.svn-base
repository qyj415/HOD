package com.hod.pro.model.dao;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hod.pojo.Hod2000MeterInfoFreeze;
import com.hod.util.Utils;

/**
 * Hod2000MeterInfoFreezeDAO
 * @author yixiang
 */
public class Hod2000MeterInfoFreezeDAO extends GenericHibernateDAO<Hod2000MeterInfoFreeze, String> implements IHod2000MeterInfoFreezeDAO {

	public double getEndPower(String meterName, Date hpEnd) {
		List list=findByNHQL("select clear_energy from hod2000_meterInfoFreeze where meter_name='"+meterName+"' and cleat_date='"+Utils.dateToStr(hpEnd)+"'");
		if(list.size()>0)
		{
			return Double.parseDouble(((Hod2000MeterInfoFreeze) list.get(0)).getClearEnergy());
		}
		else
			return 0;
	}

	public double getStartPower(String meterName, Date hpStart) {
		List list=findByNHQL("select clear_energy from hod2000_meterInfoFreeze where meter_name='"+meterName+"' and cleat_date<'"+Utils.dateToStr(hpStart)+"' order by cleat_date desc");
		if(list.size()>0)
		{
			return Double.parseDouble(list.get(0).toString());
		}
		else
			return 0;
	}

	//供暖季内月冻结最后一条记录
	public double findEnd(String meterName, String hpStartTime, String hpEndTime) {
		List list=findByNHQL("select clear_energy from hod2000_meterInfoFreeze where meter_name='"+meterName+"' and read_time>='"+hpStartTime+"' and read_time<='"+hpEndTime+"' order by read_time");
		if(list.size()>0)
			return Double.parseDouble(list.get(list.size()-1).toString());
		return 0;
	}

	//供暖季内月冻结第一条记录
	public double findFirst(String meterName, String hpStartTime,
			String hpEndTime) {
		List list=findByNHQL("select clear_energy from hod2000_meterInfoFreeze where meter_name='"+meterName+"' and read_time>='"+hpStartTime+"' and read_time<='"+hpEndTime+"' order by read_time");
		if(list.size()>0)
			return Double.parseDouble(list.get(0).toString());
		return 0;
	}

	public List findLastEnergy(String meterName, String hpStartTime) {
		return findByNHQL("select clear_energy from hod2000_meterInfoFreeze where meter_name='"+meterName+"' and read_time<'"+hpStartTime+"' order by read_time desc");
	}
 
 
}
