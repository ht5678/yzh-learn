/**
 * 
 */
package org.demo.netty.im.fake.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInput;
import java.io.DataOutput;
import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 
 * @author yue
 *
 */
@SuppressWarnings("unchecked")
public class ClusterExternalizableUtil implements ExternalizableUtilStrategy{

	
	// serialization helpers
	public static void writeObject(DataOutput out, Object object) throws IOException{
		if (null == object) {
			out.writeByte(0);
		}
		else if (object instanceof Long) {
			out.writeByte(1);
			out.writeLong((Long) object);
		}
		else if (object instanceof Integer) {
			out.writeByte(2);
			out.writeInt((Integer) object);
		}
		else if (object instanceof String) {
			out.writeByte(3);
			out.writeUTF((String)object);
		}
		else if (object instanceof Double) {
			out.writeByte(4);
			out.writeDouble((Double) object);
		}
		else if (object instanceof Float) {
			out.writeByte(5);
			out.writeFloat((Float) object);
		}
		else if (object instanceof Boolean) {
			out.writeByte(6);
			out.writeBoolean((Boolean) object);
		}
		else if (object instanceof Date) {
			out.writeByte(7);
			out.writeLong(((Date) object).getTime());
		}
		else if (object instanceof byte[]) {
			out.writeByte(8);
			out.writeInt(((byte[])object).length);
			out.write((byte[])object);
		}
		else {
			out.writeByte(9);
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			ObjectOutputStream oos = new ObjectOutputStream(bos);
			oos.writeObject(object);
			oos.close();
			byte [] buf = bos.toByteArray();
			out.writeInt(buf.length);
			out.write(buf);
		}
	}
	
	public static Object readObject(DataInput in) throws IOException{
		byte type = in.readByte();
		if (type == 0) {
			return null;
		}
		else if (type == 1) {
			return in.readLong();
		}
		else if (type == 2) {
			return in.readInt();
		} 
		else if (type == 3) {
			return in.readUTF();
		}
		else if (type == 4) {
			return in.readDouble();
		}
		else if (type == 5) {
			return in.readFloat();
		}
		else if (type == 6) {
			return in.readBoolean();
		}
		else if (type == 7) {
			return new Date(in.readLong());
		}
		else if (type == 8) {
			byte[] buf = new byte[in.readInt()];
			in.readFully(buf);
			return buf;
		}
		else if (type == 9) {
			int len = in.readInt();
			byte[] buf = new byte[len];
			in.readFully(buf);
			ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(buf));
			try {
				return ois.readObject();
			} catch (ClassNotFoundException e) {
				throw new IOException(e);
			} finally {
				ois.close();
			}
		} 
		else {
            throw new IOException("未知序列换类型： " + type);
        }
	}
	
	@Override
	public void writeStringMap(DataOutput out, Map<String, String> stringMap) throws IOException {
		writeObject(out, stringMap);
	}

	/**
	 * @param in
	 * @return
	 * @throws IOException
	 */
	@Override
	public Map<String, String> readStringMap(DataInput in) throws IOException {
		return (Map<String, String>)readObject(in);
	}

	/**
	 * @param out
	 * @param integerMap
	 * @throws IOException
	 */
	@Override
	public void writeLongIntMap(DataOutput out, Map<String, Integer> integerMap) throws IOException {
		writeObject(out, integerMap);
	}

	/**
	 * @param in
	 * @return
	 * @throws IOException
	 */
	@Override
	public Map<String, Integer> readLongIntMap(DataInput in) throws IOException {
		return (Map<String, Integer>)readObject(in);
	}

	/**
	 * @param out
	 * @param stringList
	 * @throws IOException
	 */
	@Override
	public void writeStringList(DataOutput out, List<String> stringList) throws IOException {
		writeObject(out, stringList);
	}

	/**
	 * @param in
	 * @return
	 * @throws IOException
	 */
	@Override
	public List<String> readStringList(DataInput in) throws IOException {
		return (List<String>)readObject(in);
	}

	/**
	 * @param out
	 * @param array
	 * @throws IOException
	 */
	@Override
	public void writeLongArray(DataOutput out, long[] array) throws IOException {
		writeObject(out, array);
	}

	/**
	 * @param in
	 * @return
	 * @throws IOException
	 */
	@Override
	public long[] readLongArray(DataInput in) throws IOException {
		return (long[])readObject(in);
	}

	/**
	 * @param out
	 * @param value
	 * @throws IOException
	 */
	@Override
	public void writeInt(DataOutput out, int value) throws IOException {
		writeObject(out, value);
	}

	/**
	 * @param in
	 * @return
	 * @throws IOException
	 */
	@Override
	public int readInt(DataInput in) throws IOException {
		return (int)readObject(in);
	}

	/**
	 * @param out
	 * @param value
	 * @throws IOException
	 */
	@Override
	public void writeLong(DataOutput out, long value) throws IOException {
		writeObject(out, value);
	}

	/**
	 * @param in
	 * @return
	 * @throws IOException
	 */
	@Override
	public long readLong(DataInput in) throws IOException {
		return (long)readObject(in);
	}

	/**
	 * @param out
	 * @param value
	 * @throws IOException
	 */
	@Override
	public void writeBoolean(DataOutput out, boolean value) throws IOException {
		writeObject(out, value);
	}

	/**
	 * @param in
	 * @return
	 * @throws IOException
	 */
	@Override
	public boolean readBoolean(DataInput in) throws IOException {
		return (boolean)readObject(in);
	}

	/**
	 * @param out
	 * @param value
	 * @throws IOException
	 */
	@Override
	public void writeByteArray(DataOutput out, byte[] value) throws IOException {
		writeObject(out, value);
	}

	/**
	 * @param in
	 * @return
	 * @throws IOException
	 */
	@Override
	public byte[] readByteArray(DataInput in) throws IOException {
		return (byte[])readObject(in);
	}

	/**
	 * @param out
	 * @param value
	 * @throws IOException
	 */
	@Override
	public void writeSerializable(DataOutput out, Serializable value) throws IOException {
		writeObject(out, value);
	}

	/**
	 * @param in
	 * @return
	 * @throws IOException
	 */
	@Override
	public Serializable readSerializable(DataInput in) throws IOException {
		return (Serializable)readObject(in);
	}

	/**
	 * @param out
	 * @param value
	 * @throws IOException
	 */
	@Override
	public void writeSafeUTF(DataOutput out, String value) throws IOException {
		writeObject(out, value);
	}

	/**
	 * @param in
	 * @return
	 * @throws IOException
	 */
	@Override
	public String readSafeUTF(DataInput in) throws IOException {
		return (String)readObject(in);
	}

	/**
	 * @param out
	 * @param value
	 * @throws IOException
	 */
	@Override
	public void writeExternalizableCollection(DataOutput out, Collection<? extends Externalizable> value)
			throws IOException {
		writeObject(out, value);
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
		
		Collection<Externalizable> collections = (Collection<Externalizable> )readObject(in);
		((Collection<Externalizable>)value).addAll(collections);
		return collections.size();
	}

	/**
	 * @param out
	 * @param value
	 * @throws IOException
	 */
	@Override
	public void writeSerialiazbleCollection(DataOutput out, Collection<? extends Serializable> value)
			throws IOException {
		writeObject(out, value);
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
		Collection<Serializable> collections = (Collection<Serializable>)readObject(in);
		((Collection<Serializable>)value).addAll(collections);
		return collections.size();
	}

}
