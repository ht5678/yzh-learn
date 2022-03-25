package org.demo.netty.cluster.collection.set;


/**
 * 
 * @author yuezh2
 * @date	  2022年3月25日 下午9:33:31
 */
public interface CustomSetFactoryStategy {

	/**
	 * 
	 * @param <E>
	 * @return
	 */
	<E> CustomSet<E> createOCSet(String name);
	
}
