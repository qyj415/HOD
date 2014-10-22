package com.hod.pro.model.dao;

import com.hod.pojo.Hod2000MeterType;

/**
 * Hod2000MeterTypeDAO
 * @author yixiang
 */
public interface IHod2000MeterTypeDAO  extends GenericDAO<Hod2000MeterType,String> {

	public abstract Hod2000MeterType findByTypeCode(String meterType);

}
