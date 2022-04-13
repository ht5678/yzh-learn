package org.demo.netty.im.fake.cluster.task.hazelcast;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import org.demo.netty.im.fake.cluster.task.ClusterTask;
import org.demo.netty.im.fake.im.OCIMServer;
import org.demo.netty.im.fake.transfer.TransferWaiter;
import org.demo.netty.im.fake.util.ExternalizableUtil;
import org.demo.netty.im.fake.util.UUIDUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @author yuezh2
 * @date	  2022年3月31日 下午10:10:28
 */
public class TransferExecution implements ClusterTask<Void>{
	
	private Logger log = LoggerFactory.getLogger(TransferExecution.class);
	
	private String taskId;
	
	private TransferWaiter transferWaiter;
	
	
	/**
	 * 
	 */
	public TransferExecution() {
		this.taskId = UUIDUtils.getUID();
	}
	
	
	/**
	 * 
	 */
	public TransferExecution(TransferWaiter transferWaiter) {
		this();
		this.transferWaiter = transferWaiter;
	}
	
	
	/**
	 * 
	 */
	@Override
	public void run() {
		if(null == transferWaiter) {
			OCIMServer.getInst().getRoutingTable().routeTransferByWaiter(transferWaiter);
		}
		log.info("[处理按客服转接]-任务 taskId: {} - transferWaiter: {}", taskId, transferWaiter);
	}

	
	/**
	 * 
	 */
	@Override
	public void writeExternal(ObjectOutput out) throws IOException {
		ExternalizableUtil.getInstance().writeSafeUTF(out, taskId);
		ExternalizableUtil.getInstance().writeSerializable(out, transferWaiter);
	}

	@Override
	public void readExternal(ObjectInput in) throws IOException {
		taskId = ExternalizableUtil.getInstance().readSafeUTF(in);
		transferWaiter = (TransferWaiter)ExternalizableUtil.getInstance().readSerializable(in);
	}

	@Override
	public String getTaskId() {
		return taskId;
	}

	@Override
	public Void getResult() {
		return null;
	}

	
	
}
