package org.demo.netty.im.fake.cluster.task;

import java.io.Externalizable;

/**
 * 
 * @author yuezh2
 * @date	  2022年3月23日 下午9:29:14
 */
public interface ClusterTask<V> extends Runnable,Externalizable {

	/**
	 * 获取人物编码
	 * @return
	 */
	String getTaskId();

	/**
	 * 返回执行结果
	 * @return
	 */
	V getResult();
	
}
