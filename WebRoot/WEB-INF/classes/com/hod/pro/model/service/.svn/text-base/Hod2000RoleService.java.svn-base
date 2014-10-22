package com.hod.pro.model.service;

/**
 * @author JSmart Tools
 */
 
public class Hod2000RoleService extends Service implements IHod2000RoleService {

	public String findByOperatorId(Integer operId) {
		return genericDAO.findByNHQL("select r_purview from hod2000_role roles inner join hod2000_operator operator on roles.r_id=operator.r_id where operator.oper_id="+operId).get(0).toString();
	}

	public int findByRoleName(String rname) {
		return Integer.parseInt(genericDAO.findByNHQL("select count(*) from hod2000_role where r_name='"+rname+"'").get(0).toString());
	}

	public int findByRolePermission(String rolePermissions) {
		return Integer.parseInt(genericDAO.findByNHQL("select count(*) from hod2000_role where r_purview='"+rolePermissions+"'").get(0).toString());
	}

	public int findByRolePermission(String rolePermissions, Integer rid) {
		return Integer.parseInt(genericDAO.findByNHQL("select count(*) from hod2000_role where r_purview='"+rolePermissions+"' and r_id!="+rid).get(0).toString());
	}
 

}
