package org.demo.netty.domain;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import org.demo.netty.util.ExternalizableUtil;

/**
 * 
 * @author yue
 *
 */
public class AddressFrom implements Externalizable{
	
	private String uid;
	private String name;
	private Identity idy;
	
	
	public AddressFrom() {}
	
	public AddressFrom(Identity idy) {
		this.idy = idy;
	}
	
	public AddressFrom(String uid, Identity idy) {
		this.uid = uid;
		this.idy = idy;
	}
	
	public AddressFrom(String uid, String name, Identity idy) {
		this.uid = uid;
		this.idy = idy;
		this.name = name;
	}
	
	

	@Override
	public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
		String serializableIdy = ExternalizableUtil.getInstance().readSafeUTF(in);
		if (null != serializableIdy) {
			idy = Identity.valueOf(serializableIdy);
		} else {
			idy = null;
		}
		uid = ExternalizableUtil.getInstance().readSafeUTF(in);
		name = ExternalizableUtil.getInstance().readSafeUTF(in);
	}

	@Override
	public void writeExternal(ObjectOutput out) throws IOException {
		String serializableIdy = null;
		if (null != idy) {
			serializableIdy = idy.name();
		}
		ExternalizableUtil.getInstance().writeSafeUTF(out, serializableIdy);
		ExternalizableUtil.getInstance().writeSafeUTF(out, uid);
		ExternalizableUtil.getInstance().writeSafeUTF(out, name);
	}




	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Identity getIdy() {
		return idy;
	}

	public void setIdy(Identity idy) {
		this.idy = idy;
	}

	@Override
	public String toString() {
		return "AddressFrom [uid=" + uid + ", name=" + name + ", idy=" + idy + "]";
	}
	
}
