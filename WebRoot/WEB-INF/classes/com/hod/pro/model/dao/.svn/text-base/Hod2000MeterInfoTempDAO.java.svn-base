package com.hod.pro.model.dao;

import java.util.List;

import com.hod.pojo.Hod2000MeterInfoTemp;

/**
 * Hod2000MeterInfoTempDAO
 * 
 * @author yixiang
 */
public class Hod2000MeterInfoTempDAO extends
		GenericHibernateDAO<Hod2000MeterInfoTemp, String> implements
		IHod2000MeterInfoTempDAO {

	public List findByMeterName(String meterName) {
		return findByHQL("from Hod2000MeterInfoTemp where meterName='"+meterName+"' order by read_time desc");
	}

}
