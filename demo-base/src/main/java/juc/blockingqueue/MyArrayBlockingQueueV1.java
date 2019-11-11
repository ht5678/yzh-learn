package juc.blockingqueue;

/**
 * 
 * @author yuezh2
 *
 * @date 2019年11月11日 下午4:46:49  
 *
 */
public class MyArrayBlockingQueueV1<E> {
	
	

	final Object[] items;
	
	int takeIndex;
	
	int putIndex;
	
	int count;
	
	
	
	
	public MyArrayBlockingQueueV1(int capacity , boolean fair) {
		if(capacity<=0) {
			throw new IllegalArgumentException();
		}
		this.items = new Object[capacity];
	}
	
	
	
	public MyArrayBlockingQueueV1(int capacity) {
		this(capacity, false);
	}
	
	
	
	final E itemAt(int i) {
		return (E)items[i];
	}
	
	
	
	public synchronized boolean put(E e)throws InterruptedException{
		checkNotNull(e);
		try {
			while(count == items.length) {
				this.wait();
			}
			
			items[putIndex] = e;
			if(++putIndex == items.length) {
				putIndex=0;
			}
			count++;
			this.notifyAll();
			
			return true;
		} finally {
		}
	}
	
	
	
	public E take() throws InterruptedException{
		try {
			while(count==0) {
				this.wait();
			}
			
			E x = (E)items[takeIndex];
			items[takeIndex] = null;
			if(++takeIndex == items.length) {
				takeIndex=0;
			}
			
			count--;
			this.notifyAll();
			return x;
		} finally {
		}
	}
	
	
	
	
	private static void checkNotNull(Object v) {
		if(v == null) {
			throw new NullPointerException();
		}
	}
	
}
