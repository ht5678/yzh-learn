package org.demo.netty.im.fake.scheduler;

/**
 * 
 * @author yuezh2
 * @date	  2022年3月29日 下午6:12:15
 */
public class SchedulerKey {

	public enum SchedulerType {POLL_TIMEOUT, PING_TIMEOUT, TIMEOUT_TIP, TIMEOUT_CLOSE}
	
	private final SchedulerType type;
	private Object uid;
	
	public SchedulerKey(SchedulerType type, Object uid) {
		this.type = type;
		this.uid = uid;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (uid == null ? 0 : uid.hashCode());
		result = prime * result + (type == null ? 0 : type.hashCode());
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		SchedulerKey other = (SchedulerKey)obj;
		if (uid == null) {
			if (other.uid != null) {
				return false;
			}
		} else if (!uid.equals(other.uid)) {
			return false;
		}
		if (type != other.type) {
			return false;
		}
		return true;
	}
}
