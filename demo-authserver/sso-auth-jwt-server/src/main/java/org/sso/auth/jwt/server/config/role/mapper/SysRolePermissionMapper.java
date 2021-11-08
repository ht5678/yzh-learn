package org.sso.auth.jwt.server.config.role.mapper;

import java.util.List;

import org.sso.auth.jwt.server.config.role.entity.SysRolePermission;

/**
 * 
 * @author yuezh2@lenovo.com
 *	@date 2021年10月18日下午5:23:59
 */
public interface SysRolePermissionMapper {

	
	/**
	 * 根据角色id查询资源列表
	 * @param roleIds
	 * @return
	 */
	public List<SysRolePermission> findByRoleIds(List<Integer> roleIds);
	
}
