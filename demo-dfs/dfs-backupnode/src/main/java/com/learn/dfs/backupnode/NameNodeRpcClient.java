package com.learn.dfs.backupnode;

import com.alibaba.fastjson.JSONArray;
import com.demo.dfs.rpc.model.FetchEditsLogRequest;
import com.demo.dfs.rpc.model.FetchEditsLogResponse;
import com.demo.dfs.rpc.service.NameNodeServiceGrpc;

import io.grpc.ManagedChannel;
import io.grpc.netty.NegotiationType;
import io.grpc.netty.NettyChannelBuilder;

/**
 * 
 * @author yuezh2@lenovo.com
 *	@date 2022年2月18日下午9:19:10
 */
public class NameNodeRpcClient {

	private static final String NAMEODE_HOSTNAME = "localhost";
	private static final Integer NAMENODE_PORT = 50070;
	
	private NameNodeServiceGrpc.NameNodeServiceBlockingStub namenode;
	
	
	
	/**
	 * 
	 */
	public NameNodeRpcClient () {
		
		ManagedChannel channel = NettyChannelBuilder
				.forAddress(NAMEODE_HOSTNAME , NAMENODE_PORT)
				.negotiationType(NegotiationType.PLAINTEXT)//消息类型
				.build();
		//
		this.namenode = NameNodeServiceGrpc.newBlockingStub(channel);
	}
	
	
	/**
	 * 抓取editslog数据
	 * @return
	 */
	public JSONArray fetchEditsLog() {
		FetchEditsLogRequest request = FetchEditsLogRequest.newBuilder()
						.setCode(1)
						.build();
		
		FetchEditsLogResponse response = namenode.fetchEditsLog(request);
		String editsLogJson = response.getEditsLog();
		return JSONArray.parseArray(editsLogJson);
	}
	
}
