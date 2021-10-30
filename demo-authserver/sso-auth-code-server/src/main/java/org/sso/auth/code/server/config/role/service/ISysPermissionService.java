package org.sso.auth.code.server.config.role.service;

import java.util.List;

import org.sso.auth.code.server.config.role.entity.SysPermission;

/**
 * 
 * @author yuezh2@lenovo.com
 *	@date 2021年10月18日下午5:26:19
 */
public interface ISysPermissionService {

	
	
	public List<SysPermission> findByUserId(Integer userId);
	
}
