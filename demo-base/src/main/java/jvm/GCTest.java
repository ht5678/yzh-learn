package jvm;

/**
 *  -Xms10M -Xmx10M -XX:+PrintGCDetails
 * 
 * @author yuezh2
 *
 * @date 2020年2月27日 下午8:46:18  
 *
 */
public class GCTest {
	public static void main(String[] args) throws InterruptedException {
		byte[] allocation1 , allocation2, allocation3, allocation4, allocation5, allocation6/**/;
		allocation1 = new byte[30231 * 1024];
		/*allocation2 = new byte[900*1024];
		allocation3 = new byte[1000*1024];
		allocation4 = new byte[1000*1024];
		allocation5 = new byte[1000*1024];
        allocation6 = new byte[1000*1024];*/
//        Thread.sleep(Integer.MAX_VALUE);
	}
}
