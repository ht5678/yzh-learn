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
public class ExternalizableUtil {
	
	private ExternalizableUtilStrategy strategy;
	
	private ExternalizableUtil() {}
	
	private static class Single{
		private final static ExternalizableUtil instance = new ExternalizableUtil();
	}
	
	public static ExternalizableUtil getInstance() {
		return Single.instance;
	}
	
	public void setStrategy(ExternalizableUtilStrategy strategy) {
		this.strategy = strategy;
	}
	
	public ExternalizableUtilStrategy getStrategy() {
		return strategy;
	}

	/**
	 * @param out
	 * @param stringMap
	 * @throws IOException
	 */
	public void writeStringMap(DataOutput out, Map<String, String> stringMap) throws IOException {
		strategy.writeStringMap(out, stringMap);
	}

	/**
	 * @param in
	 * @return
	 * @throws IOException
	 */
	public Map<String, String> readStringMap(DataInput in) throws IOException {
		return strategy.readStringMap(in);
	}

	/**
	 * @param out
	 * @param integerMap
	 * @throws IOException
	 */
	public void writeLongIntMap(DataOutput out, Map<String, Integer> integerMap) throws IOException {
		strategy.writeLongIntMap(out, integerMap);
	}

	/**
	 * @param in
	 * @return
	 * @throws IOException
	 */
	public Map<String, Integer> readLongIntMap(DataInput in) throws IOException {
		return strategy.readLongIntMap(in);
	}

	/**
	 * @param out
	 * @param stringList
	 * @throws IOException
	 */
	public void writeStringList(DataOutput out, List<String> stringList) throws IOException {
		strategy.writeStringList(out, stringList);
	}

	/**
	 * @param in
	 * @return
	 * @throws IOException
	 */
	public List<String> readStringList(DataInput in) throws IOException {
		return strategy.readStringList(in);
	}

	/**
	 * @param out
	 * @param array
	 * @throws IOException
	 */
	public void writeLongArray(DataOutput out, long[] array) throws IOException {
		strategy.writeLongArray(out, array);
	}

	/**
	 * @param in
	 * @return
	 * @throws IOException
	 */
	public long[] readLongArray(DataInput in) throws IOException {
		return strategy.readLongArray(in);
	}

	/**
	 * @param out
	 * @param value
	 * @throws IOException
	 */
	public void writeInt(DataOutput out, int value) throws IOException {
		strategy.writeInt(out, value);
	}

	/**
	 * @param in
	 * @return
	 * @throws IOException
	 */
	public int readInt(DataInput in) throws IOException {
		return strategy.readInt(in);
	}

	/**
	 * @param out
	 * @param value
	 * @throws IOException
	 */
	public void writeLong(DataOutput out, long value) throws IOException {
		strategy.writeLong(out, value);
	}

	/**
	 * @param in
	 * @return
	 * @throws IOException
	 */
	public long readLong(DataInput in) throws IOException {
		return strategy.readLong(in);
	}

	/**
	 * @param out
	 * @param value
	 * @throws IOException
	 */
	public void writeBoolean(DataOutput out, boolean value) throws IOException {
		strategy.writeBoolean(out, value);
	}

	/**
	 * @param in
	 * @return
	 * @throws IOException
	 */
	public boolean readBoolean(DataInput in) throws IOException {
		return strategy.readBoolean(in);
	}

	/**
	 * @param out
	 * @param value
	 * @throws IOException
	 */
	public void writeByteArray(DataOutput out, byte[] value) throws IOException {
		strategy.writeByteArray(out, value);
	}

	/**
	 * @param in
	 * @return
	 * @throws IOException
	 */
	public byte[] readByteArray(DataInput in) throws IOException {
		return strategy.readByteArray(in);
	}

	/**
	 * @param out
	 * @param value
	 * @throws IOException
	 */
	public void writeSerializable(DataOutput out, Serializable value) throws IOException {
		strategy.writeSerializable(out, value);
	}

	/**
	 * @param in
	 * @return
	 * @throws IOException
	 */
	public Serializable readSerializable(DataInput in) throws IOException {
		return strategy.readSerializable(in);
	}

	/**
	 * @param out
	 * @param value
	 * @throws IOException
	 */
	public void writeSafeUTF(DataOutput out, String value) throws IOException {
		strategy.writeSafeUTF(out, value);
	}

	/**
	 * @param in
	 * @return
	 * @throws IOException
	 */
	public String readSafeUTF(DataInput in) throws IOException {
		return strategy.readSafeUTF(in);
	}

	/**
	 * @param out
	 * @param value
	 * @throws IOException
	 */
	public void writeExternalizableCollection(DataOutput out, Collection<? extends Externalizable> value)
			throws IOException {
		strategy.writeExternalizableCollection(out, value);
	}

	/**
	 * @param in
	 * @param value
	 * @param classLoader
	 * @return
	 * @throws IOException
	 */
	public int readExternalizableCollection(DataInput in, Collection<? extends Externalizable> value,
			ClassLoader classLoader) throws IOException {
		return strategy.readExternalizableCollection(in, value, classLoader);
	}

	/**
	 * @param out
	 * @param value
	 * @throws IOException
	 */
	public void writeSerialiazbleCollection(DataOutput out, Collection<? extends Serializable> value)
			throws IOException {
		strategy.writeSerialiazbleCollection(out, value);
	}

	/**
	 * @param in
	 * @param value
	 * @param classLoader
	 * @return
	 * @throws IOException
	 */
	public int readSerializableCollection(DataInput in, Collection<? extends Serializable> value,
			ClassLoader classLoader) throws IOException {
		return strategy.readSerializableCollection(in, value, classLoader);
	}
}
