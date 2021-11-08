package org.sso.auth.jwt.server.config.role.service;

import org.sso.auth.jwt.server.config.role.entity.SysUser;

/**
 * 
 * @author yuezh2@lenovo.com
 *	@date 2021年10月18日下午5:25:26
 */
public interface ISysUserService {

	
	
	public SysUser getByUsername(String username);
	
}
