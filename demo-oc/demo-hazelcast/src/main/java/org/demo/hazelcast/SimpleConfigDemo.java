package org.demo.hazelcast;

import com.hazelcast.config.ClasspathXmlConfig;
import com.hazelcast.config.Config;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;

/**
 * 
 * @author yuezh2
 * @date	  2022年3月11日 下午3:23:20
 */
public class SimpleConfigDemo {

	
	public static void main(String[] args) {
		
//        // 从classpath加载配置文件
//        Config config = new ClasspathXmlConfig("xmlconfig/simple-config.xml");
//        // 获取网络配置
//        NetworkConfig netConfig = config.getNetworkConfig();
//        // 获取用户定义的map配置
//        MapConfig mapConfigXml = config.getMapConfig("demo.config");
//        // 获取系统默认的map配置
//        MapConfig mapConfigDefault = config.getMapConfig("default");
//        // 输出集群监听的起始端口号
//        System.out.println("Current port:" + netConfig.getPort());
//        // 输出监听端口的累加号
//        System.out.println("Current port count:" + netConfig.getPortCount());
//        // 输出自定义map的备份副本个数
//        System.out.println("Config map backup count:" + mapConfigXml.getBackupCount());
//        // 输出默认map的备份副本个数
//        System.out.println("Default map backup count:" + mapConfigDefault.getBackupCount());
		
		Config config = new ClasspathXmlConfig("hazelcast-dev.xml");
		
		HazelcastInstance hc1 = Hazelcast.newHazelcastInstance(config);
		HazelcastInstance hc2 = Hazelcast.newHazelcastInstance(config);
		
		hc1.getMap("testcache").put("name", "zhangsan");
		System.out.println(hc1.getMap("testcache"));
		System.out.println(hc2.getMap("testcache"));
		
		hc1.shutdown();
		hc2.shutdown();
	}
	
}
