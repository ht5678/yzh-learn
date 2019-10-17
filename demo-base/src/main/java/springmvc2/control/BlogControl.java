package springmvc2.control;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;

import springmvc2.mvc.FreemarkeView;
import springmvc2.mvc.MvcMapping;

/**
 * 
 * 
 * 
 * @author yuezh2   2019年10月17日 下午10:12:17
 *
 */
@Controller
public class BlogControl {

	List<BlogDoc> docs = new ArrayList<>();
	
	
	@MvcMapping("/edit")
	public FreemarkeView openEditPages(String user){
		FreemarkeView freemarkerView = new FreemarkeView("edit.ftl");
		freemarkerView.setModel("authorName", user);
		freemarkerView.setModel("user", user);
		return freemarkerView;
	}
	
	
	@MvcMapping("/list")
	public FreemarkeView openDocList(String author){
		List<BlogDoc> result = new ArrayList<>();
		if(author != null){
			for(BlogDoc doc : docs){
				if(author.equals(doc.getAuthor())){
					result.add(doc);
				}
			}
		}else{
			result.addAll(docs);
		}
		FreemarkeView freemarkeView = new FreemarkeView("docList.ftl");
		freemarkeView.setModel("authorName", author);
		freemarkeView.setModel("docs", result);
		return freemarkeView;
	}
	
	
	
	@MvcMapping("/save")
	public void openEditPage(String title,String author,String content,HttpServletResponse resp){
		BlogDoc doc = new BlogDoc(title, author, content, new Date());
		docs.add(doc);
		
		try {
			resp.sendRedirect("/list");
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	
	
	
}
