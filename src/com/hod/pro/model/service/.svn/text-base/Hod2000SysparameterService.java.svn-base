package com.hod.pro.model.service;

import java.util.List;

import com.hod.pro.model.dao.IHod2000SysparameterDAO;

/**
 * @author yixiang
 */
 
public class Hod2000SysparameterService extends Service implements IHod2000SysparameterService {

	private IHod2000SysparameterDAO hod2000SysparameterDAO;
	
	public void setHod2000SysparameterDAO(
			IHod2000SysparameterDAO hod2000SysparameterDAO) {
		this.hod2000SysparameterDAO = hod2000SysparameterDAO;
	}

	public List findByType(int type) {
		return hod2000SysparameterDAO.findByType(type);
	}
 

}
