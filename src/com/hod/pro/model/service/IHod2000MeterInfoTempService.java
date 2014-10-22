package com.hod.pro.model.service;

import java.util.List;

/**
 * @author yixiang
 */
 
public interface IHod2000MeterInfoTempService extends IService {

	public abstract List findByMeterName(String string);

	public abstract List getDataByIds(String ids);

}
