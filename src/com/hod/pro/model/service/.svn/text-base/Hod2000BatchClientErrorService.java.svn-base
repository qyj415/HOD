package com.hod.pro.model.service;

import com.hod.pro.model.dao.IHod2000BatchClientErrorDAO;

/**
 * @author JSmart Tools
 */
 
public class Hod2000BatchClientErrorService extends Service implements IHod2000BatchClientErrorService {
	
	private IHod2000BatchClientErrorDAO hod2000BatchClientErrorDAO;

	public IHod2000BatchClientErrorDAO getHod2000BatchClientErrorDAO() {
		return hod2000BatchClientErrorDAO;
	}

	public void setHod2000BatchClientErrorDAO(
			IHod2000BatchClientErrorDAO hod2000BatchClientErrorDAO) {
		this.hod2000BatchClientErrorDAO = hod2000BatchClientErrorDAO;
	}
	
	public int findByCardNo(String clientIdentity) {
		return Integer.parseInt(hod2000BatchClientErrorDAO.findByNHQL("select count(*) from hod2000_batchClientError where client_card_number='"+clientIdentity+"'").get(0).toString());
	}

}
