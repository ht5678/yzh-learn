package org.dfs.namenode;

import com.demo.dfs.rpc.model.HeartbeatRequest;
import com.demo.dfs.rpc.model.HeartbeatResponse;
import com.demo.dfs.rpc.model.MkdirRequest;
import com.demo.dfs.rpc.model.MkdirResponse;
import com.demo.dfs.rpc.model.RegisterRequest;
import com.demo.dfs.rpc.model.RegisterResponse;
import com.demo.dfs.rpc.model.ShutdownRequest;
import com.demo.dfs.rpc.model.ShutdownResponse;
import com.demo.dfs.rpc.service.NameNodeServiceGrpc.NameNodeService;

import io.grpc.stub.StreamObserver;

/**
 * NameNode的rpc服务接口
 * @author yuezh2@lenovo.com
 *	@date 2021年12月21日下午8:58:04
 */
public class NameNodeServiceImpl implements NameNodeService {
	
	/**
	 * 负责管理元数据的核心组件
	 */
	private FSNamesystem namesystem;
	
	/**
	 * 是一个逻辑上的组件 , 负责管理元数据的更新
	 * 比如说要更新内存里的文件目录树 , 就可以用dataNodeManager , ta更新的是元数据
	 * 
	 * 负责管理集群中所有的datanode组件
	 */
	private DataNodeManager dataNodeManager;
	
	public static final Integer STATUS_SUCCESS =1;
	
	public static final Integer STATUS_FAILURE = 2;
	
	public static final Integer STATUS_SHUTDOWN = 3;
	
	/*
	 * 是否在运行
	 */
	private volatile boolean isRunning = true;
	
	
	/**
	 * 
	 * @param namesystem
	 */
	public NameNodeServiceImpl(FSNamesystem namesystem , DataNodeManager dataNodeManager) {
		this.namesystem = namesystem;
		this.dataNodeManager = dataNodeManager;
	}
	
	
	/**
	 * 创建目录
	 * @param path 目录路径
	 * @return		是否创建成功
	 * @throws Exception
	 */
//	public Boolean mkdir(String path) throws Exception {
//		return this.namesystem.mkdir(path);
//	}
	
	
	/**
	 * datanode进行注册
	 * @param ip
	 * @param hostname
	 * @return
	 */
//	public Boolean register(String ip , String hostname)throws Exception {
//		return dataNodeManager.register(ip, hostname);
//	}
	
	
	/**
	 * 启动这个rpc server
	 */
	public void start() {
		System.out.println("开始监听指定的rpc server端口号 , 来接收请求");
	}

	
	/**
	 * datanode进行注册
	 */
	@Override
	public void register(RegisterRequest request, StreamObserver<RegisterResponse> responseObserver) {
		dataNodeManager.register(request.getIp(), request.getHostname());
		RegisterResponse response = RegisterResponse.newBuilder()
					.setStatus(STATUS_SUCCESS)
					.build();
		responseObserver.onNext(response);
		responseObserver.onCompleted();
	}

	
	/**
	 * datanode心跳检测
	 */
	@Override
	public void heartbeat(HeartbeatRequest request, StreamObserver<HeartbeatResponse> responseObserver) {
		dataNodeManager.heartbeat(request.getIp(), request.getHostname());
		HeartbeatResponse response = HeartbeatResponse.newBuilder()
					.setStatus(STATUS_SUCCESS)
					.build();
		responseObserver.onNext(response);
		responseObserver.onCompleted();
	}

	
	
	/**
	 * 创建目录
	 */
	@Override
	public void mkdir(MkdirRequest request, StreamObserver<MkdirResponse> responseObserver) {
		try {
			MkdirResponse response = null;
			if(!isRunning) {
				response = MkdirResponse.newBuilder()
						.setStatus(STATUS_SHUTDOWN)
						.build();
				responseObserver.onNext(response);
				responseObserver.onCompleted();
			}else {
				this.namesystem.mkdir(request.getPath());//保存文件目录结构到内存中
				System.out.println("创建目录 path : "+request.getPath());
				
				response = MkdirResponse.newBuilder()
						.setStatus(STATUS_SUCCESS)
						.build();
			}
			responseObserver.onNext(response);
			responseObserver.onCompleted();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}


	
	/**
	 * 优雅关闭
	 */
	@Override
	public void shutdown(ShutdownRequest request, StreamObserver<ShutdownResponse> responseObserver) {
		this.isRunning = false;
		this.namesystem.flush();
	}
	
}
