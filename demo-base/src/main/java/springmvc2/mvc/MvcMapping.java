package springmvc2.mvc;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 
 * 
 * 
 * @author yuezh2   2019年10月15日 下午11:11:20
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(value=ElementType.METHOD)
public @interface MvcMapping {

	public String value();
	
	public String contentType() default "JSON";
	
}
