package netty.io.demo.advanced.portunification;

import java.util.List;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.compression.ZlibCodecFactory;
import io.netty.handler.codec.compression.ZlibWrapper;
import io.netty.handler.codec.http.HttpContentCompressor;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslHandler;
import netty.io.demo.binaryprotocols.factorial.BigIntegerDecoder;
import netty.io.demo.binaryprotocols.factorial.FactorialServerHandler;
import netty.io.demo.binaryprotocols.factorial.NumberEncoder;
import netty.io.demo.http.snoop.HttpSnoopServerHandler;

/**
 * 
 * Manipulates the current pipeline dynamically to switch protocols or enable
 * SSL or GZIP.
 * 
 * 
 * @author yuezh2   2016年11月16日 下午4:35:19
 *
 */
public class PortUnificationServerHandler extends ByteToMessageDecoder{

	
	private final SslContext sslCtx;
	private final boolean detectSsl;
	private final boolean detectGzip;
	
	
	
	
	public PortUnificationServerHandler(SslContext sslCtx){
		this(sslCtx, true, true);
	}
	
	
	
	
	public PortUnificationServerHandler(SslContext sslCtx , boolean detectSsl , boolean detectGzip){
		this.sslCtx = sslCtx;
		this.detectGzip = detectGzip;
		this.detectSsl = detectSsl;
	}
	
	
	
	
	
	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
		//will use the first 5 bytes to detect protocol
		if(in.readableBytes()<5){
			return;
		}
		
		if(isSsl(in)){
			enableSsl(ctx);
		}else{
			final int magic1 = in.getUnsignedByte(in.readerIndex());
			final int magic2 = in.getUnsignedByte(in.readerIndex() + 1);
			if(isGzip(magic1, magic2)){
				enableGzip(ctx);
			}else if(isHttp(magic1, magic2)){
				switchToHttp(ctx);
			}else if(isFactorial(magic1)){
				switchToFactorial(ctx);
			}else{
				//unknow protocol , discard everything and close the connection
				in.clear();
				ctx.close();
			}
		}
		
	}

	
	
	
	private boolean isSsl(ByteBuf buf){
		if(detectSsl){
			return SslHandler.isEncrypted(buf);
		}
		return false;
	}
	
	
	
	private boolean isGzip(int magic1 , int magic2){
		if(detectGzip){
			return magic1 ==31 && magic2 ==139;
		}
		return false;
	}
	
	
	private static boolean isHttp(int magic1 , int magic2){
		return
	            magic1 == 'G' && magic2 == 'E' || // GET
	            magic1 == 'P' && magic2 == 'O' || // POST
	            magic1 == 'P' && magic2 == 'U' || // PUT
	            magic1 == 'H' && magic2 == 'E' || // HEAD
	            magic1 == 'O' && magic2 == 'P' || // OPTIONS
	            magic1 == 'P' && magic2 == 'A' || // PATCH
	            magic1 == 'D' && magic2 == 'E' || // DELETE
	            magic1 == 'T' && magic2 == 'R' || // TRACE
	            magic1 == 'C' && magic2 == 'O';   // CONNECT
	}
	
	
	private static boolean isFactorial(int magic1){
		return magic1 == 'F';
	}
	
	
	
	
	
	
	
	
	
	
	
	private void enableGzip(ChannelHandlerContext ctx){
		ChannelPipeline p = ctx.pipeline();
		p.addLast("gzipdeflater" , ZlibCodecFactory.newZlibEncoder(ZlibWrapper.GZIP));
		p.addLast("gzipinflater" , ZlibCodecFactory.newZlibDecoder(ZlibWrapper.GZIP));
		p.addLast("unificationB" , new PortUnificationServerHandler(sslCtx, detectSsl, false));
		p.remove(this);
	}
	
	
	
	private void enableSsl(ChannelHandlerContext ctx){
		ChannelPipeline p = ctx.pipeline();
		p.addLast("ssl" , sslCtx.newHandler(ctx.alloc()));
		p.addLast("unificationA" , new PortUnificationServerHandler(sslCtx , false , detectGzip));
		p.remove(this);
	}
	
	
	private void switchToHttp(ChannelHandlerContext ctx){
		ChannelPipeline p = ctx.pipeline();
		p.addLast("decoder" , new HttpRequestDecoder());
		p.addLast("encoder", new HttpResponseEncoder());
		p.addLast("deflater" , new HttpContentCompressor());
		p.addLast("handler" , new HttpSnoopServerHandler());
		p.remove(this);
	}

	
	private void switchToFactorial(ChannelHandlerContext ctx){
		ChannelPipeline p = ctx.pipeline();
		p.addLast("decoder" , new BigIntegerDecoder());
		p.addLast("encoder" , new NumberEncoder());
		p.addLast("handler" , new FactorialServerHandler());
		p.remove(this);
	}
	
}
