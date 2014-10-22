package com.hod.pro.model.dao;

import java.util.List;

import com.hod.pojo.Hod2000Concentrator;

/**
 * Hod2000ConcentratorDAO
 * @author yixiang
 */
public class Hod2000ConcentratorDAO extends GenericHibernateDAO<Hod2000Concentrator, String> implements IHod2000ConcentratorDAO {

	public List findByAddress(String codeString) {
		return findByNHQL("select con_id,con_number,con_address from hod2000_concentrator where con_able=1 and con_address like '"+codeString+"%'");
	}

	public Hod2000Concentrator findByConNum(String terminalId) {
		List list=findByHQL("from Hod2000Concentrator where conNumber='"+terminalId+"'");
		if(list.size()>0)
			return (Hod2000Concentrator) list.get(0);
		else
			return null;
	}

	public List findIsonline(String con_isonline) {
		return findByHQL("from Hod2000Concentrator where conIsonline="+con_isonline + " ORDER BY conId");
	}

	public List findByConNum2(String terminalId) {
		return findByHQL("from Hod2000Concentrator where conNumber='"+terminalId+"'");
	}

	public List findByParams(String conNum, String conIsonline,
			String conFlashStatus, String conAddress) {
		String sql="select con_number,con_position_name,con_isonline,con_flash_status,con_com1_status,con_com2_status,con_com3_status,con_strong from hod2000_concentrator where con_able=1";
		if(conNum!=null&&!conNum.equals(""))
		{
			sql+=" and con_number like '"+conNum+"%'";
		}
		if(conIsonline!=null&&!conIsonline.equals(""))
		{
			sql+=" and con_isonline="+Integer.parseInt(conIsonline);
		}
		if(conFlashStatus!=null&&!conFlashStatus.equals(""))
		{
			sql+=" and con_flash_status="+Integer.parseInt(conFlashStatus);
		}
		if(conAddress!=null&&!conAddress.equals(""))
		{
			sql+=" and con_address like '"+conAddress+"%'";
		}
		return findByNHQL(sql);
	}

	public List findWarmInfo() {
		return findByNHQL("select con_number,con_position_name,con_isonline,con_flash_status,con_com1_status,con_com2_status,con_com3_status,con_strong from hod2000_concentrator where con_able=1 and (con_flash_status=1 or con_isonline=2 or con_com1_status=1 or con_com2_status=1 or con_com3_status=1 or con_strong=0)");
	}
 
}
