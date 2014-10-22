package com.hod.pro.model.service;

import java.util.Date;

import com.hod.message.ConstantValue;
import com.hod.pojo.Hod2000ParaDownInfoCache;
import com.hod.pro.model.dao.IHod2000ParaDownInfoCacheDAO;
import com.hod.util.NetworkTimeUtil;


/**
 * @author JSmart Tools
 */
 
public class Hod2000ParaDownInfoCacheService extends Service implements IHod2000ParaDownInfoCacheService {

	private IHod2000ParaDownInfoCacheDAO cacheDAO;
	
	public void setCacheDAO(IHod2000ParaDownInfoCacheDAO cacheDAO) {
		this.cacheDAO = cacheDAO;
	}
	
	/**
	 * �����·�
	 * conNum:���������
	 * content:��������
	 * mapping:���ݱ�ʶ
	 */
	public int controlIssued(String conNum,String content, String mapping) {
		int flag=0;//�·���־
		try {
			Hod2000ParaDownInfoCache cache=new Hod2000ParaDownInfoCache();
			cache.setConNum(conNum);
			cache.setProtocolType(ConstantValue.mapping);
			cache.setSendFlag(-1);
			cache.setState(0);
			//cache.setSubmitTime(new Date());
			cache.setSubmitTime(NetworkTimeUtil.getDate());
			cache.setSendContent(content);
			cacheDAO.save(cache);
			long start_time = System.currentTimeMillis();
			while (true) {
				long end_time = System.currentTimeMillis();
				cache=(Hod2000ParaDownInfoCache) cacheDAO.findById(cache.getDownId());
				if(1==cache.getState())//�ɹ�
				{
					flag=1;
					break;
				}
				if(2==cache.getState())//ʧ��
				{
					flag=2;
					break;
				}
				if (end_time - start_time >= ConstantValue.timeout) { 
					cache=(Hod2000ParaDownInfoCache) cacheDAO.findById(cache.getDownId());
					if(0==cache.getState())//��ʱ
					{
						flag=0;
					}
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			flag=2;
		}
		return flag;
	}
 

}
