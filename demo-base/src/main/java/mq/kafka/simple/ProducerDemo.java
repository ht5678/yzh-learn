package mq.kafka.simple;

import java.util.Date;
import java.util.Properties;
import java.util.Random;

import kafka.javaapi.producer.Producer;
import kafka.producer.KeyedMessage;
import kafka.producer.ProducerConfig;



/**
 * 
 * @author yuezhihua
 *
 */
public class ProducerDemo {
    public static void main(String[] args) {
        Random rnd = new Random();
        int events=100;
 
        // 设置配置属性
        Properties props = new Properties();
        //这里即使是写一台机器的地址也是可以的，因为可以通过一台机器来获取所有的机器地址
        props.put("metadata.broker.list","10.99.205.22:9092,10.99.205.18:9092,10.99.205.17:9092");
//        props.put("metadata.broker.list","10.250.1.11:9001,10.250.1.12:9001,10.250.1.13:9001");
        props.put("serializer.class", "kafka.serializer.StringEncoder");
        // key.serializer.class默认为serializer.class
        props.put("key.serializer.class", "kafka.serializer.StringEncoder");
//        // 可选配置，如果不配置，则使用默认的partitioner
//        props.put("partitioner.class", "messagequeue.kafka.simple.PartitionerDemo");
        // 触发acknowledgement机制，否则是fire and forget，可能会引起数据丢失
        // 值为0,1,-1,可以参考
        // http://kafka.apache.org/08/configuration.html
        props.put("request.required.acks", "1");
        ProducerConfig config = new ProducerConfig(props);
 
        // 创建producer
        Producer<String, String> producer = new Producer<String, String>(config);
        // 产生并发送消息
        long start=System.currentTimeMillis();
        for (long i = 0; i < events; i++) {
            long runtime = new Date().getTime();
            String ip = "192.168.2." + i;//rnd.nextInt(255);
            String msg = runtime + ",www.example.com你好啊世界," + ip;
            //如果topic不存在，则会自动创建，默认replication-factor为1，partitions为0
            KeyedMessage<String, String> data = new KeyedMessage<String, String>(
                    "test", ip, msg);
            producer.send(data);
        }
        System.out.println("耗时:" + (System.currentTimeMillis() - start));
        // 关闭producer
        producer.close();
    }
}