package com.hod.pro.model.service;

import java.util.List;

import com.hod.pojo.Hod2000Building;
import com.hod.pojo.Hod2000Community;

/**
 * @author yixiang
 */
 
public interface IHod2000BuildingService extends IService {

	public abstract List findByLngLat(String lng1, String lng2, String lat1, String lat2);

	public abstract List findByCommunityId(int id);

	/**
	 * 查询该楼栋下的表计是否有异常
	 * @param buildingId 楼栋编号
	 * @return
	 */
	public abstract List findByBuildingId(int buildingId);

	public abstract int findByBuildingName(Integer communityId,
			String buildingName);

	public abstract int findByBuildingName(Integer communityId,
			String buildingName, Integer buildingId);

	public abstract List getLnglat(String conAddress);

	public abstract List findConEventByCode(String buildingCode);

	/**
	 * 得到楼栋的行政编码
	 * @return
	 */
	public abstract String getCode(int communityId);

	/**
	 * 查询所有楼栋信息
	 * @param community
	 * @return
	 */
	public abstract List<Hod2000Building> findAll(List<Hod2000Community> community);

}
