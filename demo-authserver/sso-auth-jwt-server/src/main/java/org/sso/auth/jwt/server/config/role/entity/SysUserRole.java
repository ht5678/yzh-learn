package org.sso.auth.jwt.server.config.role.entity;

import java.io.Serializable;

/**
 * 
 * @author yuezh2@lenovo.com
 *	@date 2021年10月18日下午5:17:39
 */
public class SysUserRole implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4059398340496953042L;

	
	private Integer id;
	
	private Integer userId;
	
	private Integer roleId;
	
	
	

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}
	
	
}
