package com.hod.pro.model.service;

import java.util.List;

import com.hod.pojo.Hod2000Concentrator;

/**
 * @author yixiang
 */
 
public interface IHod2000ConcentratorService extends IService {

	public abstract List findByAddress(String codeString);

	public abstract Hod2000Concentrator findByConNum(String terminalId);
	
	public abstract List findByConNum2(String terminalId);
	
	public abstract List<String> findIsonline(String terminalId);
	
	public abstract void updateBySql(String sql);

	public abstract List findByBuildingCode(String buildingCode);

	public abstract int initParams(String conNum, String data,
			String identificationId);

	public abstract List findByParams(String conNum, String conIsonline,
			 String conFlashStatus, String conAddress);

	public abstract List findWarmInfo();

	public abstract Hod2000Concentrator findByConNums(String conNum);

}
