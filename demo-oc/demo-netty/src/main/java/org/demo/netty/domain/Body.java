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
public class Body implements Externalizable{
	
	private BodyType type;
	private String content;
	
	
	public Body() {}
	
	public Body(BodyType type) {
		this(type, null);
	}
	
	public Body(BodyType type, String content) {
		this.type = type;
		this.content = content;
	}
	

	@Override
	public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
		String serializableType = ExternalizableUtil.getInstance().readSafeUTF(in);
		if(null != serializableType){
			type = BodyType.valueOf(serializableType);
		}else{
			type = null;
		}
		content = ExternalizableUtil.getInstance().readSafeUTF(in);
	}

	@Override
	public void writeExternal(ObjectOutput out) throws IOException {
		String serializableType = null;
		if(type != null){
			serializableType = type.name();
		}
		ExternalizableUtil.getInstance().writeSafeUTF(out, serializableType);
		ExternalizableUtil.getInstance().writeSafeUTF(out, content);
	}

	public BodyType getType() {
		return type;
	}

	public void setType(BodyType type) {
		this.type = type;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Override
	public String toString() {
		return "Body [type=" + type + ", content=" + content + "]";
	}

}
