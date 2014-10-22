package com.hod.pro.model.service;

import java.util.List;


/**
 * @author yixiang
 */
 
public interface IHod2000VillageService extends IService {

	public abstract List findByCountyId(int id);

	public abstract List findByVillageCode(String code);

}
