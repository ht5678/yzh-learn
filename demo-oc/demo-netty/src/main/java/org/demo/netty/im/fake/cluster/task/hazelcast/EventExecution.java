package org.demo.netty.im.fake.cluster.task.hazelcast;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import org.demo.netty.im.fake.cluster.task.ClusterTask;
import org.demo.netty.im.fake.im.OCIMServer;
import org.demo.netty.im.fake.register.Event;
import org.demo.netty.im.fake.util.ExternalizableUtil;
import org.demo.netty.im.fake.util.UUIDUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 路由分发事件
 * @author yuezh2
 * @date	  2022年3月31日 下午10:04:16
 */
public class EventExecution implements ClusterTask<Void>{
	
	private static Logger log = LoggerFactory.getLogger(EventExecution.class);
	
	private String taskId;
	
	private Event event;
	
	
	/**
	 * 
	 */
	public EventExecution() {
		this.taskId = UUIDUtils.getUID();
	}
	
	/**
	 * 
	 */
	public EventExecution(Event event) {
		this();
		this.event = event;
	}
	
	
	
	/**
	 * 
	 */
	@Override
	public void run() {
		if(null != event) {
			OCIMServer.getInst().getDispatcher().buildRelation(event);
		}
		log.info("[处理分配事件]-任务 taskId: {} - event: {}", taskId, event);
	}

	
	/**
	 * 
	 */
	@Override
	public void writeExternal(ObjectOutput out) throws IOException {
		ExternalizableUtil.getInstance().writeSafeUTF(out, taskId);
		ExternalizableUtil.getInstance().writeSerializable(out, event);
	}

	@Override
	public void readExternal(ObjectInput in) throws IOException {
		taskId = ExternalizableUtil.getInstance().readSafeUTF(in);
		event = (Event)ExternalizableUtil.getInstance().readSerializable(in);
	}

	
	/**
	 * 
	 */
	@Override
	public String getTaskId() {
		return taskId;
	}

	@Override
	public Void getResult() {
		return null;
	}

	
	
}
