package com.hod.pro.model.service;

import java.util.List;

/**
 * @author yixiang
 */
 
public interface IHod2000CityService extends IService {

	public abstract List findByRegionId(int id);

	public abstract List findByCityCode(String code);

}
