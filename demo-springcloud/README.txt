配置文件加载的顺序:

*项目的根目录config下
*项目的根目录applications.properties
*项目的resoures目录下的config目录
*项目的resoures目录下的配置文件


---

配置文件中   ---  代表分割 , 只能是3个,多一个或者少一个都不行


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