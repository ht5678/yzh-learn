package org.dfs.namenode;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.demo.dfs.rpc.model.FetchEditsLogRequest;
import com.demo.dfs.rpc.model.FetchEditsLogResponse;
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
	
	public static final Integer BACKUP_NODE_FETCH_SIZE = 10;
	
	/*
	 * 是否在运行
	 */
	private volatile boolean isRunning = true;
	
	/**
	 * 当前backup node 节点同步到了哪条txid
	 */
	private long backupSyncTxid = 0L;
	
	/**
	 * 当前缓冲的一小部分editslog,
	 */
	private JSONArray currentBufferedEditsLog = new JSONArray();
	
	/**
	 * 当前内存缓冲了哪个磁盘文件的数据
	 */
	private String bufferedFlushedTxid;
	
	
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

	
	/**
	 * 拉取editslog
	 */
	@Override
	public void fetchEditsLog(FetchEditsLogRequest request, StreamObserver<FetchEditsLogResponse> responseObserver) {
		FetchEditsLogResponse response = null;
		JSONArray fetchedEditsLog = new JSONArray();
		List<String> flushedTxids = namesystem.getEditsLog().getFlushedTxids();
		
		if(flushedTxids.size() == 0) {
			
			if(backupSyncTxid!=0) {
				currentBufferedEditsLog.clear();
				int fetchCount = 0;
				
				for(int i = 0 ; i < currentBufferedEditsLog.size() ;i++) {
					if(currentBufferedEditsLog.getJSONObject(i).getLong("txid")  >  backupSyncTxid) {
						fetchedEditsLog.add(currentBufferedEditsLog.getJSONObject(i));
						backupSyncTxid = currentBufferedEditsLog.getJSONObject(i).getLong("txid");
						fetchCount++;
					}
					
					if(fetchCount == BACKUP_NODE_FETCH_SIZE) {
						break;
					}
				}
				
				
				
			}else {
			
				//此时数据全部存在于内存缓冲里
				String[] bufferedEditsLog = namesystem.getEditsLog().getBufferedEditsLog();
				
				for(String editsLog : bufferedEditsLog) {
					currentBufferedEditsLog.add(JSONObject.parseObject(editsLog));
				}
				
				//此时就可以从内存缓冲里的数据开始取数据
				
				int fetchSize = Math.min(BACKUP_NODE_FETCH_SIZE, currentBufferedEditsLog.size());
				for(int i = 0 ; i < fetchSize ; i++) {
					fetchedEditsLog.add(currentBufferedEditsLog.getJSONObject(i));
					backupSyncTxid = currentBufferedEditsLog.getJSONObject(i).getLong("txid");
					
//					if(i == fetchSize-1) {//last index
//						backupSyncTxid = currentBufferedEditsLog.getJSONObject(i).getLong("txid");
//					}
				}
				
			}
		} else {
			//第一种情况 , 要拉取的txid是在某个磁盘文件里的
			if(bufferedFlushedTxid != null) {
				String[] flushedTxidSplited = bufferedFlushedTxid.split("_");
				
				long startTxid = Long.valueOf(flushedTxidSplited[0]);
				long endTxid = Long.valueOf(flushedTxidSplited[1]);
				long fetchBeginTxid = backupSyncTxid+1;
				
				if(fetchBeginTxid >= startTxid && fetchBeginTxid<= endTxid) {
					int fetchCount = 0;
					
					for(int i = 0 ; i < currentBufferedEditsLog.size() ;i++) {
						if(currentBufferedEditsLog.getJSONObject(i).getLong("txid")  >  backupSyncTxid) {
							fetchedEditsLog.add(currentBufferedEditsLog.getJSONObject(i));
							backupSyncTxid = currentBufferedEditsLog.getJSONObject(i).getLong("txid");
							fetchCount++;
						}
						
						if(fetchCount == BACKUP_NODE_FETCH_SIZE) {
							break;
						}
					}
				}else {
					String nextFlushedTxid = null;
					
					for(int i = 0 ; i < flushedTxids.size() ; i++) {
						if(flushedTxids.get(i).equals(bufferedFlushedTxid) ) {
							if((i+1) < flushedTxids.size()) {
								nextFlushedTxid = flushedTxids.get(i+1);
								
							}
						}
					}
					
					
					if(flushedTxids != null) {
						flushedTxidSplited = nextFlushedTxid.split("_");
						
						startTxid = Long.valueOf(flushedTxidSplited[0]);
						endTxid = Long.valueOf(flushedTxidSplited[1]);
						
						String currentEditsLogFile = "d:\\testdfs\\edits-"+startTxid+"-"+endTxid+".log";
						
						try {
							currentBufferedEditsLog.clear();
							List<String> editsLogs = Files.readAllLines(Paths.get(currentEditsLogFile));
							for(String editLog : editsLogs) {
								currentBufferedEditsLog.add(JSONObject.parseObject(editLog));
							}
							
							//
							int fetchCount = 0;
							
							for(int i = 0 ; i < currentBufferedEditsLog.size() ;i++) {
								if(currentBufferedEditsLog.getJSONObject(i).getLong("txid")  >  backupSyncTxid) {
									fetchedEditsLog.add(currentBufferedEditsLog.getJSONObject(i));
									backupSyncTxid = currentBufferedEditsLog.getJSONObject(i).getLong("txid");
									fetchCount++;
								}
								
								if(fetchCount == BACKUP_NODE_FETCH_SIZE) {
									break;
								}
							}
						}catch(Exception e) {
							e.printStackTrace();
						}
					}
					
				}
				
			}
			
			for(String flushedTxid : flushedTxids) {
//			for(int i = 0 ; i < flushedTxids.size() ; i++) {
				String[] flushedTxidSplited = flushedTxid.split("_");
				
				long startTxid = Long.valueOf(flushedTxidSplited[0]);
				long endTxid = Long.valueOf(flushedTxidSplited[1]);
				long fetchBeginTxid = backupSyncTxid+1;
				
				if(fetchBeginTxid >= startTxid && fetchBeginTxid<= endTxid) {
					//此时可以把这个磁盘文件里以及下一个磁盘文件的数据都读出来 , 放到内存里缓存
					//就怕一个磁盘文件的数据不够10条
					bufferedFlushedTxid = flushedTxid;
					
					currentBufferedEditsLog.clear();
					
					String currentEditsLogFile = "d:\\testdfs\\edits-"+startTxid+"-"+endTxid+".log";
					
					try {
						List<String> editsLogs = Files.readAllLines(Paths.get(currentEditsLogFile));
						for(String editLog : editsLogs) {
							currentBufferedEditsLog.add(JSONObject.parseObject(editLog));
						}
						
						//
						int fetchCount = 0;
						
						for(int i = 0 ; i < currentBufferedEditsLog.size() ;i++) {
							if(currentBufferedEditsLog.getJSONObject(i).getLong("txid")  >  backupSyncTxid) {
								fetchedEditsLog.add(currentBufferedEditsLog.getJSONObject(i));
								backupSyncTxid = currentBufferedEditsLog.getJSONObject(i).getLong("txid");
								fetchCount++;
							}
							
							if(fetchCount == BACKUP_NODE_FETCH_SIZE) {
								break;
							}
						}
						
					} catch (IOException e) {
						e.printStackTrace();
					}
					
				}
			}
			
			//第二种情况 , 要拉取的txid已经比磁盘文件的都新了 , 还在内存缓冲
			
			
		}
		
		response = FetchEditsLogResponse.newBuilder()
				.setEditsLog(fetchedEditsLog.toJSONString())
				.build();
		
		responseObserver.onNext(response);
		responseObserver.onCompleted();
	}
	
}
