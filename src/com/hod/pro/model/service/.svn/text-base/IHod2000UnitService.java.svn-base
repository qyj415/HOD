package com.hod.pro.model.service;

import java.util.List;

import com.hod.pojo.Hod2000Building;
import com.hod.pojo.Hod2000Unit;

/**
 * @author JSmart Tools
 */
 
public interface IHod2000UnitService extends IService {

	public abstract List findByBuildingId(int id);

	public abstract int findByUnitName(Integer buildingId, String unitName,
			Integer unitId);

	public abstract int findByRoomName(Integer buildingId, String unitName);

	public abstract List findByUnitId(int id);

	/**
	 * 得到单元的行政区域代码
	 * @return
	 */
	public abstract String getCode(int buildingId);

	/**
	 * 查询所有单元信息
	 * @param hod2000Building
	 * @return
	 */
	public abstract List<Hod2000Unit> findAll(List<Hod2000Building> hod2000Building);

}
