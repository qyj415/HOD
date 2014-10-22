package com.hod.pro.model.service;

import java.util.List;

/**
 * @author gngyf15
 */
 
public interface IHod2000ClientService extends IService {

	public abstract List findByClientId(int clientId);

	public abstract List findReceiveFlagByClientId(int clientId);

	/**
	 * 查询该证件号码存在的有效注册次数
	 * @param clientIdentity
	 * @return
	 */
	public abstract int findByCardNo(String clientIdentity);

	/**
	 * 当前有效用户的个数
	 * @return
	 */
	public abstract int findCount();

	/**
	 * 根据用户id查询房间信息
	 * @param clientId
	 * @return
	 */
	public abstract List findRoomInfoByClientId(int clientId);

}
