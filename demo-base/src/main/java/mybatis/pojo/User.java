package mybatis.pojo;


/**
 * 
 * @author yuezh2
 *
 * @date 2019年9月23日 下午6:13:32  
 *
 */
public class User {

	private Long id;
	
	private String username;
	
	private Integer age;
	
	private Long phone;
	
	private String desc;
	
	
	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public Long getPhone() {
		return phone;
	}

	public void setPhone(Long phone) {
		this.phone = phone;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	
	@Override
	public String toString() {
		return "User [id=" + id + ", username=" + username + ", age=" + age + ", phone=" + phone + ", desc=" + desc
				+ "]";
	}
	
	
	
}
