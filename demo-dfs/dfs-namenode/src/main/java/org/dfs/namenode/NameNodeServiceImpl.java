package org.dfs.namenode;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
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
	private long syncedTxid = 0L;
	
	/**
	 * 当前缓冲的一小部分editslog,
	 */
	private JSONArray currentBufferedEditsLog = new JSONArray();
	
	/**
	 * 当前缓存里editslog最大的txid
	 */
	private long currentBufferedMaxTxid = 0L;
	
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
		
		
		//如果此时没有刷出任何磁盘文件 ,  此时数据仅在于内存缓冲中
		if(flushedTxids.size() == 0) {
			System.out.println("暂时没有任何磁盘文件 , 直接从内存缓冲中拉取 ... ... ");
			fetchFromBufferedEditsLog(fetchedEditsLog);
		} else {//如果此时已经有落地磁盘文件 , 要扫描所有磁盘文件额索引范围
			//第一种情况 , 要拉取的txid是在某个磁盘文件里的
			//有磁盘文件,  而且内存里还缓存了某个磁盘文件的数据
			if(bufferedFlushedTxid != null) {
				//如果要拉取的数据就在当前缓存的磁盘文件数据里
				if(existInFlushedFile(bufferedFlushedTxid)) {
					System.out.println("上一次已经缓存过磁盘文件的数据 , 直接从磁盘文件缓存中拉取editslog ...");
					fetchFromCurrentBuffer(fetchedEditsLog);
				}else {//如果要拉取的数据不在当前缓存的磁盘文件数据里 , 那么需要从下一个磁盘文件拉取
					String nextFlushedTxid = getNextFlushedTxid(flushedTxids , bufferedFlushedTxid);

					//如果可以找到下一个磁盘文件 , 那么就从下一个磁盘文件里开始读取数据
					if(nextFlushedTxid != null) {
						System.out.println("上一次缓存的磁盘文件找不到要拉取的数据 , 从下一个磁盘文件中拉取editslog ");
						fetchFromFlushedFile(nextFlushedTxid, fetchedEditsLog);
					}else {
						System.out.println("上一次缓存的磁盘文件找不到要拉取的数据 , 而且没有下一个文件 , 尝试从内存缓冲中拉取 ");
						//如果没有找到下一个文件, 需要从内存中继续读取
						fetchFromBufferedEditsLog(fetchedEditsLog);
					}
				}
			}else{//第一次尝试从磁盘文件中拉取
				//遍历所有磁盘文件的索引范围 , 0-391 , 
				boolean fetchedFromFlushedFile = false;
				for(String flushedTxid : flushedTxids) {
					//如果要拉取的下一条数据在某个磁盘文件里
					if(existInFlushedFile(flushedTxid)) {
						System.out.println("尝试从磁盘文件中拉取 ...");
						//此时可以把这个磁盘文件里以及下一个磁盘文件的数据都读出来 , 放到内存里缓存
						//就怕一个磁盘文件的数据不够10条
						fetchFromFlushedFile(flushedTxid, fetchedEditsLog);
						fetchedFromFlushedFile = true;
						break;
					}
				}
				
				//第二种情况 , 要拉取的txid已经比磁盘文件的都新了 , 还在内存缓冲
				if(!fetchedFromFlushedFile) {
					System.out.println("所有磁盘文件都没有找到要拉取的editslog , 尝试从内存缓冲中拉取editslog ... ...");
					fetchFromBufferedEditsLog(fetchedEditsLog);
				}
			}
		}
		
		response = FetchEditsLogResponse.newBuilder()
				.setEditsLog(fetchedEditsLog.toJSONString())
				.build();
		
		responseObserver.onNext(response);
		responseObserver.onCompleted();
	}
	
	
	
	/**
	 * 从已经刷入的文件里读取editsLog , 同时缓存文件到内存 
	 * @param flushedTxid
	 */
	private void fetchFromFlushedFile(String flushedTxid , JSONArray fetchedEditsLog) {
		String[] flushedTxidSplited = flushedTxid.split("_");
		
		long startTxid = Long.valueOf(flushedTxidSplited[0]);
		long endTxid = Long.valueOf(flushedTxidSplited[1]);
//		long fetchTxid = backupSyncTxid+1;
		
		//*****目录必须一直 *****
		String currentEditsLogFile = "d:\\data\\edits-"+startTxid+"-"+endTxid+".log";
		
		List<String> editsLogs;
		try {
			editsLogs = Files.readAllLines(Paths.get(currentEditsLogFile) , StandardCharsets.UTF_8);
			
			currentBufferedEditsLog.clear();
			for(String editLog : editsLogs) {
				currentBufferedEditsLog.add(JSONObject.parseObject(editLog));
				//我们在这里记录一下 , 当前内存缓冲中的数据最大txid是多少 , 这样下去再拉取
				//可以判断 , 内存缓存里的数据是否还可以读取,不要每次都重新从内存缓冲中去加载
				currentBufferedMaxTxid = JSONObject.parseObject(editLog).getLongValue("txid");
			}
			
			bufferedFlushedTxid = flushedTxid;		//缓存了某个刷入磁盘文件的数据
			
			//
			fetchFromCurrentBuffer(fetchedEditsLog);
			
			//
		} catch (IOException e) {
			e.printStackTrace();
		}
		

	}
	
	
	
	
	/**
	 * 是否存在于刷到磁盘文件中
	 * @param flushedTxid
	 * @return
	 */
	private Boolean existInFlushedFile(String flushedTxid) {
		String[] flushedTxidSplited = flushedTxid.split("_");
		
		long startTxid = Long.valueOf(flushedTxidSplited[0]);
		long endTxid = Long.valueOf(flushedTxidSplited[1]);
		long fetchTxid = syncedTxid+1;
		
		if(fetchTxid >= startTxid && fetchTxid<= endTxid) {
			return true;
		}
		return false;
	}
	
	
	
	
	/**
	 * 就是从, 内存缓冲 的editslog中拉取数据
	 * @param fetchedEditsLog
	 */
	private void fetchFromBufferedEditsLog(JSONArray fetchedEditsLog) {
		//如果要拉取的txid还在上一次内存缓冲中 , 此时继续从内存缓冲中拉取即可
		long fetchTxid = syncedTxid + 1;
		if(fetchTxid <= currentBufferedMaxTxid){
			fetchFromCurrentBuffer(fetchedEditsLog);
			return;
		}
		
		//必须重新把内存缓冲里的数据加载到内存缓存里来
		currentBufferedEditsLog.clear();

		String[] bufferedEditsLog = namesystem.getEditsLog().getBufferedEditsLog();
		
		if(bufferedEditsLog != null){
			for(String editsLog : bufferedEditsLog) {
				currentBufferedEditsLog.add(JSONObject.parseObject(editsLog));
				//我们在这里记录一下 , 当前内存缓冲中的数据最大txid是多少 , 这样下去再拉取
				//可以判断 , 内存缓存里的数据是否还可以读取,不要每次都重新从内存缓冲中去加载
				currentBufferedMaxTxid = JSONObject.parseObject(editsLog).getLongValue("txid");
			}
			bufferedFlushedTxid = null;
			//
			fetchFromCurrentBuffer(fetchedEditsLog);
		}
	}
	
	
	/**
	 * 从当前已经在内存缓存里的数据拉取editslog
	 */
	private void fetchFromCurrentBuffer(JSONArray fetchedEditsLog) {
		int fetchCount = 0;
		for(int i = 0 ; i < currentBufferedEditsLog.size() ;i++) {
			if(currentBufferedEditsLog.getJSONObject(i).getLong("txid")  ==  syncedTxid + 1) {
				fetchedEditsLog.add(currentBufferedEditsLog.getJSONObject(i));
				syncedTxid = currentBufferedEditsLog.getJSONObject(i).getLong("txid");
				fetchCount++;
			}
			
			if(fetchCount == BACKUP_NODE_FETCH_SIZE) {
				break;
			}
		}
	}
	
	

	/**
	 * 获取下一个磁盘文件对应的txid范围
	 * @param flushedTxids
	 * @param bufferedFlushedTxid
	 * @return
	 */
	private String getNextFlushedTxid(List<String> flushedTxids , String bufferedFlushedTxid) {
		for(int i = 0 ; i < flushedTxids.size() ; i++) {
			if(flushedTxids.get(i).equals(bufferedFlushedTxid) ) {
				if((i+1) < flushedTxids.size()) {
					return flushedTxids.get(i+1);
				}
			}
		}
		return null;
	}
	
	
}
