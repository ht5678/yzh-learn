package jvm;



/**
 * 
 * @author yuezh2
 *
 * @date 2020年2月27日 下午8:46:27  
 *
 */
public class DeadLockTest {

	private static Object lock1 = new Object();
	private static Object lock2 = new Object();

	public static void main(String[] args) {
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				synchronized (lock1) {
					try {
						System.out.println("thread1 begin");
						Thread.sleep(5000);
					} catch (InterruptedException e) {
					}
					synchronized (lock2) {
						System.out.println("thread1 end");
					}
				}
			}
		}).start();

		new Thread(new Runnable() {
			
			@Override
			public void run() {
				synchronized (lock2) {
					try {
						System.out.println("thread2 begin");
						Thread.sleep(5000);
					} catch (InterruptedException e) {
					}
					synchronized (lock1) {
						System.out.println("thread2 end");
					}
				}
		}}).start();

		System.out.println("main thread end");
	}
}
