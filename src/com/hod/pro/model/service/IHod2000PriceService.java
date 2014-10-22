package com.hod.pro.model.service;

import java.util.Date;
import java.util.List;

import com.hod.pojo.Hod2000Price;

/**
 * @author JSmart Tools
 */
 
public interface IHod2000PriceService extends IService {

	public abstract List findByPriceName(String pname);

	public abstract List findByPriceName(String pname, Integer pid);

	public abstract List findByParams(int priceType, Date pstartTime);

	public abstract List findByParams(int ptype, Date pstartTime,
			int pid);

	public abstract Hod2000Price findByParams(int priceType, int priceStatus);

}
