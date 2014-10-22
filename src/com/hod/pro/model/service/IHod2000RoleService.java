package com.hod.pro.model.service;

/**
 * @author JSmart Tools
 */
 
public interface IHod2000RoleService extends IService {

	/**
	 * ���ݵ�ǰ��½��id��ѯȨ�޼���
	 * @param operId
	 * @return
	 */
	public abstract String findByOperatorId(Integer operId);

	public abstract int findByRoleName(String rname);

	/**
	 * ����Ȩ�޲�ѯ��ǰ��ɫ�ĸ���
	 * @param rolePermissions
	 * @return
	 */
	public abstract int findByRolePermission(String rolePermissions);

	public abstract int findByRolePermission(String rolePermissions, Integer rid);

}
