package org.demo.netty.transfer;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import org.demo.netty.util.ExternalizableUtil;

/**
 * 
 * @author yuezh2
 * @date	  2022年3月28日 下午4:56:32
 */
public class TransferTeam implements Externalizable{

	private String ttc;
	private String tmc;
	private String skc;
	private String skn;
	private String uid;
	private String name;
	private String fromWc;
	private String fromWn;
	private String reason;

	public TransferTeam() {}

	public String getTtc() {
		return ttc;
	}

	public void setTtc(String ttc) {
		this.ttc = ttc;
	}

	public String getTmc() {
		return tmc;
	}

	public void setTmc(String tmc) {
		this.tmc = tmc;
	}

	public String getSkc() {
		return skc;
	}

	public void setSkc(String skc) {
		this.skc = skc;
	}

	public String getSkn() {
		return skn;
	}

	public void setSkn(String skn) {
		this.skn = skn;
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

	public String getFromWc() {
		return fromWc;
	}

	public void setFromWc(String fromWc) {
		this.fromWc = fromWc;
	}

	public String getFromWn() {
		return fromWn;
	}

	public void setFromWn(String fromWn) {
		this.fromWn = fromWn;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	@Override
	public void writeExternal(ObjectOutput out) throws IOException {
		ExternalizableUtil.getInstance().writeSafeUTF(out, ttc);
		ExternalizableUtil.getInstance().writeSafeUTF(out, tmc);
		ExternalizableUtil.getInstance().writeSafeUTF(out, skc);
		ExternalizableUtil.getInstance().writeSafeUTF(out, skn);
		ExternalizableUtil.getInstance().writeSafeUTF(out, uid);
		ExternalizableUtil.getInstance().writeSafeUTF(out, name);
		ExternalizableUtil.getInstance().writeSafeUTF(out, fromWc);
		ExternalizableUtil.getInstance().writeSafeUTF(out, fromWn);
		ExternalizableUtil.getInstance().writeSafeUTF(out, reason);
	}

	@Override
	public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
		this.ttc = ExternalizableUtil.getInstance().readSafeUTF(in);
		this.tmc = ExternalizableUtil.getInstance().readSafeUTF(in);
		this.skc = ExternalizableUtil.getInstance().readSafeUTF(in);
		this.skn = ExternalizableUtil.getInstance().readSafeUTF(in);
		this.uid = ExternalizableUtil.getInstance().readSafeUTF(in);
		this.name = ExternalizableUtil.getInstance().readSafeUTF(in);
		this.fromWc = ExternalizableUtil.getInstance().readSafeUTF(in);
		this.fromWn = ExternalizableUtil.getInstance().readSafeUTF(in);
		this.reason = ExternalizableUtil.getInstance().readSafeUTF(in);
	}


	@Override
	public String toString() {
		return "TransferInfo{" +
				"ttc='" + ttc + '\'' +
				", tmc='" + tmc + '\'' +
				", skc='" + skc + '\'' +
				", skn='" + skn + '\'' +
				", uid='" + uid + '\'' +
				", name='" + name + '\'' +
				", fromWc='" + fromWc + '\'' +
				", fromWn='" + fromWn + '\'' +
				", reason='" + reason + '\'' +
				'}';
	}
}
