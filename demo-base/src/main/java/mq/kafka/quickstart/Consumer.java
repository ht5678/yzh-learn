package mq.kafka.quickstart;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import kafka.consumer.ConsumerConfig;
import kafka.consumer.ConsumerIterator;
import kafka.consumer.KafkaStream;
import kafka.javaapi.consumer.ConsumerConnector;

/**
 * 
 * @author yuezhihua
 *
 */
public class Consumer extends Thread{
	
	private final ConsumerConnector consumer;
	
	private final String topic;
	
	
	
	/**
	 * 初始化配置
	 * @param topic
	 */
	public Consumer(String topic){
		consumer = kafka.consumer.Consumer.createJavaConsumerConnector(createConsumerConfig());
		this.topic = topic;
	}
	
	
	
	/**
	 * 创建配置
	 * @return
	 */
	private static ConsumerConfig createConsumerConfig(){
		Properties props = new Properties();
		props.put("zookeeper.connect", KafkaProperties.zkConnect);
		props.put("group.id", KafkaProperties.groupId);
		props.put("zookeeper.session.timeout.ms", "40000");
		props.put("zookeeper.sync.time.ms", "200");
		props.put("auto.commit.interval.ms", "1000");
		
		return new ConsumerConfig(props);
	}
	
	
	
	
	public void run() {
		Map<String,Integer> topicCountMap = new HashMap<>();
		topicCountMap.put(topic, new Integer(1));
		
		Map<String, List<KafkaStream<byte[], byte[]>>> consumerMap = consumer.createMessageStreams(topicCountMap);
		KafkaStream<byte[], byte[]> stream = consumerMap.get(topic).get(0);
		ConsumerIterator<byte[], byte[]> it = stream.iterator();
		
		while(it.hasNext()){
			System.out.println(new String(it.next().message()));
		}
	}
	
	
	

}
