package com.hod.pro.model.dao;

import java.util.List;
import com.hod.pojo.Hod2000Client;

/**
 * Hod2000ClientDAO
 * @author yixiang
 */
public class Hod2000ClientDAO extends GenericHibernateDAO<Hod2000Client, String> implements IHod2000ClientDAO {

	/**
	 * 检测用户是否已预付款
	 */
	public List findByClientId(int clientId) {
		return findByNHQL("select client_id from hod2000_client where client_id="+clientId+" and client_pre_flag=1");
	}

	/**
	 * 检测用户是否已付费
	 */
	public List findReceiveFlagByClientId(int clientId) {
		return findByNHQL("select client_id from hod2000_client where client_id="+clientId+" and client_receive_flag=1");
	}
 
}
