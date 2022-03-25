package org.demo.netty.cluster.collection.set.local;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.demo.netty.cluster.collection.set.CustomSet;
import org.demo.netty.cluster.collection.set.CustomSetFactoryStategy;

/**
 * 
 * @author yuezh2
 * @date	  2022年3月25日 下午9:37:10
 */
public class DefaultCustomSetFactoryStrategy implements CustomSetFactoryStategy{

	private Map<String, CustomSet<?>> sets = new ConcurrentHashMap<>();
	
	
	/**
	 * 
	 */
	@Override
	public <E> CustomSet<E> createOCSet(String name) {
		CustomSet<?> set = sets.get(name);
		if(null == set) {
			set = new DefaultCustomSet<E>(name);
			sets.put(name, set);
		}
		return (CustomSet<E>)set;
	}

	
	
}
