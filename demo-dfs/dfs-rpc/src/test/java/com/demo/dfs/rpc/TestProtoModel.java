package com.demo.dfs.rpc;

import com.demo.dfs.rpc.model.RegisterRequest;
import com.googlecode.protobuf.format.JsonFormat;

/**
 * 
 * @author yuezh2@lenovo.com
 *	@date 2021年12月29日下午3:07:49
 */
public class TestProtoModel {

    public static void main( String[] args )throws Exception{
    	
    	RegisterRequest rr = RegisterRequest.newBuilder().setHostname("test").build();
    	byte[] bytes = rr.toByteArray();
    	
    	//byte
    	RegisterRequest rr1 = RegisterRequest.parseFrom(bytes);
    	System.out.println(rr1.getHostname());
    	
    	//json
//    	JsonFormat format = new JsonFormat();
    	String json = JsonFormat.printToString(rr);
    	System.out.println(json);
    }
	
}
