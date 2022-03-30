package org.demo.netty.cluster.task.hazelcast;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import org.demo.netty.cluster.task.ClusterTask;
import org.demo.netty.im.OCIMServer;
import org.demo.netty.routing.CustomerRoute;
import org.demo.netty.util.ExternalizableUtil;
import org.demo.netty.util.UUIDUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 删除集群中已存在的客户信息
 * @author yuezh2
 * @date	  2022年3月30日 下午10:37:17
 */
public class RmCustomerSessionExecution implements ClusterTask<Void>{
	
	private static Logger log = LoggerFactory.getLogger(RmCustomerSessionExecution.class);
	
	private String taskId;
	
	private CustomerRoute customerRoute;
	
	
	/**
	 * 
	 */
	public RmCustomerSessionExecution() {
		this.taskId = UUIDUtils.getUID();
	}
	
	
	/**
	 * 
	 */
	public RmCustomerSessionExecution(CustomerRoute customerRoute) {
		this();
		this.customerRoute = customerRoute;
	}
	
	
	/**
	 * 
	 */
	@Override
	public void run() {
		OCIMServer.getInst().getRoutingTable().removeRemoteCustomerSession(customerRoute);
		log.info("[处理删除集群中已存在的客户消息]-任务 taskId: {} - customerRoute: {}", taskId, customerRoute);
	}

	/**
	 * 
	 */
	@Override
	public void writeExternal(ObjectOutput out) throws IOException {
		ExternalizableUtil.getInstance().writeSafeUTF(out, taskId);
		ExternalizableUtil.getInstance().writeSerializable(out, customerRoute);
	}

	/**
	 * 
	 */
	@Override
	public void readExternal(ObjectInput in) throws IOException {
		taskId = ExternalizableUtil.getInstance().readSafeUTF(in);
		customerRoute = (CustomerRoute)ExternalizableUtil.getInstance().readSerializable(in);
	}

	
	/**
	 * 
	 */
	@Override
	public String getTaskId() {
		return taskId;
	}

	
	/**
	 * 
	 */
	@Override
	public Void getResult() {
		return null;
	}

	
	
}
