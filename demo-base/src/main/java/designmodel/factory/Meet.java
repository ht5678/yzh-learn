package designmodel.factory;


/**
 * 
 * @author yuezh2
 *
 * @date 2020年7月9日 下午5:29:01  
 *
 */
public class Meet implements Restaurant {
	
	
	private String param;
	
	
	
	public Meet(String param) {
		this.param = param;
	}



	@Override
	public void cook() {
		System.out.println("来盘小炒肉!"+param);
	}

}
