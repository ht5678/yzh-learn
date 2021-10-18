package org.authserver.gateway.config.role.mapper;

import java.util.List;

import org.authserver.gateway.config.role.entity.SysPermission;

/**
 * 
 * @author yuezh2@lenovo.com
 *	@date 2021年10月18日下午5:22:49
 */
public interface SysPermissionMapper {

	
	/**
	 * 根据资源id集合查询资源列表
	 * @param ids
	 * @return
	 */
	public List<SysPermission> findByIds(List<Integer> ids);
	
}
