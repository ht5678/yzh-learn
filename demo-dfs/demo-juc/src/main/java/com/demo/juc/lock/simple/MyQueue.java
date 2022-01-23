package com.demo.juc.lock.simple;

import java.util.LinkedList;

/**
 * 
 * @author yue
 *
 */
public class MyQueue {
	
	
	private static final int MAX_SIZE = 100;
	

	private LinkedList<String> queue = new LinkedList<>();
	

	
	public synchronized void offer(String element){
		try{
			if(queue.size() == MAX_SIZE){
				//一个线程只要到了这一步 , 说白了 , 已经获取到了一把锁
				//让该进程进入等待的状态 , 释放掉锁
				wait();
			}
			
			queue.addLast(element);
			
			notifyAll();
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	
	
	public synchronized String take(){
		//别的线程可以从队列中take数据
		String element = null; 
		try{
			if(queue.size() ==0){
				wait();		//释放锁 , 等待其他线程放入数据
			}
			
			element = queue.getFirst();
			notifyAll();		//唤醒当前在等待锁的线程
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return element;
	}
	
}
