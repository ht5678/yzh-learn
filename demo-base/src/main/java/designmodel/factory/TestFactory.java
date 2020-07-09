package designmodel.factory;


/**
 * 
 * @author yuezh2
 *
 * @date 2020年7月9日 下午5:33:04  
 *
 */
public class TestFactory {

	public static void main(String[] args) {
		Restaurant meetCooker = CookFactory.createMeet("三分熟");
		meetCooker.cook();
		
		Restaurant duckCooker = CookFactory.createDuck();
		duckCooker.cook();
		
		Restaurant fishCooker = CookFactory.createFish();
		fishCooker.cook();
	}
	
}
