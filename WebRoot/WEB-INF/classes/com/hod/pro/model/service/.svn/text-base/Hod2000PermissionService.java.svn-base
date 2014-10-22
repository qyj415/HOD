package com.hod.pro.model.service;

import java.util.List;
import com.hod.pro.model.dao.IHod2000PermissionDAO;

/**
 * @author yixiang
 */
 
public class Hod2000PermissionService extends Service implements IHod2000PermissionService {

	private IHod2000PermissionDAO hod2000PermissionDAO;
	
	public void setHod2000PermissionDAO(IHod2000PermissionDAO hod2000PermissionDAO) {
		this.hod2000PermissionDAO = hod2000PermissionDAO;
	}

	public List findByPermCodeName(String[] str) {
		return hod2000PermissionDAO.findByPermCodeName(str);
	}

	public String findByRolePermission(String rolePermission) {
		List list=genericDAO.findByNHQL("select perm_code_name from hod2000_permission where perm_id in ("+rolePermission+")");
		String permissions="";
		for (int i = 0; i < list.size(); i++) {
			permissions+=list.get(i).toString()+",";
		}
		if(permissions.length()>0)
			permissions=permissions.substring(0,permissions.length()-1);
		return permissions;
	}

	public List findByRole(String rpurview) {
		return genericDAO.findByHQL("from Hod2000Permission where permId in ("+rpurview+")");
	}

	public List findByRoleNotIn(String rpurview) {
		return genericDAO.findByHQL("from Hod2000Permission where permId not in ("+rpurview+")");
	}
 

}
