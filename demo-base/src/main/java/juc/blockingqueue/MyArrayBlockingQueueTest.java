package juc.blockingqueue;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * 
 * offer - poll  &  add - remove的差异 ， 后一个item为null的时候会抛出异常 , 前一个返回null
 * 
 * @author yuezh2
 *
 * @date 2019年11月11日 下午4:35:17  
 *
 */
public class MyArrayBlockingQueueTest {

	BlockingQueue<String> blockingQueue1 = new ArrayBlockingQueue<>(5);
	
	BlockingQueue<String> blockingQueue2 = new ArrayBlockingQueue<>(5);
	
	
	public boolean addByOffer(String s) {
		return blockingQueue1.offer(s);
	}
	
	
	public boolean addByAdd(String s) {
		return blockingQueue2.add(s);
	}
	
	
	public String removeByPoll() {
		return blockingQueue1.poll();
	}
	
	
	public String removeByRemove() {
		return blockingQueue2.remove();
	}
	
	
	
	public static void main(String args[]) {
		MyArrayBlockingQueueTest myArrayBlockingQueue = new MyArrayBlockingQueueTest();
		System.out.println("Adding 6 elements by offer ()");
		for (int i = 0; i < 6; i++) {
			System.out.println(
					"Element no :" + (i + 1) + "adding by offer() : " + myArrayBlockingQueue.addByOffer("String" + i));
		}

		System.out.println("=============================");

		System.out.println("Adding 6 elements by add ()");
		for (int i = 0; i < 5; i++) {
			System.out.println(
					"Element no :" + (i + 1) + "adding by add() : " + myArrayBlockingQueue.addByAdd("String" + i));
		}
		
  
		System.out.println("=============================");
        System.out.println("Removing 6 elements by poll ()");
        for (int i = 0; i < 6; i++) {
            System.out.println("Element no :" + (i+1) + " removed by poll() : " + myArrayBlockingQueue.removeByPoll());
        }
        
 
		System.out.println("=============================");
        System.out.println("Removing 6 elements by remove ()");
        for (int i = 0; i < 6; i++) {
            System.out.println("Element no :" + (i+1) + " removed by remove() : " + myArrayBlockingQueue.removeByRemove());
        }

	}
}
