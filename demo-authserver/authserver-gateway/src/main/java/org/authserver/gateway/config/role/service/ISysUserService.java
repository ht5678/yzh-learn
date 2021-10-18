package org.authserver.gateway.config.role.service;

import org.authserver.gateway.config.role.entity.SysUser;

/**
 * 
 * @author yuezh2@lenovo.com
 *	@date 2021年10月18日下午5:25:26
 */
public interface ISysUserService {

	
	
	public SysUser getByUsername(String username);
	
}
