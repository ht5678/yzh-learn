package springmvc2.control;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * 
 * 
 * @author yuezh2   2019年10月17日 下午10:10:31
 *
 */
public class BlogDoc implements Serializable{

	private String title;
	
	private String author;
	
	private String content;
	
	private Date createTime;
	
	
	

	public BlogDoc(String title, String author, String content, Date createTime) {
		super();
		this.title = title;
		this.author = author;
		this.content = content;
		this.createTime = createTime;
	}
	
	
	

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	
	
}
