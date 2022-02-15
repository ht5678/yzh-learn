package org.dfs.client;

import java.io.ByteArrayOutputStream;

/**
 * 
 * @author yue
 *
 */
public class TestBufferSize {

	public static void main(String[] args) throws Exception{
		ByteArrayOutputStream buffer = new ByteArrayOutputStream(1024 * 2);
		
		System.out.println(buffer.size());
		buffer.write("test1".getBytes());
		System.out.println(buffer.size());
		buffer.write("test13333".getBytes());
		System.out.println(buffer.size());
		
		buffer.flush();
		buffer.close();
	}
	
}
