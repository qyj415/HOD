package com.hod.pro.model.service;

import java.util.List;
import com.hod.pro.model.dao.IHod2000ClientDAO;

/**
 * @author yixiang
 */
 
public class Hod2000ClientService extends Service implements IHod2000ClientService {

	private IHod2000ClientDAO hod2000ClientDAO;
	
	public void setHod2000ClientDAO(IHod2000ClientDAO hod2000ClientDAO) {
		this.hod2000ClientDAO = hod2000ClientDAO;
	}

	public List findByClientId(int clientId) {
		return hod2000ClientDAO.findByClientId(clientId);
	}

	public List findReceiveFlagByClientId(int clientId) {
		return hod2000ClientDAO.findReceiveFlagByClientId(clientId);
	}

	public int findByCardNo(String clientIdentity) {
		return Integer.parseInt(hod2000ClientDAO.findByNHQL("select count(*) from hod2000_client where client_card_number='"+clientIdentity+"' and client_enable=1").get(0).toString());
	}

	public int findCount() {
		return Integer.parseInt(hod2000ClientDAO.findByNHQL("select count(*) from hod2000_client where client_enable=1").get(0).toString());
	}

	public List findRoomInfoByClientId(int clientId) {
		return genericDAO.findByNHQL("select address,room_size from client_address where client_id="+clientId);
	}
 

}
