package com.demo.register.server;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 服务注册表
 * @author yuezh2@lenovo.com
 *	@date 2022年1月6日下午8:35:30
 */
public class ServiceRegistry {

	/**
	 * 单例
	 */
	private static ServiceRegistry instance = new ServiceRegistry();
	
	
	private ServiceRegistry() {
		
	}
	
	//核心内存数据结构 : 注册表
	//key 是服务名称, value是服务的所有服务实例
	//Map<String , ServiceInstance> : key是服务实例id , value是服务实例的信息
	private Map<String, Map<String , ServiceInstance>> registry = new ConcurrentHashMap<>();
	
	
	
	/**
	 * 服务注册
	 * @param serviceInstance
	 */
	public void register(ServiceInstance serviceInstance) {
		Map<String, ServiceInstance> serviceInstanceMap = registry.get(serviceInstance.getServiceName());
		
		if(serviceInstanceMap == null) {
			serviceInstanceMap = new ConcurrentHashMap<String, ServiceInstance>();
			registry.put(serviceInstance.getServiceName(), serviceInstanceMap);
		}
		
		serviceInstanceMap.put(serviceInstance.getServiceInstanceId(), serviceInstance);
		
		System.out.println("服务实例 ["+serviceInstance+"] , 完成注册 ......");
		System.out.println("注册表: "+registry);
	}
	
	
	
	/**
	 * 获取服务实例
	 * @param serviceName
	 * @return
	 */
	public ServiceInstance getServiceInstance(String serviceName , String serviceInstanceId) {
		Map<String, ServiceInstance> serviceInstanceMap = registry.get(serviceName);
		return serviceInstanceMap.get(serviceInstanceId);
	}
	
	
	/**
	 * 获取整个注册表
	 * @return
	 */
	public Map<String, Map<String , ServiceInstance>> getRegistry() {
		return registry;
	}
	
	
	/**
	 * 从注册表删除一个服务实例
	 * @param serviceName
	 * @param serviceInstanceId
	 */
	public void remove(String serviceName  , String serviceInstanceId) {
		Map<String, ServiceInstance> serviceInstanceMap = registry.get(serviceName);
		serviceInstanceMap.remove(serviceInstanceId);
		System.out.println("服务实例:"+serviceInstanceId+" , 进行摘除");
	}
	
	
	public static ServiceRegistry getInstance() {
		return instance;
	}
	
}
