package org.demo.netty.session;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import org.demo.netty.util.ExternalizableUtil;

/**
 * 
 * @author yuezh2
 * @date	  2022年3月28日 下午4:53:18
 */
public class Customer implements Externalizable{
	
	private static final long serialVersionUID = 1L;
	private String uid;
	private String name;
	private boolean login;
	private String tenantCode;
	private String teamCode;
	private String skillCode;
	private String skillName;
	private String goodsCode;
	private String device;
	private Integer wait;
	private long time;

	public Customer() {}
	
	public Customer(String uid, String name, boolean login,
			String tenantCode, String teamCode,
			String skillCode, String skillName,
			String goodsCode, String device) {
		this.uid = uid;
		this.name = name;
		this.login = login;
		this.tenantCode = tenantCode;
		this.teamCode = teamCode;
		this.skillCode = skillCode;
		this.skillName = skillName;
		this.goodsCode = goodsCode;
		this.device = device;
		this.time = System.currentTimeMillis();
		this.wait = 0;
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

	public boolean isLogin() {
		return login;
	}

	public void setLogin(boolean login) {
		this.login = login;
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

	public String getSkillCode() {
		return skillCode;
	}

	public void setSkillCode(String skillCode) {
		this.skillCode = skillCode;
	}

	public String getSkillName() {
		return skillName;
	}

	public void setSkillName(String skillName) {
		this.skillName = skillName;
	}
	
	public String getGoodsCode() {
		return goodsCode;
	}

	public void setGoodsCode(String goodsCode) {
		this.goodsCode = goodsCode;
	}

	public String getDevice() {
		return device;
	}

	public void setDevice(String device) {
		this.device = device;
	}

	public Integer getWait() {
		return wait;
	}

	public void setWait(Integer wait) {
		this.wait = wait;
	}

	public long getTime() {
		return time;
	}

	public void setTime(Long time) {
		this.time = time;
	}

	@Override
	public void writeExternal(ObjectOutput out) throws IOException {
		ExternalizableUtil.getInstance().writeSafeUTF(out, uid);
		ExternalizableUtil.getInstance().writeSafeUTF(out, name);
		ExternalizableUtil.getInstance().writeBoolean(out, login);
		ExternalizableUtil.getInstance().writeSafeUTF(out, tenantCode);
		ExternalizableUtil.getInstance().writeSafeUTF(out, teamCode);
		ExternalizableUtil.getInstance().writeSafeUTF(out, skillCode);
		ExternalizableUtil.getInstance().writeSafeUTF(out, skillName);
		ExternalizableUtil.getInstance().writeSafeUTF(out, goodsCode);
		ExternalizableUtil.getInstance().writeSafeUTF(out, device);
		ExternalizableUtil.getInstance().writeInt(out, wait);
		ExternalizableUtil.getInstance().writeLong(out, time);
	}

	@Override
	public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
		uid = ExternalizableUtil.getInstance().readSafeUTF(in);
		name = ExternalizableUtil.getInstance().readSafeUTF(in);
		login = ExternalizableUtil.getInstance().readBoolean(in);
		tenantCode = ExternalizableUtil.getInstance().readSafeUTF(in);
		teamCode = ExternalizableUtil.getInstance().readSafeUTF(in);
		skillCode = ExternalizableUtil.getInstance().readSafeUTF(in);
		skillName = ExternalizableUtil.getInstance().readSafeUTF(in);
		goodsCode = ExternalizableUtil.getInstance().readSafeUTF(in);
		device = ExternalizableUtil.getInstance().readSafeUTF(in);
		wait = ExternalizableUtil.getInstance().readInt(in);
		time = ExternalizableUtil.getInstance().readLong(in);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (null == obj) {
			return false;
		}
		
		if (obj instanceof Customer) {
			Customer that = (Customer)obj;
			if (that.getUid() != null && that.getUid().equals(this.uid)) {
				return true;
			}
		} 
		
		return false;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (uid == null ? 0 : uid.hashCode());
		return result;
	}

	@Override
	public String toString() {
		return "Customer{" +
				"uid='" + uid + '\'' +
				", name='" + name + '\'' +
				", login=" + login +
				", tenantCode='" + tenantCode + '\'' +
				", teamCode='" + teamCode + '\'' +
				", skillCode='" + skillCode + '\'' +
				", skillName='" + skillName + '\'' +
				", goodsCode='" + goodsCode + '\'' +
				", device='" + device + '\'' +
				", wait='" + wait + '\'' +
				", time=" + time +
				'}';
	}
}
