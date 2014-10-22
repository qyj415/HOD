package com.hod.pro.model.dao;

import java.util.Date;
import java.util.List;

import com.hod.pojo.Hod2000Price;
import com.hod.pojo.Hod2000PriceDetail;
import com.hod.util.Utils;

/**
 * Hod2000PriceDAO
 * @author yixiang
 */
public class Hod2000PriceDAO extends GenericHibernateDAO<Hod2000Price, String> implements IHod2000PriceDAO {

	public List findByPriceName(String pname) {
		return findByNHQL("select p_name from hod2000_price where p_name='"+pname+"'");
	}

	public Hod2000Price findByParams(int priceType, int priceStatus) {
		List list=findByHQL("from Hod2000Price where p_type="+priceType+" and p_status="+priceStatus);
		if(list.size()>0)
			return (Hod2000Price) list.get(0);
		else
			return null;
	}

	public List<Hod2000PriceDetail> getPriceDetailsById(int pid) {
		return findByHQL("from Hod2000PriceDetail where hod2000Price.pid="+pid+" order by pdPower");
	}

	public List findByPriceName(String pname, Integer pid) {
		return findByNHQL("select p_name from hod2000_price where p_id!="+pid+" and p_name='"+pname+"'");
	}

	public List findByParams(int priceType, Date pstartTime) {
		return findByNHQL("select * from hod2000_price where p_status=2 and p_type="+priceType+" and p_startDate='"+Utils.dateToStr(pstartTime)+"'");
	}

	public List findByParams(int ptype, Date pstartTime, int pid) {
		return findByNHQL("select * from hod2000_price where p_status=2 and p_type="+ptype+" and p_startDate='"+Utils.dateToStr(pstartTime)+"' and p_id!="+pid);
	}
 
}
