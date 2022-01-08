package com.demo.juc.lock.simple;


/**
 * 
 * @author yue
 *
 */
public class InterruptDemo1 {

	
	public static void main(String[] args) throws Exception{
		Thread thread = new Thread(new Runnable() {
			
			@Override
			public void run() {
				while(!Thread.interrupted()){
					System.out.println("线程1在执行工作 ... ... ");
				}
			}
		});
		
		thread.start();
		
		Thread.sleep(1000);
		
		thread.interrupt();
	}
	
}
