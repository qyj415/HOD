package com.hod.pro.model.dao;

import java.util.List;

import com.hod.pojo.Hod2000City;

/**
 * Hod2000CityDAO
 * @author yixiang
 */
public interface IHod2000CityDAO  extends GenericDAO<Hod2000City,String> {

	public abstract List findByRegionId(int id);

}
