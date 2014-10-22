package com.hod.pro.model.dao;

import java.util.List;

import com.hod.pojo.Hod2000MeterType;

/**
 * Hod2000MeterTypeDAO
 * @author yixiang
 */
public class Hod2000MeterTypeDAO extends GenericHibernateDAO<Hod2000MeterType, String> implements IHod2000MeterTypeDAO {

	public Hod2000MeterType findByTypeCode(String meterType) {
		List list=findByHQL("from Hod2000MeterType where typeCode='"+meterType+"'");
		if(list.size()>0)
			return (Hod2000MeterType) list .get(0);
		else
			return null;
	}
 
}
