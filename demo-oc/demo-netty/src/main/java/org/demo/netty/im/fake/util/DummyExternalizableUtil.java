package org.demo.netty.im.fake.util;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.Externalizable;
import java.io.IOException;
import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * 
 * @author yuezh2
 * @date	  2022年4月11日 下午4:30:43
 */
public class DummyExternalizableUtil implements ExternalizableUtilStrategy{

	/**
	 * @param out
	 * @param stringMap
	 * @throws IOException
	 */
	@Override
	public void writeStringMap(DataOutput out, Map<String, String> stringMap) throws IOException {
		
	}

	/**
	 * @param in
	 * @return
	 * @throws IOException
	 */
	@Override
	public Map<String, String> readStringMap(DataInput in) throws IOException {
		return Collections.emptyMap();
	}

	/**
	 * @param out
	 * @param integerMap
	 * @throws IOException
	 */
	@Override
	public void writeLongIntMap(DataOutput out, Map<String, Integer> integerMap) throws IOException {
		
	}

	/**
	 * @param in
	 * @return
	 * @throws IOException
	 */
	@Override
	public Map<String, Integer> readLongIntMap(DataInput in) throws IOException {
		return Collections.emptyMap();
	}

	/**
	 * @param out
	 * @param stringList
	 * @throws IOException
	 */
	@Override
	public void writeStringList(DataOutput out, List<String> stringList) throws IOException {
		
	}

	/**
	 * @param in
	 * @return
	 * @throws IOException
	 */
	@Override
	public List<String> readStringList(DataInput in) throws IOException {
		return Collections.emptyList();
	}

	/**
	 * @param out
	 * @param array
	 * @throws IOException
	 */
	@Override
	public void writeLongArray(DataOutput out, long[] array) throws IOException {
		
	}

	/**
	 * @param in
	 * @return
	 * @throws IOException
	 */
	@Override
	public long[] readLongArray(DataInput in) throws IOException {
		return new long[] {};
	}

	/**
	 * @param out
	 * @param value
	 * @throws IOException
	 */
	@Override
	public void writeInt(DataOutput out, int value) throws IOException {
		
	}

	/**
	 * @param in
	 * @return
	 * @throws IOException
	 */
	@Override
	public int readInt(DataInput in) throws IOException {
		return 0;
	}

	/**
	 * @param out
	 * @param value
	 * @throws IOException
	 */
	@Override
	public void writeLong(DataOutput out, long value) throws IOException {
		
	}

	/**
	 * @param in
	 * @return
	 * @throws IOException
	 */
	@Override
	public long readLong(DataInput in) throws IOException {
		return 0;
	}

	/**
	 * @param out
	 * @param value
	 * @throws IOException
	 */
	@Override
	public void writeBoolean(DataOutput out, boolean value) throws IOException {
	}

	/**
	 * @param in
	 * @return
	 * @throws IOException
	 */
	@Override
	public boolean readBoolean(DataInput in) throws IOException {
		return false;
	}

	/**
	 * @param out
	 * @param value
	 * @throws IOException
	 */
	@Override
	public void writeByteArray(DataOutput out, byte[] value) throws IOException {
	}

	/**
	 * @param in
	 * @return
	 * @throws IOException
	 */
	@Override
	public byte[] readByteArray(DataInput in) throws IOException {
		return new byte[] {};
	}

	/**
	 * @param out
	 * @param value
	 * @throws IOException
	 */
	@Override
	public void writeSerializable(DataOutput out, Serializable value) throws IOException {
		
	}

	/**
	 * @param in
	 * @return
	 * @throws IOException
	 */
	@Override
	public Serializable readSerializable(DataInput in) throws IOException {
		return null;
	}

	/**
	 * @param out
	 * @param value
	 * @throws IOException
	 */
	@Override
	public void writeSafeUTF(DataOutput out, String value) throws IOException {
		
	}

	/**
	 * @param in
	 * @return
	 * @throws IOException
	 */
	@Override
	public String readSafeUTF(DataInput in) throws IOException {
		return "";
	}

	/**
	 * @param out
	 * @param value
	 * @throws IOException
	 */
	@Override
	public void writeExternalizableCollection(DataOutput out, Collection<? extends Externalizable> value)
			throws IOException {
		
	}

	/**
	 * @param in
	 * @param value
	 * @param classLoader
	 * @return
	 * @throws IOException
	 */
	@Override
	public int readExternalizableCollection(DataInput in, Collection<? extends Externalizable> value,
			ClassLoader classLoader) throws IOException {
		return 0;
	}

	/**
	 * @param out
	 * @param value
	 * @throws IOException
	 */
	@Override
	public void writeSerialiazbleCollection(DataOutput out, Collection<? extends Serializable> value)
			throws IOException {
	}

	/**
	 * @param in
	 * @param value
	 * @param classLoader
	 * @return
	 * @throws IOException
	 */
	@Override
	public int readSerializableCollection(DataInput in, Collection<? extends Serializable> value,
			ClassLoader classLoader) throws IOException {
		return 0;
	}

}
