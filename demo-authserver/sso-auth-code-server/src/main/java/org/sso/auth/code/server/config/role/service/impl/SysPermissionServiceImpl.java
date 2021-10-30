package org.sso.auth.code.server.config.role.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.sso.auth.code.server.config.role.entity.SysPermission;
import org.sso.auth.code.server.config.role.entity.SysRolePermission;
import org.sso.auth.code.server.config.role.entity.SysUserRole;
import org.sso.auth.code.server.config.role.mapper.SysPermissionMapper;
import org.sso.auth.code.server.config.role.mapper.SysRolePermissionMapper;
import org.sso.auth.code.server.config.role.mapper.SysUserRoleMapper;
import org.sso.auth.code.server.config.role.service.ISysPermissionService;



/**
 * 
 * @author yuezh2@lenovo.com
 *	@date 2021年10月19日上午11:26:14
 */
@Component
public class SysPermissionServiceImpl implements ISysPermissionService {
	
	
	private static final Logger LOGGER = LoggerFactory.getLogger(SysPermissionServiceImpl.class);
	
	@Qualifier("sysRolePermissionMapper")
	@Autowired
	private SysRolePermissionMapper sysRolePermissionMapper;
	
	@Autowired
	private SysUserRoleMapper sysUserRoleMapper;
	
	@Autowired
	private SysPermissionMapper sysPermissionMapper;
	
	
	

	@Override
	public List<SysPermission> findByUserId(Integer userId) {
		//根据用户id查询用户角色映射列表
		List<SysUserRole> sysUserRoleList = sysUserRoleMapper.findByUserId(userId);
		if(CollectionUtils.isEmpty(sysUserRoleList)) {
			LOGGER.warn("根据用户id:{}查询用户角色为空" , userId);
			return null;
		}
		
		
		//迭代循环获取role ids
		List<Integer> roleIds = new ArrayList<>();
		for(SysUserRole sysUserRole : sysUserRoleList) {
			roleIds.add(sysUserRole.getRoleId());
		}
		
		//查询角色  资源关联集合
		List<SysRolePermission> sysRolePermissionList = sysRolePermissionMapper.findByRoleIds(roleIds);
		if(CollectionUtils.isEmpty(sysRolePermissionList)) {
			LOGGER.warn("根据role ids : {} 查询SysRolePermission集合为空" , roleIds);
			return null;
		}
		
		//迭代permission id加入集合
		List<Integer> permissionIds = new ArrayList<>();
		for(SysRolePermission sysRolePermission : sysRolePermissionList) {
			permissionIds.add(sysRolePermission.getPermissionId());
		}
		
		//查询用户的所有资源
		List<SysPermission> sysPermissionList = sysPermissionMapper.findByIds(permissionIds);
		if(CollectionUtils.isEmpty(sysRolePermissionList)) {
			LOGGER.warn("根据permissionIds:{} 查询SysPermission为空" , permissionIds);
		}
		
		return sysPermissionList;
	}

}
