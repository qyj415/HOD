package com.hod.pro.model.dao;

import java.util.List;
import java.util.Map;

import com.hod.pojo.Hod2000MeterInfoHistory;

/**
 * Hod2000MeterInfoHistoryDAO
 * @author yixiang
 */
public interface IHod2000MeterInfoHistoryDAO  extends GenericDAO<Hod2000MeterInfoHistory,String> {

	public abstract List findMeterWarm();

	public abstract List findByMeterName(String meterName, String months);

	public abstract List findEnergyMeterName(String meterName,String hpStartTime);

	public abstract List findEnergyMeterName2(String meterName,
			String hpEndTime);

	public abstract double findEnergyMeterNameFirst(String meterName,
			String hpStartTime,String hpEndTime);

	public abstract double findEnergyMeterNameEnd(String meterName,
			String hpStartTime,String hpEndTime);

}
