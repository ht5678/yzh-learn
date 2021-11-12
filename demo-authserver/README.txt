sso-auth-code-server:

login filter:

切换成cookie :

*关闭org.sso.portal.code.interceptor.LoginInterceptor的注解
*打开org.sso.portal.code.interceptor.CookieLoginInterceptor的注解
*org.sso.portal.code.config.PortalWebConfig切换cookie配置
*org.sso.portal.code.controller.CallBackController修改callBack方法为cookie方式
*org.sso.portal.code.controller.PortalProductController.showProductDetail修改为cookie方式
*yzh-learn\demo-authserver\sso-portal-code\src\main\resources\templates 添加cookie方式




code auth方式 (按顺序):
*sso-auth-code-server
*ms-cloud-gateway
*gateway-product-service
*gateway-order-service
*sso-portal-code


jwt方式:
*sso-auth-jwt-server
*sso-api-gateway-jwt
*sso-product-jwt-server
*TestJwtGatewayUtil