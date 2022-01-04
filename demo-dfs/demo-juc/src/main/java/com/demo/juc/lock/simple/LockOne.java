package com.demo.juc.lock.simple;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * 
 * @author yuezh2@lenovo.com
 *	@date 2022年1月4日下午5:47:49
 */
public class LockOne implements Lock{
	
	
	private volatile boolean[] flag = new boolean[2];
	
	

	@Override
	public void lock() {
		int i = (int)(Thread.currentThread().getId());
		int j = 1-i;
		flag[i] = true;
		while(flag[j]) {}
	}

	@Override
	public void lockInterruptibly() throws InterruptedException {
		int i = (int)Thread.currentThread().getId()-1;
		flag[i] = false;
	}
	
	
	@Override
	public void unlock() {
		int i = (int)Thread.currentThread().getId()-1;
		flag[i] = false;
	}
	
	
	

	@Override
	public Condition newCondition() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean tryLock() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean tryLock(long arg0, TimeUnit arg1) throws InterruptedException {
		// TODO Auto-generated method stub
		return false;
	}



}
