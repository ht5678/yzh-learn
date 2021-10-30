package org.sso.auth.code.server.config;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.sso.auth.code.server.config.role.domain.DemoUser;
import org.sso.auth.code.server.config.role.entity.SysPermission;
import org.sso.auth.code.server.config.role.entity.SysUser;
import org.sso.auth.code.server.config.role.mapper.SysUserMapper;
import org.sso.auth.code.server.config.role.service.ISysPermissionService;

import com.alibaba.fastjson.JSON;

/**
 * 
 * @author yue
 *
 */
@Component
public class DemoUserDetailService implements UserDetailsService{

	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private SysUserMapper sysUserMapper;
	
	@Qualifier("sysPermissionServiceImpl")
	@Autowired
	private ISysPermissionService sysPermissionService;
	
	
	private static final Logger LOGGER = LoggerFactory.getLogger(DemoUserDetailService.class);
	
	
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		SysUser sysUser = sysUserMapper.findByUserName(username);
		
		if(null == sysUser) {
			LOGGER.warn("根据用户名:{}查询用户信息为空" , username);
			throw new UsernameNotFoundException(username);
		}
		
		List<SysPermission> sysPermissionList = sysPermissionService.findByUserId(sysUser.getId());
		
		List<SimpleGrantedAuthority> authorityList = new ArrayList<SimpleGrantedAuthority>();
		if(!CollectionUtils.isEmpty(sysPermissionList)) {
			for(SysPermission sysPermission : sysPermissionList) {
				authorityList.add(new SimpleGrantedAuthority(sysPermission.getUri()));
			}
		}
			
		DemoUser demoUser = new DemoUser(sysUser.getUsername(), passwordEncoder.encode(sysUser.getPassword()), authorityList);
		LOGGER.info("用户登录成功: {}" , JSON.toJSONString(demoUser));
		
		return demoUser;
	}

	
	
}

