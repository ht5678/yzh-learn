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
*queue					:异步执行
*execute				:同步执行


发生回退的三种情况:
*执行超时等失败情况
*断路器打开
*线程池满载



断路器开启的条件:
*整个链路达到一定的阈值,默认情况下 , 10s钟内产生超过20次请求,则符合第一个条件
*满足第一个条件的情况下,如果请求的错误百分比大于阈值,则会打开断路器,默认为50%

断路器打开以后,有默认5s的休眠期,到时候会变成半休眠状态,然后会尝试一两次请求,如果请求成功了,会关闭断路器.



隔离策略(各有优势):
*Thread		(线程池默认10个线程)
*Semaphore	(不支持超时和异步操作的 , 开销小)


------------------------------------------------------------------------------------------------------------------------------------------------------------------------

#监控
S2-l08-spring-hystrix-dashboard:


ping:
http://localhost:8081/actuator/hystrix.stream

url(里边的url写ping的url , 然后monitor-name随便写, 一开始是没有监控结果的 , 需要调用一下随便某个接口才会有结果):
http://localhost:8082/hystrix


------------------------------------------------------------------------------------------------------------------------------------------------------------------------

zuul:
测试url:
http://localhost:9000/source/hello/zhangsan

source是在配置文件中配置的 , 用于隔离不同的请求.
source的请求都会跳转到8080的服务器上



zuul集群测试:
测试url:
http://localhost:9001/sale/food-sale/1

测试url:
http://localhost:9001/routeTest/163

测试url:
http://localhost:9001/route163

测试跳转路由规则url:
http://localhost:9001/fo/



zuul路由规则:
*简单路由:
*SimpleHostRoutingFilter(底层会从HttpServletRequest中获取请求信息 , 然后包装成httpclient请求源服务):
	配置连接池:
	*zuul.host.maxTotalConnections:目标主机的最大连接数,默认值为200.配置该项,相当于调用了PoolingHttpClientConnectionManager的setMaxTotal方法
	*zuul.host.maxPerRouteConnections:每个主机的初始连接数,默认为20.配置该项,相当于调用了PoolingHttpClientConnectionManager的setDefaultMaxPerRoute方法

	
*跳转路由:
*SendForwardFilter



*自定义路由规则:
*PatternServiceRouteMapper(自定义路由用到的 , 参考S3-l02-spring-zuul-gateway里边的MyConfig)
*ignoredServices(屏蔽serviceId,不做转发或者跳转)
*ignoredPatterns(屏蔽url,对某些url不做处理)