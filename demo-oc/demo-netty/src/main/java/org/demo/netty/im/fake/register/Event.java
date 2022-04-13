package org.demo.netty.im.fake.register;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import org.demo.netty.im.fake.dispatcher.EventType;
import org.demo.netty.im.fake.util.ExternalizableUtil;

/**
 * 
 * @author yuezh2
 * @date	  2022年3月29日 下午9:19:43
 */
public class Event implements Externalizable{
	
	private EventType type;
	private String uid;
	private String tenantCode;
	private String teamCode;
	private String waiterName;
	private String waiterCode;
	private String content;
	
	public Event() {}
	
	public Event(EventType type, String uid, String tenantCode,String teamCode) {
		this(type, uid, tenantCode, teamCode, null, null);
	}
	
	public Event(EventType type, String uid, String tenantCode, 
			String teamCode, String waiterName, String waiterCode) {
		this.type = type;
		this.uid = uid;
		this.tenantCode = tenantCode;
		this.teamCode = teamCode;
		this.waiterName = waiterName;
		this.waiterCode = waiterCode;
	}
	
	public EventType getType() {
		return type;
	}

	public void setType(EventType type) {
		this.type = type;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getTenantCode() {
		return tenantCode;
	}

	public void setTenantCode(String tenantCode) {
		this.tenantCode = tenantCode;
	}

	public String getTeamCode() {
		return teamCode;
	}

	public void setTeamCode(String teamCode) {
		this.teamCode = teamCode;
	}

	public String getWaiterName() {
		return waiterName;
	}

	public void setWaiterName(String waiterName) {
		this.waiterName = waiterName;
	}

	public String getWaiterCode() {
		return waiterCode;
	}

	public void setWaiterCode(String waiterCode) {
		this.waiterCode = waiterCode;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Override
	public void writeExternal(ObjectOutput out) throws IOException {
		ExternalizableUtil.getInstance().writeSafeUTF(out, type.name());
		ExternalizableUtil.getInstance().writeSafeUTF(out, uid);
		ExternalizableUtil.getInstance().writeSafeUTF(out, tenantCode);
		ExternalizableUtil.getInstance().writeSafeUTF(out, teamCode);
		ExternalizableUtil.getInstance().writeSafeUTF(out, waiterName);
		ExternalizableUtil.getInstance().writeSafeUTF(out, waiterCode);
		ExternalizableUtil.getInstance().writeSafeUTF(out, content);
	}

	@Override
	public void readExternal(ObjectInput in) throws IOException {
		String typeName = ExternalizableUtil.getInstance().readSafeUTF(in);
		type = EventType.valueOf(typeName);
		uid = ExternalizableUtil.getInstance().readSafeUTF(in);
		tenantCode = ExternalizableUtil.getInstance().readSafeUTF(in);
		teamCode = ExternalizableUtil.getInstance().readSafeUTF(in);
		waiterName = ExternalizableUtil.getInstance().readSafeUTF(in);
		waiterCode = ExternalizableUtil.getInstance().readSafeUTF(in);
		content = ExternalizableUtil.getInstance().readSafeUTF(in);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		
		Event ot = (Event)obj;
		if (this.uid.equals(ot.getUid())) {
			return true;
		}
		
		return false;
	}
	
	@Override
	public int hashCode() {
		int result = 17;
		return 31 * result + uid.hashCode();
	}

	@Override
	public String toString() {
		return "Event{" +
				"type=" + type +
				", uid='" + uid + '\'' +
				", tenantCode='" + tenantCode + '\'' +
				", teamCode='" + teamCode + '\'' +
				", waiterName='" + waiterName + '\'' +
				", waiterCode='" + waiterCode + '\'' +
				", content='" + content + '\'' +
				'}';
	}
}
