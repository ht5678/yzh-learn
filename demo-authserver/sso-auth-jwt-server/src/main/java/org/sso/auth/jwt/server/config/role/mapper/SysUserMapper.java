package org.sso.auth.jwt.server.config.role.mapper;

import org.sso.auth.jwt.server.config.role.entity.SysUser;

/**
 * 
 * @author yuezh2@lenovo.com
 *	@date 2021年10月18日下午5:20:18
 */
public interface SysUserMapper {

	
	public SysUser findByUserName(String userName);
	
}
