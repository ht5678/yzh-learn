package org.demo.netty.boot;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.StringTokenizer;

import javax.security.sasl.Sasl;
import javax.security.sasl.SaslException;
import javax.security.sasl.SaslServer;

import org.demo.netty.domain.Identity;
import org.demo.netty.domain.Packet;
import org.demo.netty.domain.Waiter;

import io.netty.channel.ChannelHandlerContext;

/**
 * 
 * @author yuezh2
 * @date	  2022年3月14日 下午3:53:44
 */
public class PlainSaslServer implements SaslServer{
	
	private final String NAME = "PLAIN";
	
	private String userName;
	private String passWord;
	private String status;
	private boolean completed;
	private boolean aborted;
	private Map<String, ?> props;
	
	
	
	
	public PlainSaslServer(Map<String, ?> props) {
		this.completed = false;
		this.props = props;
	}
	
	

	@Override
	public String getMechanismName() {
		return NAME;
	}

	@Override
	public byte[] evaluateResponse(byte[] response) throws SaslException {
		if(completed) {
			throw new IllegalStateException("PLAIN认证已经完成");
		}
		
		if(aborted) {
			throw new IllegalStateException("PLAIN由于错误导致认证被打断");
		}
		
		Packet packet = (Packet)props.get(Packet.class.getCanonicalName());
		ChannelHandlerContext ctx = (ChannelHandlerContext)props.get(ChannelHandlerContext.class.getCanonicalName());
		
		if(null == ctx || null == packet) {
			throw new SaslException("PLAIN : 不支持此类型用户授权");
		}
		
		
		try {
			if(response.length>0) {
				String data = new String(response , StandardCharsets.UTF_8);
				StringTokenizer tokens = new StringTokenizer(data , " ");
				if(tokens.countTokens() == 3) {
					userName = tokens.nextToken();
					passWord = tokens.nextToken();
					status = tokens.nextToken();
				}else {
					throw new SaslException("缺失认证参数");
				}
				//
				if(packet.getFrom().getIdy() == Identity.WAITER) {
					Waiter waiter = WaiterProvider.getInst().authentication(userName, passWord);
					waiter.setStatus(status);
					ctx.channel().attr(SASLAuthentication.WAITER).set(waiter);
					this.completed = true;
				}else {
					throw new SaslException("PLAIN: 不支持此类型用户授权");
				}
			}else {
				throw new SaslException("PLAIN当前为空,期望一个response");
			}
		}catch(IOException e) {
			aborted = true;
			throw new SaslException("PLAIN用户认证失败,失败用户为:"+userName , e);
		}
		
		return null;
	}

	@Override
	public boolean isComplete() {
		return completed;
	}

	@Override
	public String getAuthorizationID() {
		if(completed) {
            return userName;
        } else {
            throw new IllegalStateException("PLAIN 认证尚未完成。");
        }
	}

	@Override
	public byte[] unwrap(byte[] incoming, int offset, int len) throws SaslException {
		if(completed) {
            throw new IllegalStateException("PLAIN 不支持");
        } else {
            throw new IllegalStateException("PLAIN 认证未完成");
        }
	}

	@Override
	public byte[] wrap(byte[] outgoing, int offset, int len) throws SaslException {
		if(completed) {
            throw new IllegalStateException("PLAIN 不支持");
        } else {
            throw new IllegalStateException("PLAIN 认证未完成");
        }
	}

	@Override
	public Object getNegotiatedProperty(String propName) {
		if (completed) {
            if (propName.equals(Sasl.QOP)) {
                return "auth";
            } else {
                return null;
            }
        } else {
            throw new IllegalStateException("PLAIN 认证未完成");
        }
	}

	@Override
	public void dispose() throws SaslException {
		passWord = null;
        userName = null;
        completed = false;
	}

}
