package jvm;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


/**
 * 
 * @author yuezh2
 *
 * @date 2020年2月25日 下午8:28:33  
 *
 */
public class OOMTest {
	// JVM设置    
	// -Xms10M -Xmx10M -XX:+PrintGCDetails -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=D:\jvm.dump 
	public static void main(String[] args) {
		List<Object> list = new ArrayList<>();
		int i = 0;
		int j=0;
		while (true) {
			list.add(new User(i++, UUID.randomUUID().toString()));
			new User(j--, UUID.randomUUID().toString());
		}
	}
}
