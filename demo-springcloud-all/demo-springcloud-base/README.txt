配置文件加载的顺序:

*项目的根目录config下
*项目的根目录applications.properties
*项目的resoures目录下的config目录
*项目的resoures目录下的配置文件


---

配置文件中   ---  代表分割 , 只能是3个,多一个或者少一个都不行

------------------------------------------------------------------------------------------------------------------------------------------------------------------------
eureka练习:
---
demo-springcloud-first-114:服务注册中心
demo-springcloud-first-police:服务提供者
demo-springcloud-first-person:服务消费者


测试流程:
*启动demo-springcloud-first-114的serverApp
*启动demo-springcloud-first-police的policeServer
*启动demo-springcloud-first-person的personServer

*打开:
	http://localhost:8761/
 	http://localhost:8081/router
 	

------------------------------------------------------------------------------------------------------------------------------------------------------------------------
 	
---eureka集群
*cloud-114   		ServerApp.java   启动输入 slave1  , 启动输入slave2 , 启动两个实例
*cloud-police		启动输入8080 , 启动输入8081,启动两个实例
*cloud-person 	启动,访问  http://localhost:9000/router , 多次刷新来自不同的police


------------------------------------------------------------------------------------------------------------------------------------------------------------------------

---客户端健康检查和常用配置
*心跳配置
*服务列表抓取配置
*元数据的配置和使用
*关闭自我保护模式


------------------------------------------------------------------------------------------------------------------------------------------------------------------------
客户端健康状态actuator:

http://localhost:8080/actuator
http://localhost:8080/actuator/health

通过这个接口修改  注册服务 的在线状态 
http://localhost:8080/db/true
------------------------------------------------------------------------------------------------------------------------------------------------------------------------


ribbon简介(用于客户端):
*负载均衡框架,支持可插拔的负载均衡规则
*支持多种协议,如HTTP , UDP 等
*提供负载均衡客户端


负载均衡器组件:
*一个负载均衡,至少提供以下功能:
	-要维护各服务器的IP等信息
	-根据特定逻辑选取服务器
*为了实现基本的负载均衡功能,ribbon的负载均衡器有三大子模块:
	-rule
	-ping
	-serverList
	

	
ribbon的内置负载均衡规则:
*RoundRobinRule
*AvailabilityFilteringRule
*WeightedResponseTimeRule
*ZoneAvoidanceRule
*BestAvailableRule
*RandomRule
*RetryRule


springcloud中使用ribbon测试启动：
依次启动server，provider，client
然后访问http://localhost:8081/router ， 如果报错， 请多次请求， 因为会负载到8080和8081两个端口 ， 8081没有启动 ，负载到8080就会成功了


------------------------------------------------------------------------------------------------------------------------------------------------------------------------

feign:
默认使用HttpURLConnection连接HTTP服务


spring-feign整合:
*s2-l03-spring-feign-server启动:输入slave1启动
*s2-l03-spring-feign-provider启动:输入8080启动
*s2-l03-spring-feign-invoker启动:启动

测试:
http://localhost:9000/routerclient



feign压缩配置:
  feign.compression.request.enabled: 设置为true开启请
  求压缩。
  feign.compression.response.enabled:设置为true开户响应压缩。
  feign.compression.request.mime-types:  数据类型列表，默认值为text/xml, application/xml, application/json。
  feign.compression.request.min-request-size:  设置请求内容的最小阀值，默认值为2048。


------------------------------------------------------------------------------------------------------------------------------------------------------------------------

hystrix命令执行4种模式:
*toObservable		:rxjava的类,是其他3个方法的基础,不会立即执行
*observe				:会立即执行
*queue
*execute


发生回退的三种情况:
*执行超时等失败情况
*断路器打开
*线程池满载



断路器开启的条件:
*整个链路达到一定的阈值,默认情况下 , 10s钟内产生超过20次请求,则符合第一个条件
*满足第一个条件的情况下,如果请求的错误百分比大于阈值,则会打开断路器,默认为50%

断路器打开以后,有默认5s的休眠期,到时候会变成半休眠状态,然后会尝试一两次请求,如果请求成功了,会关闭断路器.

