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
*ignoredServices(屏蔽serviceId,不做转发或者跳转,多个的话用 , 分割)
*ignoredPatterns(屏蔽url,对某些url不做处理,多个的话用 , 分割)



请求头配置(会把这两个头信息给屏蔽 , 不会转发给源服务 , 源服务获取不到):
zuul:
  sensitiveHeaders: accept-language,cookie
  ignoredHeaders: accept-language
  
  

路由端点(actuator可以看到路由的映射信息,默认情况下端点设置了认证 , 关掉认证就可以了):
Actuator依赖:
management.security.enabled设置为false


#需要加上这段配置,不然actuator的很多功能不能生效
management:
  endpoints:
    web:
      exposure:
        include: '*'   
        



zuul的filter需要动态加载,不然每次加载都要停掉服务
动态加载用到groovy



S3-l02-spring-zuul-gateway:动态添加过滤器
正式运行的时候，DynamicFilter.groovy 可以添加到 demo-springcloud-all\S3-l02-spring-zuul-gateway\src\main\java\groovy\filters\route 
过滤器就可以动态生效了,不需要重启服务



------------------------------------------------------------------------------------------------------------------------------------------------------------------------


springcloud-stream内置了三个接口:Sink  ,  Source   , Processor

public interface Sink{
	String INPUT = "input";
	
	@Input(Sink.INPUT)
	SubscribableChannel input();
}


public interface Sink{
	String OUTPUT = "input";
	
	@Input(Sink.OUTPUT)
	SubscribableChannel output();
}



根据上面两个接口得知 , 实际上帮我们内置了 input 和 output 两个通道 , 那么在大多数情况下 , 
我们就可以不必编写服务接口 , 甚至不必使用@Input和@Output两个注解 , 以消费者为例 , 
在绑定通道时加入Sink.class


Processor接口继承于Sink与Source , 在实际应用中, 可以只考虑使用Processor.



------------------------------------------------------------------------------------------------------------------------------------------------------------------------



配置中心简介:

服务器要做的事:
*提供访问配置的服务接口
*对属性进行加密和解密


客户端要做的事:
*绑定配置服务器,使用远程的属性来初始化spring容器
*对属性进行加密和解密
*属性改变时,可以对他们进行重新加载
*提供了与配置相关的几个管理节点
*在初始化引导程序的上下文时,进行绑定配置服务器和属性解密等工作,当然,也可以实现其他工作



引导程序:
*在主应用上下文初始化之前创建
*创建时会读取远程配置



配置SVN仓库:
*配置profile:
	*git:默认值,表示去Git仓库读取配置文件
	*subversion:表示去svn仓库读取配置文件
	*native:将会去本地的文件系统中读取配置文件
	*vault:去vault中读取配置文件,vault是一款资源控制工具,可对资源实现安全访问
	


encrypt:
  keyStore:
    location: classpath:/myTest.keystore	#keystore的位置
    password: 	mypassword						#密钥库的密码
    alias: testKey										#密钥对的别名
    secret: mysecret								#密钥口令
    
spring:
  profiles:
    active: subversion
  cloud:
    config:
      server:
        svn:
          uri: https://localhost/svn/test-project
          username: myusername
          password: mypassword
        default-label: default-config   #表示uri路径下的default-config目录下
        health:
          enabled: false
          respositories:
            book-service:
              label: health-test


security:
  user:
    name: root
    password: mypassword
    

    
    
    
    
    
    
客户端获取配置服务器的配置:
spring:
  application:
    name: config-client
  cloud:
    config:
      url: http://localhost:8888
      profile: dev         #会在config-server的searchpaths下读取 config-client-dev.yml文件
      
    
    
      
---
    
    
      
spring:
  cloud:
    config:
      url: http://localhost:8888
      profile: dev         
      name: config-client2  #会在config-server的searchpaths下读取 config-client2-dev.yml文件
    
    

如果既没有cloud.config.name , 也没有application.name , 则读取 application-dev.yml

客户端读取多份配置文件:
spring.cloud.config.profile: hystrix,zuul
客户端配置文件目录(会覆盖server的test-label):
spring.cloud.config.label: xxx




刷新bean:
在spring的容器中 , 有一个类型为RefreshBean的bean,当'/refresh'端点被访问时,负责处理刷新的   ContextRefresher 类  , 
会先去远程的配置服务器刷新配置,然后再调用RefreshBean的refreshAll方法处理实例,容器中使用@RefreshBean注解进行修饰的bean,
都会在缓存中进行销毁,当这些bean被再次引用时,就会创建新的实例,以此达到一个'刷新的效果'
@RefreshScope






配置中心的加解密都依赖JCE
记得encrypt.key: mykey 配置一定要放到bootstrap.yml文件里 , 不然会不成功 , 也可能跟版本有关系

配置服务器读取到这里的时候 , 就会自动解密 , 要加上'{cipher}'
test.user.password: '{cipher}ff477c23e45efd59def381ec2d487e473060e803517153fb5187bfd590f52ebb48144f66b8eca2b58eab3ac9df8a4e66'






配置中心的非对称加密:






客户端连接   配置服务器  的重置配置项:
*spring.cloud.config.retry.initial-interval: 初始的重试间隔,  默认1000ms
*spring.cloud.config.retry.max-attempts: 最大重试次数,默认为6
*spring.cloud.config.retry.max-interval: 最大的重试间隔,默认为2000ms
*spring.cloud.config.retry.multiplier:      重试间隔的递增系数, 默认为1.1





服务器的配置访问规则:
*/{application}/{profile}[/{label}]
*/{application}-{profile}.yml
*/{label}/{application}-{profile}.yml
*/{application}-{profile}.properties
*/{label}/{application}-{profile}.properties





*****************************config-client必须是bootstrap.yml配置文件 , bootstrap.yml文件的意思是会从远程读取配置来引导初始化项目, application.yml没有这样的效果*****************************