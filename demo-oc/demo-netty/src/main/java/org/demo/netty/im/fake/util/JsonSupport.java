/**
 * 
 */
package org.demo.netty.im.fake.util;

import java.io.IOException;

import io.netty.buffer.ByteBufInputStream;
import io.netty.buffer.ByteBufOutputStream;

/**
 * 
 * @author yue
 *
 */
public interface JsonSupport {
	
	<T> T read(ByteBufInputStream is, Class<T> clazz) throws IOException;
	
	void write(ByteBufOutputStream out, Object value) throws IOException;
	
	String writeString(Object obj) throws IOException;
	
	byte[] writeBytes(Object obj) throws IOException;
	
	Object read(Object o) throws IOException;
	
	<T> T readClass(Object o, Class<T> clazz) throws IOException;
	
	<T> T readClasses(Object o, Object ref) throws IOException;
}
