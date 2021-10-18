package org.authserver.gateway.config.role.entity;

import java.io.Serializable;

/**
 * 
 * @author yuezh2@lenovo.com
 *	@date 2021年10月18日下午5:18:43
 */
public class SysRolePermission implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7787421543031766254L;

	
	private Integer id;
	
	private Integer roleId;
	
	private Integer permissionId;

	
	
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	public Integer getPermissionId() {
		return permissionId;
	}

	public void setPermissionId(Integer permissionId) {
		this.permissionId = permissionId;
	}
	
	
	
}
