package com.hod.pro.model.dao;

import java.util.List;

import com.hod.pojo.Hod2000Operator;

/**
 * Hod2000OperatorDAO
 * @author yixiang
 */
public class Hod2000OperatorDAO extends GenericHibernateDAO<Hod2000Operator, String> implements IHod2000OperatorDAO {

	public List findByLoginName(String operLoginName) {
		List list= findByNHQL("select oper_id,login_name,oper_password,oper_enable from hod2000_operator where login_name='"+operLoginName+"'");
		return list;
	}

	public int findByRoleId(int roleId) {
		return Integer.parseInt(findByNHQL("select count(*) from hod2000_operator where r_id="+roleId).get(0).toString());
	}
 
}
