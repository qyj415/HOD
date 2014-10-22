package com.hod.pro.model.service;

import java.util.List;

/**
 * @author yixiang
 */
 
public interface IHod2000PermissionService extends IService {

	public abstract List findByPermCodeName(String[] str);

	/**
	 * 根据角色中的权限id集合查询权限代码
	 * @param rolePermission
	 * @return
	 */
	public abstract String findByRolePermission(String rolePermission);

	/**
	 * 根据角色中的权限，查询权限集合
	 * @param rpurview
	 * @return
	 */
	public abstract List findByRole(String rpurview);

	/**
	 * 查询角色中未拥有的权限
	 * @param rpurview
	 * @return
	 */
	public abstract List findByRoleNotIn(String rpurview);

}
