package org.demo.netty.im.fake.boot;

import java.util.Map;

import javax.security.sasl.SaslException;
import javax.security.sasl.SaslServer;

/**
 * 匿名认证服务
 * @author yuezh2
 * @date	  2022年3月14日 下午3:46:12
 */
public class AnonymousSaslServer implements SaslServer{
	
	private final static String NAME = "ANONYMOUS";
	
	private Map<String, ?> props;
	private boolean completed;
	
	
	
	/**
	 * 
	 * @param props
	 */
	public AnonymousSaslServer(Map<String, ?> props) {
		this.props = props;
		this.completed = false;
	}
	
	

	@Override
	public String getMechanismName() {
		return NAME;
	}

	@Override
	public byte[] evaluateResponse(byte[] response) throws SaslException {
		if(isComplete()) {
			throw new IllegalStateException("认证交换已经完成");
		}
		props.clear();
		throw new SaslException("ANONYMOUS: 不支持此类型用户授权");
	}

	@Override
	public boolean isComplete() {
		return completed;
	}

	@Override
	public String getAuthorizationID() {
		if(!isComplete()) {
			throw new IllegalStateException("PLAIN认证尚未完成");
		}
		return null;
	}

	
	@Override
	public byte[] unwrap(byte[] incoming, int offset, int len) throws SaslException {
		if(completed) {
            throw new IllegalStateException("PLAIN 不支持");
        } else {
            throw new IllegalStateException("PLAIN 认证未完成");
        }
	}

	/**
	 * @param outgoing
	 * @param offset
	 * @param len
	 * @return
	 * @throws SaslException
	 */
	@Override
	public byte[] wrap(byte[] outgoing, int offset, int len) throws SaslException {
		if(completed) {
            throw new IllegalStateException("PLAIN 不支持");
        } else {
            throw new IllegalStateException("PLAIN 认证未完成");
        }
	}

	/**
	 * @param propName
	 * @return
	 */
	@Override
	public Object getNegotiatedProperty(String propName) {
		if(completed) {
            throw new IllegalStateException("PLAIN 不支持");
        } else {
            throw new IllegalStateException("PLAIN 认证未完成");
        }
	}

	/**
	 * @throws SaslException
	 */
	@Override
	public void dispose() throws SaslException {
		props = null;
		completed = false;
	}

}

