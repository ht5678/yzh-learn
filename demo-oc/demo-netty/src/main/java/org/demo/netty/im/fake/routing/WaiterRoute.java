package org.demo.netty.im.fake.routing;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import org.demo.netty.im.fake.im.OCIMServer;
import org.demo.netty.im.fake.node.NodeID;
import org.demo.netty.im.fake.session.WaiterSession;
import org.demo.netty.im.fake.util.ExternalizableUtil;

/**
 * 
 * @author yuezh2
 * @date	  2022年3月28日 下午4:55:28
 */
public class WaiterRoute implements Externalizable, Route {

	private static final long serialVersionUID = -1013547561230619979L;
	
	private String uid;
	private NodeID nodeID;
	private String version;

	public WaiterRoute() {}
	
	public WaiterRoute(WaiterSession session) {
		this.uid = session.getUid();
		this.nodeID = OCIMServer.getInst().getNodeID();
		this.version = session.getVersion();
	}
	
	@Override
	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	@Override
	public NodeID getNodeID() {
		return nodeID;
	}

	public void setNodeID(NodeID nodeID) {
		this.nodeID = nodeID;
	}

	@Override
	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	@Override
	public void writeExternal(ObjectOutput out) throws IOException {
		ExternalizableUtil.getInstance().writeSafeUTF(out, uid);
		ExternalizableUtil.getInstance().writeSerializable(out, nodeID);
		ExternalizableUtil.getInstance().writeSafeUTF(out, version);
	}

	@Override
	public void readExternal(ObjectInput in) throws IOException {
		uid = ExternalizableUtil.getInstance().readSafeUTF(in);
		nodeID = (NodeID)ExternalizableUtil.getInstance().readSerializable(in);
		version = ExternalizableUtil.getInstance().readSafeUTF(in);
	}

	@Override
	public String toString() {
		return "WaiterRoute{" +
				"uid='" + uid + '\'' +
				", nodeID=" + nodeID +
				", version='" + version + '\'' +
				'}';
	}
}
