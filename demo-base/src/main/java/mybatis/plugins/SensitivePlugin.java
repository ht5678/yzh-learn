package mybatis.plugins;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Properties;
import java.util.stream.Stream;

import org.apache.ibatis.executor.resultset.ResultSetHandler;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;

import mybatis.annotations.DataMask;

/**
 * 
 * @author yuezh2
 * @date 2021/06/24 14:15
 *
 */
@Intercepts(@Signature(type = ResultSetHandler.class,method = "handleResultSets",args = {DataMask.class}))
public class SensitivePlugin implements Interceptor{

	@SuppressWarnings("unchecked")
	@Override
	public Object intercept(Invocation invocation) throws Throwable {
		List<Object> records = (List<Object>) invocation.proceed();
		// 对结果集脱敏
		records.forEach(this::sensitive);
		return records;
	}
	
	
	private void sensitive(Object source) {
		// 拿到返回值类型
		Class<?> sourceClass = source.getClass();
		//初始化返回值类型的 MetaObject
		MetaObject metaObject = SystemMetaObject.forObject(source);
		// 捕捉到属性上的标记注解 @Sensitive 并进行对应的脱敏处理
		Stream.of(sourceClass.getDeclaredFields())
				.filter(field -> field.isAnnotationPresent(DataMask.class))
				.forEach(field -> doSensitive(metaObject, field));
	}
	
	
	private void doSensitive(MetaObject metaObject, Field field) {
		// 拿到属性名
		String name = field.getName();
		// 获取属性值
		Object value = metaObject.getValue(name);
		// 
		if (String.class == metaObject.getGetterType(name) && value != null) {
			DataMask annotation = field.getAnnotation(DataMask.class);
			//获取对应的脱敏策略 并进行脱敏
//			SensitiveStrategy type = annotation.strategy();
//			Object o = type.getDesensitizer().apply((String) value);
			// 把脱敏后的值塞回去
//			metaObject.setValue(name, o);
			metaObject.setValue(name,null);
		}
	}
	

	@Override
	public Object plugin(Object target) {
		return Plugin.wrap(target, this);
	}

	@Override
	public void setProperties(Properties properties) {
		//nothing
	}

}
