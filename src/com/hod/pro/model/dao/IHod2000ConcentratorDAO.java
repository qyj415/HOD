package com.hod.pro.model.dao;

import java.util.List;

import com.hod.pojo.Hod2000Concentrator;

/**
 * Hod2000ConcentratorDAO
 * @author yixiang
 */
public interface IHod2000ConcentratorDAO  extends GenericDAO<Hod2000Concentrator,String> {

	public abstract List findByAddress(String codeString);

	public abstract Hod2000Concentrator findByConNum(String terminalId);
	
	public abstract List<String> findIsonline(String terminalId);

	public abstract List findByConNum2(String terminalId);

	public abstract List findByParams(String conNum, String conIsonline,
			String conFlashStatus, String conAddress);

	public abstract List findWarmInfo();

}
