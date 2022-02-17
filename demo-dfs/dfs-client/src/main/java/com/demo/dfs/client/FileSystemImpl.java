package com.demo.dfs.client;

import com.demo.dfs.rpc.model.MkdirRequest;
import com.demo.dfs.rpc.model.MkdirResponse;
import com.demo.dfs.rpc.model.ShutdownRequest;
import com.demo.dfs.rpc.service.NameNodeServiceGrpc;

import io.grpc.ManagedChannel;
import io.grpc.netty.NegotiationType;
import io.grpc.netty.NettyChannelBuilder;

/**
 * 文件系统客户端的实现类
 * @author yuezh2@lenovo.com
 *	@date 2021年12月30日下午2:27:39
 */
public class FileSystemImpl implements FileSystem {

	private static final String NAMEODE_HOSTNAME = "localhost";
	private static final Integer NAMENODE_PORT = 50070;
	
	private NameNodeServiceGrpc.NameNodeServiceBlockingStub namenode;
	
	
	
	/**
	 * 
	 */
	public FileSystemImpl () {
		
		ManagedChannel channel = NettyChannelBuilder
				.forAddress(NAMEODE_HOSTNAME , NAMENODE_PORT)
				.negotiationType(NegotiationType.PLAINTEXT)//消息类型
				.build();
		//
		this.namenode = NameNodeServiceGrpc.newBlockingStub(channel);
	}
	
	
	
	
	/**
	 * 创建目录
	 */
	@Override
	public void mkdir(String path) throws Exception {
		MkdirRequest request = MkdirRequest.newBuilder()
					.setPath(path)
					.build();
		
		MkdirResponse response = namenode.mkdir(request);
		System.out.println("创建目录的响应: "+response.getStatus());
	}



	/**
	 * 优雅关闭
	 */
	@Override
	public void shutdown() throws Exception {
		ShutdownRequest request = ShutdownRequest.newBuilder()
				.setCode(1)
				.build();		
		namenode.shutdown(request);
	}

}
