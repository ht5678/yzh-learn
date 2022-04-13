package org.demo.netty.im.fake.domain;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.Date;

import org.demo.netty.im.fake.util.DateFormat;
import org.demo.netty.im.fake.util.ExternalizableUtil;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * 
 * @author yue
 *
 */
public class Packet implements Externalizable{
	
	private String pid;
	private String cid;
	@JsonIgnore
	private String ttc;
	@JsonIgnore
	private String tmc;
	
	private PacketType type;
	private AddressFrom from;
	private AddressTo to;
	private Body body;
	private Transport ts;
	private long timestamp;
	private String datetime;
	private String ver = "1.0";
	
	
	

	@Override
	public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
		String serializableType = ExternalizableUtil.getInstance().readSafeUTF(in);
		if(null != serializableType){
			type = PacketType.valueOf(serializableType);
		}else{
			type = null;
		}
		
		String serializableTs = ExternalizableUtil.getInstance().readSafeUTF(in);
		if(null != serializableTs){
			ts = Transport.valueOf(serializableTs);
		}else{
			ts = null;
		}
		
		from = (AddressFrom)ExternalizableUtil.getInstance().readSerializable(in);
		to = (AddressTo)ExternalizableUtil.getInstance().readSerializable(in);
		timestamp = ExternalizableUtil.getInstance().readLong(in);
		pid = ExternalizableUtil.getInstance().readSafeUTF(in);
		cid = ExternalizableUtil.getInstance().readSafeUTF(in);
		datetime = ExternalizableUtil.getInstance().readSafeUTF(in);
		ver = ExternalizableUtil.getInstance().readSafeUTF(in);
		ttc = ExternalizableUtil.getInstance().readSafeUTF(in);
		tmc = ExternalizableUtil.getInstance().readSafeUTF(in);
		body = (Body)ExternalizableUtil.getInstance().readSerializable(in);
		
	}

	@Override
	public void writeExternal(ObjectOutput out) throws IOException {
		String serializableType = null;
		
		if(type != null){
			serializableType = type.name();
		}
		ExternalizableUtil.getInstance().writeSafeUTF(out, serializableType);
		String serializableTs = null;
		if(null != ts){
			serializableTs = ts.name();
		}
		ExternalizableUtil.getInstance().writeSafeUTF(out, serializableTs);
		
		ExternalizableUtil.getInstance().writeSerializable(out, from);
		ExternalizableUtil.getInstance().writeSerializable(out, to);
		ExternalizableUtil.getInstance().writeLong(out, timestamp);
		ExternalizableUtil.getInstance().writeSafeUTF(out, pid);
		ExternalizableUtil.getInstance().writeSafeUTF(out, cid);
		ExternalizableUtil.getInstance().writeSafeUTF(out, datetime);
		ExternalizableUtil.getInstance().writeSafeUTF(out, ver);
		ExternalizableUtil.getInstance().writeSafeUTF(out, ttc);
		ExternalizableUtil.getInstance().writeSafeUTF(out, tmc);
		ExternalizableUtil.getInstance().writeSerializable(out, body);
	}
	
	
	
	public Packet() {
		Date date = new Date();
		this.timestamp = date.getTime();
		this.datetime = DateFormat.getStringDate(date);
	}
	
	public Packet(PacketType type) {
		this();
		this.type = type;
	}
	
	public Packet(PacketType type, Body body) {
		this();
		this.type = type;
		this.body = body;
	}
	
	public Packet(PacketType type, AddressFrom from, Body body) {
		this();
		this.type = type;
		this.from = from;
		this.body = body;
	}
	
	public Packet(PacketType type, AddressTo to, Body body) {
		this();
		this.type = type;
		this.to = to;
		this.body = body;
	}
	
	public Packet(PacketType type, AddressFrom from, AddressTo to, Body body) {
		this();
		this.type = type;
		this.from = from;
		this.to = to;
		this.body = body;
	}
	
	

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public String getCid() {
		return cid;
	}

	public void setCid(String cid) {
		this.cid = cid;
	}

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

	public PacketType getType() {
		return type;
	}

	public void setType(PacketType type) {
		this.type = type;
	}

	public AddressFrom getFrom() {
		return from;
	}

	public void setFrom(AddressFrom from) {
		this.from = from;
	}

	public AddressTo getTo() {
		return to;
	}

	public void setTo(AddressTo to) {
		this.to = to;
	}

	public Body getBody() {
		return body;
	}

	public void setBody(Body body) {
		this.body = body;
	}

	public Transport getTs() {
		return ts;
	}

	public void setTs(Transport ts) {
		this.ts = ts;
	}

	public long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

	public String getDatetime() {
		return datetime;
	}

	public void setDatetime(String datetime) {
		this.datetime = datetime;
	}

	public String getVer() {
		return ver;
	}

	public void setVer(String ver) {
		this.ver = ver;
	}

	@Override
	public String toString() {
		return "Packet [pid=" + pid + ", cid=" + cid + ", ttc=" + ttc + ", tmc=" + tmc + ", type=" + type + ", from="
				+ from + ", to=" + to + ", body=" + body + ", ts=" + ts + ", timestamp=" + timestamp + ", datetime="
				+ datetime + ", ver=" + ver + "]";
	}

	
}
