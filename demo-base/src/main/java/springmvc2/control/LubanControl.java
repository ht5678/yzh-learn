package springmvc2.control;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import springmvc.annotation.Controller;
import springmvc2.mvc.FreemarkeView;
import springmvc2.mvc.MvcMapping;

/**
 * 
 * 
 * 
 * @author yuezh2   2019年10月18日 上午12:20:45
 *
 */
@Controller
public class LubanControl {

	    @MvcMapping("/luban.do")
	    public FreemarkeView openLubanPage(String name) {
	        FreemarkeView freemarkeView = new FreemarkeView("luban.ftl");
	        freemarkeView.setModel("name", name);
	        return freemarkeView;
	    }

	    @MvcMapping("/hello.do")
	    public FreemarkeView open(String name, BlogDoc doc,
	                              HttpServletRequest request, HttpServletResponse resp) {
	        FreemarkeView freemarkeView = new FreemarkeView("lluban.ft");
	        freemarkeView.setModel("name", name);
	        return freemarkeView;
	    }
	}

