package jvm;



/**
 * 
 * @author yuezh2
 *
 * @date 2019年12月10日 下午4:42:33  
 *
 */
public class Math {
	
	public static final Integer CONSTANT_1 = 666;
	
	
	public int math() {
		int a=1;
		int b=3;
		int c=(a+b)*10;
		return c;
	}
	
	
	public static void main(String[] args) {
		Math math = new Math();
		System.out.println(math.math());
	}

}
