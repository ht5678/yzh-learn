package org.demo.netty.im.fake.domain;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import org.demo.netty.im.fake.util.ExternalizableUtil;

/**
 * 
 * @author yuezh2
 * @date	  2022年3月14日 下午3:21:21
 */
public class Waiter implements Externalizable{
	
	private Long id;
	private String tenantCode;
	private String teamCode;
	private String waiterName;
	private String waiterCode;
	private String status;
	private String type;
	private String shunt;
	private Integer curReception;
	private Integer maxReception;
	private String autoReply;
	private String replyMsg;
	private String realName;
	private String mobile;
	private Long sysBusyTimestamp;
	
	
	

	@Override
	public void writeExternal(ObjectOutput out) throws IOException {
		ExternalizableUtil.getInstance().writeLong(out, id == null ? 0 : id);
		ExternalizableUtil.getInstance().writeSafeUTF(out, tenantCode);
		ExternalizableUtil.getInstance().writeSafeUTF(out, teamCode);
		ExternalizableUtil.getInstance().writeSafeUTF(out, waiterName);
		ExternalizableUtil.getInstance().writeSafeUTF(out, waiterCode);
		ExternalizableUtil.getInstance().writeSafeUTF(out, status);
		ExternalizableUtil.getInstance().writeSafeUTF(out, type);
		ExternalizableUtil.getInstance().writeSafeUTF(out, shunt);
		ExternalizableUtil.getInstance().writeInt(out, curReception == null ? 0 : curReception);
		ExternalizableUtil.getInstance().writeInt(out, maxReception == null ? 0 : maxReception);
		ExternalizableUtil.getInstance().writeSafeUTF(out, autoReply);
		ExternalizableUtil.getInstance().writeSafeUTF(out, replyMsg);
        ExternalizableUtil.getInstance().writeLong(out, sysBusyTimestamp == null ? 0 : sysBusyTimestamp);
	}

	@Override
	public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
		id = ExternalizableUtil.getInstance().readLong(in);
		tenantCode = ExternalizableUtil.getInstance().readSafeUTF(in);
		teamCode = ExternalizableUtil.getInstance().readSafeUTF(in);
		waiterName = ExternalizableUtil.getInstance().readSafeUTF(in);
		waiterCode = ExternalizableUtil.getInstance().readSafeUTF(in);
		status = ExternalizableUtil.getInstance().readSafeUTF(in);
		type = ExternalizableUtil.getInstance().readSafeUTF(in);
		shunt = ExternalizableUtil.getInstance().readSafeUTF(in);
		curReception = ExternalizableUtil.getInstance().readInt(in);
		maxReception = ExternalizableUtil.getInstance().readInt(in);
		autoReply = ExternalizableUtil.getInstance().readSafeUTF(in);
		replyMsg = ExternalizableUtil.getInstance().readSafeUTF(in);
        sysBusyTimestamp = ExternalizableUtil.getInstance().readLong(in);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getShunt() {
		return shunt;
	}

	public void setShunt(String shunt) {
		this.shunt = shunt;
	}

	public Integer getCurReception() {
		return curReception;
	}

	public void setCurReception(Integer curReception) {
		this.curReception = curReception;
	}

	public Integer getMaxReception() {
		return maxReception;
	}

	public void setMaxReception(Integer maxReception) {
		this.maxReception = maxReception;
	}

	public String getAutoReply() {
		return autoReply;
	}

	public void setAutoReply(String autoReply) {
		this.autoReply = autoReply;
	}

	public String getReplyMsg() {
		return replyMsg;
	}

	public void setReplyMsg(String replyMsg) {
		this.replyMsg = replyMsg;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public Long getSysBusyTimestamp() {
		return sysBusyTimestamp;
	}

	public void setSysBusyTimestamp(Long sysBusyTimestamp) {
		this.sysBusyTimestamp = sysBusyTimestamp;
	}
	
	
	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		
		if (obj instanceof Waiter) {
			Waiter compareWaiter = (Waiter)obj;
			if (!waiterName.equals(compareWaiter.getWaiterName()) 
					|| !tenantCode.equals(compareWaiter.getTenantCode())) {
				return false;
			}
		} else {
			return false;
		}
		
		return true;
	}
	
	@Override
	public int hashCode() {
		int prime = 31;
		int result = 1;
		result = prime * result + waiterName.hashCode();
		result = prime * result + tenantCode.hashCode();
		return result;
	}
	
	

}
