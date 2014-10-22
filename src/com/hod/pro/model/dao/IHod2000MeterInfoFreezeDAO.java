package com.hod.pro.model.dao;

import java.util.Date;
import java.util.List;

import com.hod.pojo.Hod2000MeterInfoFreeze;

/**
 * Hod2000MeterInfoFreezeDAO
 * @author gngyf15
 */
public interface IHod2000MeterInfoFreezeDAO  extends GenericDAO<Hod2000MeterInfoFreeze,String> {

	public abstract double getStartPower(String meterName, Date hpStart);

	public abstract double getEndPower(String meterName, Date hpEnd);

	/**
	 * 供暖结束累计热量，供暖季之内月冻结的最后一条记录
	 * @param meterName
	 * @param hpStartTime
	 * @param hpEndTime
	 * @return
	 */
	public abstract double findEnd(String meterName, String hpStartTime,
			String hpEndTime);

	/**
	 * 供暖开始累计热量，供暖季内月冻结数据的第一条记录
	 * @param meterName
	 * @param hpStartTime
	 * @param hpEndTime
	 * @return
	 */
	public abstract double findFirst(String meterName, String hpStartTime,
			String hpEndTime);

	/**
	 * 上季度月冻结数据，按抄表时间降序排序
	 * @param meterName
	 * @param hpStartTime
	 * @return
	 */
	public abstract List findLastEnergy(String meterName, String hpStartTime);
}
