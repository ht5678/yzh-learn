spring MVC执行流程:
DispatchServlet
1/基于URL查找对应的Mapping
2/基于Handler去查找对应的适配器
3/基于Handler获取返回结果(Model And View)
4/ViewName 去ViewResolver中查找对应的视图
5/在基于View去解析视图

5个核心组件:
HandlerMapping
HandlerAdapter  适配器
ViewResolver
HandlerExceptionResolver
HandlerInterceptor
