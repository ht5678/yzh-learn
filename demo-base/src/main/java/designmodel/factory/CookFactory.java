package designmodel.factory;


/**
 * 
 * @author yuezh2
 *
 * @date 2020年7月9日 下午5:31:25  
 *
 */
public class CookFactory {

	
	public static Restaurant createMeet(String param) {
		return new Meet(param);
	}
	
	
	public static Restaurant createFish() {
		return new Fish();
	}
	
	
	public static Restaurant createDuck() {
		return new Duck();
	}
	
	
}
