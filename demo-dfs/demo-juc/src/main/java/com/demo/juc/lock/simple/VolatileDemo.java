package com.demo.juc.lock.simple;

/**
 * 
 * cpu缓存模型 -> java内存模型 -> 原子性 , 可见性 , 有序性 -> volatile的作用 -> volatile底层原理 -> volatile实战
 * 
 * @author yuezh2@lenovo.com
 *	@date 2022年1月10日下午8:43:43
 */
public class VolatileDemo {

	
//	static int flag = 0;
	
	static volatile int flag = 0;
	
	
	public static void main(String[] args) throws Exception{
		
		//线程1
		new Thread() {
			@Override
			public void run() {
				int localFlag = flag;
				
				while(true) {
					if(localFlag != flag) {
						System.out.println("读取到了修改后的标志位 : "+flag);
						localFlag = flag;
					}
				}
			}
		}.start();
		
		
		//线程2
		new Thread() {
			
			@Override
			public void run() {
				int localFlag = flag;
				while(true) {
					System.out.println("标志位被修改成了 : "+  ++localFlag);
					flag = localFlag;
					try {
						Thread.sleep(1 * 1000L);
					}catch(Exception e) {
						e.printStackTrace();
					}
				}
			}
		}.start();
		
		
	}
	
	
}
