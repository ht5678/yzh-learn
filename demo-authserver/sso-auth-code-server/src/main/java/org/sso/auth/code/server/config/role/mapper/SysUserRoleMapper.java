package org.sso.auth.code.server.config.role.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Select;
import org.sso.auth.code.server.config.role.entity.SysUserRole;

/**
 * 
 * @author yuezh2@lenovo.com
 *	@date 2021年10月18日下午5:21:20
 */
public interface SysUserRoleMapper {

	
	/**
	 * 根据用户id查询 , 用户角色列表
	 * @param userId
	 * @return
	 */
	@Select("select * from sys_user_role where user_id=#{userId}")
	public List<SysUserRole> findByUserId(Integer userId);
	
}
