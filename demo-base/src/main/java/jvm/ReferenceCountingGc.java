package jvm;



/**
 * 
 * @author yuezh2
 *
 * @date 2020年2月25日 下午8:28:46  
 *
 */
public class ReferenceCountingGc {
	Object instance = null;

	public static void main(String[] args) {
		ReferenceCountingGc objA = new ReferenceCountingGc();
		ReferenceCountingGc objB = new ReferenceCountingGc();
		objA.instance = objB;
		objB.instance = objA;
		objA = null;
		objB = null;
	}
}
