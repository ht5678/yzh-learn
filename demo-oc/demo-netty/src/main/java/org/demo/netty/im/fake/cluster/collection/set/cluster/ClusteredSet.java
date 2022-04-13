package org.demo.netty.im.fake.cluster.collection.set.cluster;

import java.util.Collection;
import java.util.Iterator;

import org.demo.netty.im.fake.cluster.collection.set.CustomSet;

import com.hazelcast.core.ISet;

/**
 * 
 * @author yuezh2
 * @date	  2022年3月25日 下午9:46:45
 * @param <E>
 */
public class ClusteredSet<E> implements CustomSet<E>{

	private ISet<E> set;
	private String name;
	
	public ClusteredSet(String name, ISet<E> set) {
		this.set = set;
		this.name = name;
	}

	@Override
	public int size() {
		return set.size();
	}

	@Override
	public boolean isEmpty() {
		return set.isEmpty();
	}

	@Override
	public boolean contains(Object o) {
		return set.contains(o);
	}

	@Override
	public Iterator<E> iterator() {
		return set.iterator();
	}

	@Override
	public Object[] toArray() {
		return set.toArray();
	}

	@Override
	public <T> T[] toArray(T[] a) {
		return set.toArray(a);
	}

	@Override
	public boolean add(E e) {
		return set.add(e);
	}

	@Override
	public boolean remove(Object o) {
		return set.remove(o);
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		return set.containsAll(c);
	}

	@Override
	public boolean addAll(Collection<? extends E> c) {
		return set.addAll(c);
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		return set.retainAll(c);
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		return set.removeAll(c);
	}

	@Override
	public void clear() {
		set.clear();
	}

	@Override
	public String getName() {
		return name;
	}
}
