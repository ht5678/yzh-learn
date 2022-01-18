package com.demo.juc.lock.simple;


/**
 * 
 * @author yue
 *
 */
public class ThreadUnsafeDemo {

	private static int data = 0;
	
	
	
	
	public static void main(String[] args) throws Exception{
//		for(int i =0 ; i < 2 ; i++){
			
			
			//
			Thread thread1 = new Thread(){
				public void run() {
					for(int i = 0 ; i < 10 ; i ++){
						//线程不安全
//						ThreadUnsafeDemo.data++;
//						System.out.println(data);
						
						//线程安全
						increment();
					}
//					System.out.println(data);
				};
			};
			
			thread1.start();
			
			
			//
			Thread thread2 = new Thread(){
				public void run() {
					for(int i = 0 ; i < 10 ; i ++){
//						ThreadUnsafeDemo.data++;
//						System.out.println(data);
						increment();
					}
//					System.out.println(data);
				};
			};
			
			thread2.start();			

			
			thread1.join();
			thread2.join();
			
			
//		}
		
//		System.out.println(data);
	}
	
	
	 private synchronized static void increment(){
		 ThreadUnsafeDemo.data++ ;
		 System.out.println(data);
	 }
	
}
