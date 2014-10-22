package com.hod.pro.model.service;

import java.util.Date;
import java.util.List;

import com.hod.pojo.Hod2000Price;
import com.hod.pro.model.dao.IHod2000PriceDAO;
/**
 * @author yixiang
 */
 
public class Hod2000PriceService extends Service implements IHod2000PriceService {

	private IHod2000PriceDAO hod2000PriceDAO;
	
	public void setHod2000PriceDAO(IHod2000PriceDAO hod2000PriceDAO) {
		this.hod2000PriceDAO = hod2000PriceDAO;
	}

	public List findByPriceName(String pname) {
		return hod2000PriceDAO.findByPriceName(pname);
	}

	public List findByPriceName(String pname, Integer pid) {
		return hod2000PriceDAO.findByPriceName(pname,pid);
	}

	public List findByParams(int priceType, Date pstartTime) {
		return hod2000PriceDAO.findByParams(priceType,pstartTime);
	}

	public List findByParams(int ptype, Date pstartTime, int pid) {
		return hod2000PriceDAO.findByParams(ptype,pstartTime,pid);
	}

	public Hod2000Price findByParams(int priceType, int priceStatus) {
		return hod2000PriceDAO.findByParams(priceType, priceStatus);
	}
}
