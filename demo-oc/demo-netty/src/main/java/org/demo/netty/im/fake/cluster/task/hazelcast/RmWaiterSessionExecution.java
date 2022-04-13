package org.demo.netty.im.fake.cluster.task.hazelcast;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import org.demo.netty.im.fake.cluster.task.ClusterTask;
import org.demo.netty.im.fake.im.OCIMServer;
import org.demo.netty.im.fake.routing.WaiterRoute;
import org.demo.netty.im.fake.util.ExternalizableUtil;
import org.demo.netty.im.fake.util.UUIDUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 删除集群中已存在的客服信息
 * @author yuezh2
 * @date	  2022年3月31日 下午9:22:42
 */
public class RmWaiterSessionExecution implements ClusterTask<Void>{
	
	
	private static Logger log = LoggerFactory.getLogger(RmWaiterSessionExecution.class);
	
	private String taskId;
	
	private WaiterRoute waiterRoute;
	
	
	/**
	 * 
	 */
	public RmWaiterSessionExecution() {
		this.taskId = UUIDUtils.getUID();
	}
	
	
	/**
	 * 
	 */
	public RmWaiterSessionExecution(WaiterRoute waiterRoute) {
		this();
		this.waiterRoute = waiterRoute;
	}
	
	
	/**
	 * 
	 */
	@Override
	public void run() {
		OCIMServer.getInst().getRoutingTable().removeRemoteWaiterSession(waiterRoute);
		log.info("[处理删除集群中已存在的客服信息]-任务  taskId : {} - waiterRoute : {}" , taskId , waiterRoute);
	}

	/**
	 * 
	 */
	@Override
	public void writeExternal(ObjectOutput out) throws IOException {
		ExternalizableUtil.getInstance().writeSafeUTF(out, taskId);
		ExternalizableUtil.getInstance().writeSerializable(out, waiterRoute);
	}

	@Override
	public void readExternal(ObjectInput in) throws IOException {
		taskId = ExternalizableUtil.getInstance().readSafeUTF(in);
		waiterRoute = (WaiterRoute)ExternalizableUtil.getInstance().readSerializable(in);
	}

	
	/**
	 * 
	 */
	@Override
	public String getTaskId() {
		return this.taskId;
	}

	@Override
	public Void getResult() {
		return null;
	}

	
	
}
