package com.hod.pro.model.service;

import java.util.List;

import com.hod.pro.model.dao.IHod2000OperatorDAO;


/**
 * @author JSmart Tools
 */
 
public class Hod2000OperatorService extends Service implements IHod2000OperatorService {

	private IHod2000OperatorDAO hod2000OperatorDAO;
	
	public void setHod2000OperatorDAO(IHod2000OperatorDAO hod2000OperatorDAO) {
		this.hod2000OperatorDAO = hod2000OperatorDAO;
	}

	public List findByLoginName(String operLoginName) {
		return hod2000OperatorDAO.findByLoginName(operLoginName);
	}

	public int findByRoleId(int roleId) {
		return hod2000OperatorDAO.findByRoleId(roleId);
	}
 

}
