package springmvc2.mvc;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.core.ParameterNameDiscoverer;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.servlet.view.freemarker.FreeMarkerView;

import freemarker.template.Configuration;
import freemarker.template.MalformedTemplateNameException;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;
import freemarker.template.TemplateNotFoundException;




/**
 * 
 * 
 * 
 * @author yuezh2   2019年10月15日 下午11:29:24
 *
 */
public class HandlerServlet extends HttpServlet {

	private WebApplicationContext context;
	private MvcBeanFactory beanFactory;
	final ParameterNameDiscoverer parameterUtil = new LocalVariableTableParameterNameDiscoverer();
	private Configuration freemarkerConfig;
	
	
	

	@Override
	public void init() throws ServletException {
		context = WebApplicationContextUtils.getWebApplicationContext(getServletContext());
		beanFactory = new MvcBeanFactory(context);
		Configuration freemarkerConfig = null;
		try{
			freemarkerConfig = context.getBean(Configuration.class);
		}catch(NoSuchBeanDefinitionException e){}
		
		if(freemarkerConfig == null){
			freemarkerConfig = new Configuration(Configuration.VERSION_2_3_23);
			freemarkerConfig.setDefaultEncoding("UTF-8");
			freemarkerConfig.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
			
			try {
				freemarkerConfig.setDirectoryForTemplateLoading(new File(getServletContext().getRealPath("/ftl/")));
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
		this.freemarkerConfig = freemarkerConfig;
	}
	
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doHandler(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doHandler(req, resp);
	}


	
	public void doHandler(HttpServletRequest req , HttpServletResponse resp){
		String uri = req.getServletPath();
		if(uri.equals("/favicon.ico")){
			return;
		}
		
		MvcBeanFactory.MvcBean mvcBean = beanFactory.getMvcBean(uri);
		
		if(mvcBean == null){
			throw new IllegalArgumentException(String.format("not found %s mapping", uri));
		}
		
		Object[] args = buildParams(mvcBean, req, resp);
		
		Object result;
		try {
			result = mvcBean.run(args);
			processResult(result, resp);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	
	
	private void processResult(Object result , HttpServletResponse resp) throws TemplateNotFoundException, MalformedTemplateNameException, freemarker.core.ParseException, IOException, TemplateException{
		if(result instanceof FreeMarkerView){
			FreemarkeView fview = (FreemarkeView)result;
			Template temp = freemarkerConfig.getTemplate(fview.getFtlPath());
			ByteArrayOutputStream output = new ByteArrayOutputStream();
			resp.setContentType("text/html;charset=UTF-8");
			resp.setCharacterEncoding("utf-8");
			resp.setStatus(200);
			temp.process(fview.getModels(), resp.getWriter());
		}
		//JSTL VIEW
		//JSON VIEW
	}
	
	
	
	
	private Object[] buildParams(MvcBeanFactory.MvcBean mvcBean , HttpServletRequest req , HttpServletResponse resp){
		Method method = mvcBean.getTargetMethod();
		List<String> paramNames = Arrays.asList(parameterUtil.getParameterNames(method));
		Class<?>[] paramTypes = method.getParameterTypes();//反射
		//a,b,c
		Object[] args = new Object[paramTypes.length];
		for(int i = 0 ; i < paramNames.size();i++){
			if(paramTypes[i].isAssignableFrom(HttpServletRequest.class)){
				args[i] = req;
			}else if(paramTypes[i].isAssignableFrom(HttpServletResponse.class)){
				args[i] = resp;
			}else{
				if(req.getParameter(paramNames.get(i)) == null){
					args[i] = null;
				}else{
					args[i] = convert(req.getParameter(paramNames.get(i)), paramTypes[i]);
				}
			}
		}
		return args;
	}
	
	
	
	public <T> T convert(String val , Class<T> targetClass){
		Object result = null;
		if(val == null){
			return null;
		}else if(Integer.class.equals(targetClass)){
			result = Integer.parseInt(val.toString());
		}else if(Long.class.equals(targetClass)){
			result = Long.parseLong(val.toString());
		}else if(Date.class.equals(targetClass)){
			try {
				result = new SimpleDateFormat("").parse(val);
			} catch (ParseException e) {
				throw new IllegalArgumentException("date format Illegal must be 'yyyy-MM-dd HH:mm:ss'");
			}
		}else if(String.class.equals(targetClass)){
			result = val;
		}
		//TODO:serializable  进行自动封装
		else{
			System.err.println(String.format("not support param type %s", targetClass.getName()));
		}
		return (T)result;
	}
	
}
