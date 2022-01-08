package com.demo.juc.lock.simple;


/**
 * 
 * @author yue
 *
 */
public class InterruptDemo2 {

	
	public static void main(String[] args) throws Exception{
		MyThread thread = new MyThread();
		thread.start();
		
		Thread.sleep(1000);
		
		thread.setShouldRun(false);
		thread.interrupt();
	}

	

	private static class MyThread extends Thread {
		private Boolean shouldRun = true;
		
		
		@Override
		public void run() {
			while(shouldRun){
				System.out.println("线程1在执行工作 ... ... ");
				try {
					Thread.sleep(30 * 1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		
		
		public void setShouldRun(Boolean shouldRun){
			this.shouldRun = shouldRun;
		}
		
		
	}
	
}

