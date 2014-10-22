package com.hod.pro.model.service;

import java.util.List;

import com.hod.pro.model.dao.IHod2000MeterCallDownInfoDao;


public class Hod2000MeterCallDownInfoService extends Service implements IHod2000MeterCallDownInfoService{

	private IHod2000MeterCallDownInfoDao hod2000MeterCallDownInfoDao;
	
	public void setHod2000MeterCallDownInfoDao(
			IHod2000MeterCallDownInfoDao hod2000MeterCallDownInfoDao) {
		this.hod2000MeterCallDownInfoDao = hod2000MeterCallDownInfoDao;
	}

	public List findByHQL(String HQL) {
		return hod2000MeterCallDownInfoDao.findByHQL(HQL);
	}
	
}
