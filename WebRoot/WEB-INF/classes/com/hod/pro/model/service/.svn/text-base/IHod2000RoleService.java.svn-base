package com.hod.pro.model.service;

/**
 * @author JSmart Tools
 */
 
public interface IHod2000RoleService extends IService {

	/**
	 * 根据当前登陆的id查询权限集合
	 * @param operId
	 * @return
	 */
	public abstract String findByOperatorId(Integer operId);

	public abstract int findByRoleName(String rname);

	/**
	 * 根据权限查询当前角色的个数
	 * @param rolePermissions
	 * @return
	 */
	public abstract int findByRolePermission(String rolePermissions);

	public abstract int findByRolePermission(String rolePermissions, Integer rid);

}
