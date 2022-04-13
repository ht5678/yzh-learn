/**
 * 
 */
package org.demo.netty.im.fake.util;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.Externalizable;
import java.io.IOException;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * 
 * @author yue
 *
 */
public interface ExternalizableUtilStrategy {
	
	void writeStringMap(DataOutput out, Map<String, String> stringMap) throws IOException;
	
	Map<String, String> readStringMap(DataInput in) throws IOException;
	
	void writeLongIntMap(DataOutput out, Map<String, Integer> integerMap) throws IOException;
	
	Map<String, Integer> readLongIntMap(DataInput in) throws IOException;
	
	void writeStringList(DataOutput out, List<String> stringList) throws IOException;
	
	List<String> readStringList(DataInput in) throws IOException;
	
	void writeLongArray(DataOutput out, long []array) throws IOException;
	
	long[] readLongArray(DataInput in) throws IOException;
	
	void writeInt(DataOutput out, int value) throws IOException;
	
	int readInt(DataInput in) throws IOException;
	
	void writeLong(DataOutput out, long value) throws IOException;
	
	long readLong(DataInput in) throws IOException;
	
	void writeBoolean(DataOutput out, boolean value) throws IOException;
	
	boolean readBoolean(DataInput in) throws IOException;
	
	void writeByteArray(DataOutput out, byte[] value) throws IOException;
	
	byte[] readByteArray(DataInput in) throws IOException;
	
	void writeSerializable(DataOutput out, Serializable value) throws IOException;
	
	Serializable readSerializable(DataInput in) throws IOException;
	
	void writeSafeUTF(DataOutput out, String value) throws IOException;
	
	String readSafeUTF(DataInput in) throws IOException;
	
	void writeExternalizableCollection(DataOutput out, Collection<? extends Externalizable> value) throws IOException;
	
	int readExternalizableCollection(DataInput in, Collection<? extends Externalizable> value, ClassLoader classLoader) throws IOException;
	
	void writeSerialiazbleCollection(DataOutput out, Collection<? extends Serializable> value) throws IOException;
	
	int readSerializableCollection(DataInput in, Collection<? extends Serializable> value, ClassLoader classLoader) throws IOException;
}
