package org.demo.netty.session;

import java.util.Collection;
import java.util.Iterator;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 
 * @author yuezh2
 * @date	  2022年3月15日 下午6:29:41
 * @param <E>
 */
public class DefaultCustomQueue<E> implements CustomQueue<E>{

	private BlockingQueue<E> queue = new LinkedBlockingQueue<>();
	private ReentrantLock lock = new ReentrantLock();
	
	private long size = 0;
	private String name;

	public DefaultCustomQueue() {}

	public DefaultCustomQueue(String name) {
		this.name = name;
	}
	
	/**
	 * @param e
	 * @return
	 */
	@Override
	public boolean add(E e) {
		return queue.add(e);
	}

	/**
	 * @param e
	 * @return
	 */
	@Override
	public boolean offer(E e) {
		return queue.offer(e);
	}

	/**
	 * @return
	 */
	@Override
	public E remove() {
		return queue.remove();
	}

	/**
	 * @return
	 */
	@Override
	public E poll() {
		return queue.poll();
	}

	/**
	 * @return
	 */
	@Override
	public E element() {
		return queue.element();
	}

	/**
	 * @return
	 */
	@Override
	public E peek() {
		return queue.peek();
	}

	/**
	 * @return
	 */
	@Override
	public int size() {
		return queue.size();
	}

	/**
	 * @return
	 */
	@Override
	public boolean isEmpty() {
		return queue.isEmpty();
	}

	/**
	 * @param o
	 * @return
	 */
	@Override
	public boolean contains(Object o) {
		return queue.contains(o);
	}

	/**
	 * @return
	 */
	@Override
	public Iterator<E> iterator() {
		return queue.iterator();
	}

	/**
	 * @return
	 */
	@Override
	public Object[] toArray() {
		return queue.toArray();
	}

	/**
	 * @param a
	 * @return
	 */
	@Override
	public <T> T[] toArray(T[] a) {
		return queue.toArray(a);
	}

	/**
	 * @param o
	 * @return
	 */
	@Override
	public boolean remove(Object o) {
		return queue.remove(o);
	}

	/**
	 * @param c
	 * @return
	 */
	@Override
	public boolean containsAll(Collection<?> c) {
		return queue.containsAll(c);
	}

	/**
	 * @param c
	 * @return
	 */
	@Override
	public boolean addAll(Collection<? extends E> c) {
		return queue.addAll(c);
	}

	/**
	 * @param c
	 * @return
	 */
	@Override
	public boolean removeAll(Collection<?> c) {
		return queue.removeAll(c);
	}

	/**
	 * @param c
	 * @return
	 */
	@Override
	public boolean retainAll(Collection<?> c) {
		return queue.retainAll(c);
	}

	/**
	 * 
	 */
	@Override
	public void clear() {
		queue.clear();
	}

	/**
	 * @return
	 */
	@Override
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 */
	@Override
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 
	 */
	@Override
	public long getQueueSize() {
		return size;
	}

	/**
	 * @param e
	 * @throws InterruptedException
	 */
	@Override
	public void put(E e) throws InterruptedException {
		queue.put(e);
	}

	/**
	 * @param e
	 * @param timeout
	 * @param unit
	 * @return
	 * @throws InterruptedException
	 */
	@Override
	public boolean offer(E e, long timeout, TimeUnit unit) throws InterruptedException {
		return queue.offer(e, timeout, unit);
	}

	/**
	 * @return
	 * @throws InterruptedException
	 */
	@Override
	public E take() throws InterruptedException {
		return queue.take();
	}

	/**
	 * @param timeout
	 * @param unit
	 * @return
	 * @throws InterruptedException
	 */
	@Override
	public E poll(long timeout, TimeUnit unit) throws InterruptedException {
		return queue.poll(timeout, unit);
	}

	/**
	 * @return
	 */
	@Override
	public int remainingCapacity() {
		return queue.remainingCapacity();
	}

	/**
	 * @param c
	 * @return
	 */
	@Override
	public int drainTo(Collection<? super E> c) {
		return queue.drainTo(c);
	}

	/**
	 * @param c
	 * @param maxElements
	 * @return
	 */
	@Override
	public int drainTo(Collection<? super E> c, int maxElements) {
		return queue.drainTo(c, maxElements);
	}

	/**
	 * @param name
	 */
	@Override
	public void lock() {
		lock.lock();
	}

	/**
	 * @param name
	 */
	@Override
	public void unlock() {
		lock.unlock();
	}

	/**
	 * @param timeout
	 * @throws InterruptedException
	 */
	@Override
	public void tryLock(long timeout) throws InterruptedException {
		lock.tryLock(timeout, TimeUnit.MILLISECONDS);
	}
}