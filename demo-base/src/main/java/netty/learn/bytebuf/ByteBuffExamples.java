package netty.learn.bytebuf;

import java.nio.charset.Charset;
import java.util.Random;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.util.ByteProcessor;

/**
 * 
 * @author yuezh2
 *
 * @date 2020年3月15日 下午4:39:58  
 *
 */
public class ByteBuffExamples {

	private final static Random random = new Random();
	private static final ByteBuf BYTE_BUF_FROM_SOMEWHERE = Unpooled.buffer(1024);
	
	
	public static void main(String[] args) {
//		byteBufSetGet();//readerIndex   writerIndex
//		byteBufWriteRead();
//		writeAndRead();
		byteProcessor();//find char '\r'
		byteBufSlice();
	}
	
	
	
	public static void byteBufSetGet() {
		Charset utf8 = Charset.forName("UTF-8");
		ByteBuf buf = Unpooled.copiedBuffer("Netty in Action rocks!" , utf8);
		
		System.out.println((char)buf.getByte(0));
		int readerIndex = buf.readerIndex();
		int writerIndex = buf.writerIndex();
		System.out.println("readerIndex="+readerIndex+";writerIndex="+writerIndex);
		
		
		buf.setByte(0, (byte)'B');
		System.out.println((char)buf.getByte(0));
		readerIndex = buf.readerIndex();
		writerIndex = buf.writerIndex();
		System.out.println("readerIndex = "+buf.readerIndex() +" ; writerIndex = "+writerIndex);
		
	}
	
	
	public static void byteBufWriteRead() {
		Charset utf8 = Charset.forName("UTF-8");
		ByteBuf buf = Unpooled.copiedBuffer("Netty in Action rocks!" , utf8);
		
		System.out.println((char)buf.readByte());
		int readerIndex = buf.readerIndex();
		int writerIndex = buf.writerIndex();
		System.out.println("readerIndex="+readerIndex+";writerIndex="+writerIndex);
		
		buf.writeByte((byte)'?');
		readerIndex = buf.readerIndex();
		writerIndex = buf.writerIndex();
		System.out.println("readerIndex="+readerIndex+";writerIndex="+writerIndex);
		
		buf.readByte();
		readerIndex = buf.readerIndex();
		writerIndex = buf.writerIndex();
		System.out.println("readerIndex="+readerIndex+";writerIndex="+writerIndex);
	}
	
	
	
	public static void writeAndRead() {
		ByteBuf buffer = Unpooled.buffer(20);	//get reference from somewhere
		int i =0;
		while(buffer.writableBytes()>=4) {
			buffer.writeInt(i++);
		}
		
		while(buffer.isReadable()) {
			System.out.println(buffer.readInt());
		}
	}
	
	
	public static void byteProcessor() {
		Charset utf8 = Charset.forName("UTF-8");
		ByteBuf buffer = Unpooled.copiedBuffer("Netty\r in Action rocks!",utf8);
		
		int index = buffer.forEachByte(ByteProcessor.FIND_CR);//查找\r
		System.out.println(index);
	}
	
	
	public static void byteBufSlice() {
		Charset utf8 = Charset.forName("UTF-8");
		ByteBuf buf = Unpooled.copiedBuffer("Netty in Action rocks!" , utf8);
		
		ByteBuf sliced = buf.slice(0, 15);
		System.out.println(sliced.toString(utf8));
		
		sliced.setByte(0, (byte)'J');
		System.out.println(sliced.toString(utf8));
		
	}
	
}
