package org.demo.netty.im.bs.message;

import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @author yuezh2
 * @date	  2022年3月29日 下午9:53:10
 */
public class PollErrorMessage implements Message{

	private final Map<String, Object> data;

	public PollErrorMessage() {
		this.data = new HashMap<>();
	}
	
    public PollErrorMessage(Map<String, Object> data) {
        this.data = data;
    }

    public Map<String, Object> getData() {
        return data;
    }
    
    public void setAttr(String key, Object value) {
    	data.put(key, value);
    }
    
    public void removeAttr(String key) {
    	data.remove(key);
    }
}
