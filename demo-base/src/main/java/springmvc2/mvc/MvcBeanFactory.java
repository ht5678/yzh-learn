package springmvc2.mvc;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;

import org.springframework.context.ApplicationContext;
import org.springframework.util.Assert;

/**
 * 
 * 
 * 
 * @author yuezh2   2019年10月15日 下午11:04:15
 *
 */
public class MvcBeanFactory {

	private ApplicationContext applicationContext;
	
	//api接口住的地方
	private HashMap<String, MvcBean>  apiMap = new HashMap<>();
	
	
	
	public MvcBeanFactory(ApplicationContext applicationContext){
		Assert.notNull(applicationContext, "argument 'applicationContext' must not be null");
		this.applicationContext = applicationContext;
		this.loadApiFromSpringBeans();
	}
	
	
	
	public MvcBean getMvcBean(String apiName){
		return apiMap.get(apiName);
	}
	
	
	
	private void loadApiFromSpringBeans(){
		apiMap.clear();
		
		//ioc所有bean
		//spring ioc 扫描
		String[] names = applicationContext.getBeanDefinitionNames();
		Class<?> type;
		
		for(String name : names){
			type = applicationContext.getType(name);
			for(Method m : type.getDeclaredMethods()){
				//通过反射拿到HttpMapping注解
				MvcMapping mvcMapping = m.getAnnotation(MvcMapping.class);
				if(mvcMapping!=null){
					//封装成一个mvc  bean
					addApiItem(mvcMapping, name, m);
				}
			}
		}
		
	}
	
	
	/**
	 * 添加api
	 * @param mvcMapping
	 * @param beanName
	 * @param method
	 */
	private void addApiItem(MvcMapping mvcMapping , String beanName, Method method){
		MvcBean apiRun = new MvcBean();
		apiRun.apiName=mvcMapping.value();
		apiRun.targetMethod=method;
		apiRun.targetName=beanName;
		apiRun.context = this.applicationContext;
		
		apiMap.put(mvcMapping.value(), apiRun);
	}
	
	
	
	public boolean containsApi(String apiName,String version){
		return apiMap.containsKey(apiName+"_"+version);
	}
	
	
	public ApplicationContext getApplicationContext(){
		return applicationContext;
	}
	
	
	
	
	//用于执行对应的api方法
	public static class MvcBean{
		String apiName; 	//bit.api.user.getUser
		String targetName;//ioc bean 名称
		
		Object target;		//UserServiceImpl实例
		Method targetMethod;//目标方法getUser
		ApplicationContext context;
		
		
		public Object run(Object... args) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException{
			//懒加载
			if(target ==null){
				//spring ioc容器里边服务bean , 比如GoodsServiceImpl
				target = context.getBean(targetName);
			}
			return targetMethod.invoke(target, args);
		}
		
		
		public Class<?>[] getParamTypes(){
			return targetMethod.getParameterTypes();
		}

		public String getApiName() {
			return apiName;
		}

		public String getTargetName() {
			return targetName;
		}

		public Object getTarget() {
			return target;
		}

		public Method getTargetMethod() {
			return targetMethod;
		}
		
		
		
	}
	
	
	
}
