package com.demo.dfs.rpc;

import com.demo.dfs.rpc.model.RegisterRequest;

/**
 * Hello world!
 *
 */
public class App {
    public static void main( String[] args )throws Exception{
    	
    	RegisterRequest rr = RegisterRequest.newBuilder().setHostname("test").build();
    	byte[] bytes = rr.toByteArray();
    	
    	RegisterRequest rr1 = RegisterRequest.parseFrom(bytes);
    	System.out.println(rr1.getHostname());
    }
}
