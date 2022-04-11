package org.demo.netty.dispatcher.cache.pojo;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import org.demo.netty.util.ExternalizableUtil;

/**
 * 
 * @author yuezh2
 * @date	  2022年4月11日 下午5:17:46
 */
public class ConciseWaiter implements Externalizable{
	
	private String waiterCode;
	private String teamCode;
	private Long score;

	public ConciseWaiter() {}

	public ConciseWaiter(String waiterCode, String teamCode) {
		this.waiterCode = waiterCode;
		this.teamCode = teamCode;
		this.score = System.currentTimeMillis();
	}
	
	public ConciseWaiter(String waiterCode, String teamCode, Long score) {
		this.waiterCode = waiterCode;
		this.teamCode = teamCode;
		this.score = score;
	}

	public String getWaiterCode() {
		return waiterCode;
	}

	public void setWaiterCode(String waiterCode) {
		this.waiterCode = waiterCode;
	}

	public String getTeamCode() {
		return teamCode;
	}

	public void setTeamCode(String teamCode) {
		this.teamCode = teamCode;
	}

	public Long getScore() {
		return score;
	}

	public void setScore(Long score) {
		this.score = score;
	}

	@Override
	public void writeExternal(ObjectOutput out) throws IOException {
		ExternalizableUtil.getInstance().writeSafeUTF(out, waiterCode);
		ExternalizableUtil.getInstance().writeSafeUTF(out, teamCode);
		ExternalizableUtil.getInstance().writeLong(out, score);
	}

	@Override
	public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
		waiterCode = ExternalizableUtil.getInstance().readSafeUTF(in);
		teamCode = ExternalizableUtil.getInstance().readSafeUTF(in);
		score = ExternalizableUtil.getInstance().readLong(in);
	}

	@Override
	public String toString() {
		return "ConciseWaiter{" +
				"waiterCode='" + waiterCode + '\'' +
				", teamCode='" + teamCode + '\'' +
				", score=" + score +
				'}';
	}
}
