package jvm;

/**
 * 
 * @author yuezh2
 *
 * @date 2020年2月25日 下午8:54:44  
 *
 */
public class StackOverflowTest {
	// JVM设置    
	// -Xss128k
	static int count = 0;
	
	static void redo() {
		count++;
		redo();
	}
	
	public static void main(String[] args) {
		try {
			redo();
		} catch (Throwable t) {
			t.printStackTrace();
			System.out.println(count);
		}
	}
}
