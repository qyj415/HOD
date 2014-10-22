package com.hod.pro.model.service;

import java.util.List;

import com.hod.pojo.Hod2000Community;

/**
 * @author yixiang
 */
 
public interface IHod2000CommunityService extends IService {

	public abstract List findByVillageId(int id);

	public abstract int findByCommunityName(Integer villageId,
			String communityName);

	public abstract int findByCommunityName(Integer villageId,
			String communityName, Integer communityId);

	/**
	 * 得到小区的行政编码
	 * @return
	 */
	public abstract String getCode(int villageId);

	public abstract List<Hod2000Community> findCommunityVillageId(int villageId);

}
