package com.hod.pro.model.dao;

import java.util.List;
import java.util.Map;

import com.hod.pojo.Hod2000Meter;

/**
 * Hod2000MeterDAO
 * @author yixiang
 */
public interface IHod2000MeterDAO  extends GenericDAO<Hod2000Meter,String> {

	public abstract List findByMeterParent(String meterPosition,int meterAble);

	public abstract List findByConId(int id);

	public abstract List findbyMeterName(String meterName);

	public abstract String getParentName(String meterParentCode);

	public abstract int getTerminalCount(Integer conId);

	public abstract int getMeterIndex();

	public abstract List findByParams(String meterName, String valveStatus,
			String batteryStatus, String isOnline,String meterPosition);

	public abstract List findByRoomId(int roomId,String hpStartTime,String hpEndTime);

	public abstract int findByRoomIdAndMeterAble(int roomId, int meterAble);

	public abstract List findbyUserMeterName();

	public abstract List findbyMeterNames(String meterNames, String code);

	public abstract List findByCaliber(String caliber);
}
