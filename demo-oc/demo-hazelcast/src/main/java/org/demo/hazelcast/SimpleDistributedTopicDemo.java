package org.demo.hazelcast;

import com.hazelcast.config.Config;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.ITopic;
import com.hazelcast.core.Message;
import com.hazelcast.core.MessageListener;

/**
 * 
 * @author yuezh2
 * @date	  2022年3月11日 下午3:15:24
 */
public class SimpleDistributedTopicDemo implements MessageListener<String>{

	public static void main(String[] args) {
		Config config = new Config();
		
		HazelcastInstance hc1 = Hazelcast.newHazelcastInstance(config);
		
	    ITopic<String> topic = hc1.getTopic("test-topic");
	    topic.addMessageListener(new SimpleDistributedTopicDemo());
	    topic.publish("hello hazelcast topic test");
		
		hc1.shutdown();
	}
	
	
	

	@Override
	public void onMessage(Message<String> message) {
		System.out.println("got message : " + message.getMessageObject());
	}
	
}
