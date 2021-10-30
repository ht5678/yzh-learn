package org.sso.auth.code.server.config.role.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.sso.auth.code.server.config.role.entity.SysUser;
import org.sso.auth.code.server.config.role.mapper.SysUserMapper;
import org.sso.auth.code.server.config.role.service.ISysUserService;



/**
 * 
 * @author yuezh2@lenovo.com
 *	@date 2021年10月18日下午5:27:54
 */
@Component
public class SysUserServiceImpl implements ISysUserService {
	
	@Autowired
	private SysUserMapper sysUserMapper;
	

	@Override
	public SysUser getByUsername(String username) {
		return sysUserMapper.findByUserName(username);
	}

}
