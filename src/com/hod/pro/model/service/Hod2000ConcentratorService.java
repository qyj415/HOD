package com.hod.pro.model.service;

import java.util.List;
import com.hod.message.ConstantValue;
import com.hod.message.MessageBase;
import com.hod.message.Util;
import com.hod.pojo.Hod2000Concentrator;
import com.hod.pojo.Hod2000ParaDownInfoCache;
import com.hod.pro.model.dao.IHod2000ConcentratorDAO;
import com.hod.pro.model.dao.IHod2000ParaDownInfoCacheDAO;
import com.hod.util.NetworkTimeUtil;

/**
 * @author yixiang
 */
 
public class Hod2000ConcentratorService extends Service implements IHod2000ConcentratorService {

	private IHod2000ConcentratorDAO concentratorDAO;
	private IHod2000ParaDownInfoCacheDAO cacheDAO;
	

	public void setConcentratorDAO(IHod2000ConcentratorDAO concentratorDAO) {
		this.concentratorDAO = concentratorDAO;
	}

	public void setCacheDAO(IHod2000ParaDownInfoCacheDAO cacheDAO) {
		this.cacheDAO = cacheDAO;
	}

	public List findByAddress(String codeString) {
		return concentratorDAO.findByAddress(codeString);
	}


	public Hod2000Concentrator findByConNum(String terminalId) {
		return concentratorDAO.findByConNum(terminalId);
	}

	public List<String> findIsonline(String terminalId) {
		return concentratorDAO.findIsonline(terminalId);
	}


	public void updateBySql(String Sql) {
		concentratorDAO.updateByHql(Sql);
	}


	public List findByBuildingCode(String buildingCode) {
		String sql="select distinct conNumber,conPositionName from ConEventMap where conAddress like concat(regionCode,cityCode,countyCode,villageCode,communityCode,'"+buildingCode+"%')";
		return genericDAO.findByHQL(sql);
		//return genericDAO.findByNHQL("select distinct con_number,con_position_name from address_con where con_address like region_code+city_code+county_code+village_code+community_code+'"+buildingCode+"%'");
	}


	public int initParams(String conNum, String data,
			String identificationId) {
		int flag=0;
		try {
			MessageBase message=new MessageBase(ConstantValue.conType,Util.getAddr(conNum,ConstantValue.procuNum),1);
			Hod2000ParaDownInfoCache cache=new Hod2000ParaDownInfoCache();
			cache.setConNum(conNum);
			cache.setProtocolType(identificationId);
			cache.setSendFlag(-1);
			cache.setState(0);
			//cache.setSubmitTime(new Date());
			cache.setSubmitTime(NetworkTimeUtil.getDate());
			cache.setSendContent(Util.bytesToHexString(message.parseDownInfoToBytes(Util.getData(identificationId,data,ConstantValue.SEQ))));
			cacheDAO.save(cache);
			long start_time = System.currentTimeMillis();
			while (true) {
				long end_time = System.currentTimeMillis();
				cache=cacheDAO.findById(cache.getDownId());
				if(1==cache.getState())//³É¹¦
				{
					flag=1;
					break;
				}
				if(2==cache.getState())//Ê§°Ü
				{
					flag=2;
					break;
				}
				if (end_time - start_time >= ConstantValue.timeout) { 
					cache=cacheDAO.findById(cache.getDownId());
					if(0==cache.getState())//³¬Ê±
					{
						flag=0;
					}
					break;
				}
			}
		} catch (Exception e) {
			flag=2;
			e.printStackTrace();
		}
		return flag;
	}

	public List findByConNum2(String terminalId) {
		return concentratorDAO.findByConNum2(terminalId);
	}

	public List findByParams(String conNum, String conIsonline,
			 String conFlashStatus, String conAddress) {
		return concentratorDAO.findByParams(conNum,conIsonline,conFlashStatus,conAddress);
	}

	public List findWarmInfo() {
		return concentratorDAO.findWarmInfo();
	}

	public Hod2000Concentrator findByConNums(String conNum) {
		List list=findByHQL("from Hod2000Concentrator where conNumber like '"+conNum+"%'");
		if(list.size()>0)
			return (Hod2000Concentrator) list.get(0);
		else
			return null;
	}


}
