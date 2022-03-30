package org.demo.netty.cluster.task.hazelcast;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import org.demo.netty.cluster.task.ClusterTask;
import org.demo.netty.domain.Packet;
import org.demo.netty.im.OCIMServer;
import org.demo.netty.util.ExternalizableUtil;
import org.demo.netty.util.UUIDUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @author yuezh2
 * @date	  2022年3月30日 下午10:02:06
 */
public class PacketExecution implements ClusterTask<Void> {
	
	
	private static Logger log = LoggerFactory.getLogger(PacketExecution.class);
	
	private String taskId;
	private Packet packet;
	private RemotePacketType type;
	
	
	/**
	 * 
	 */
	public PacketExecution() {
		this.taskId = UUIDUtils.getUID();
		this.type = RemotePacketType.NORMAL;
	}
	
	
	/**
	 * 
	 */
	public PacketExecution(Packet packet , RemotePacketType type) {
		this.taskId = UUIDUtils.getUID();
		this.packet = packet;
		this.type = type;
	}
	
	
	
	/**
	 * 
	 */
	@Override
	public void run() {
		if(type == RemotePacketType.NORMAL) {
			OCIMServer.getInst().getRoutingTable().routePacket(packet);
		}else if(type == RemotePacketType.CHAT_CLOSE) {
			OCIMServer.getInst().getRoutingTable().routeChatClose(packet);
		}
		log.info("[处理分发消息 - 任务 taskId : {}  type : {} - packet : {} ]" , taskId , type.name() , packet);
	}

	
	/**
	 * 
	 */
	@Override
	public void writeExternal(ObjectOutput out) throws IOException {
		ExternalizableUtil.getInstance().writeSafeUTF(out, type.name());
		ExternalizableUtil.getInstance().writeSafeUTF(out, taskId);
		ExternalizableUtil.getInstance().writeSerializable(out, packet);
	}

	
	/**
	 * 
	 */
	@Override
	public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
		String typeName = ExternalizableUtil.getInstance().readSafeUTF(in);
		if (null != typeName) {
			type = RemotePacketType.valueOf(typeName);
		}
		taskId = ExternalizableUtil.getInstance().readSafeUTF(in);
		packet = (Packet)ExternalizableUtil.getInstance().readSerializable(in);
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

	
	
	/**
	 * 
	 * @author yuezh2
	 * @date	  2022年3月30日 下午10:05:17
	 */
	public enum RemotePacketType {
		NORMAL,//正常消息
		CHAT_CLOSE;//客服关闭消息
	}
	
	
}
