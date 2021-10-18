package org.authserver.gateway.config.role.domain;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

/**
 * 
 * @author yuezh2@lenovo.com
 *	@date 2021年10月18日下午4:43:30
 */
public class DemoUser extends User{
	
	
	
	
	

	/**
	 * 
	 */
	private static final long serialVersionUID = -6423037781312904292L;
	
	
	private Integer departmentId;//举个例子 , 部门id
	
	private String mobile ; //举个例子,  假设我们想增加字段, 这里加一个mobile字段
	
	
    public DemoUser(String username, String password, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
    }

	public DemoUser(String username, String password, boolean enabled, boolean accountNonExpired,
			boolean credentialsNonExpired, boolean accountNonLocked,
			Collection<? extends GrantedAuthority> authorities) {
		super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
		// TODO Auto-generated constructor stub
	}

	
	
	public Integer getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(Integer departmentId) {
		this.departmentId = departmentId;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	
}
