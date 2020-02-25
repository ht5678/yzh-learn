package jvm;


/**
 * 
 * @author yuezh2
 *
 * @date 2020年2月25日 下午8:55:08  
 *
 */
public class User {
	
	private int id;
	private String name;
	
	public User(int id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	@Override
		protected void finalize() throws Throwable {
			super.finalize();
			System.out.println("关闭资源,user"+this.getId()+"即将被回收 ");
		}
	
}
