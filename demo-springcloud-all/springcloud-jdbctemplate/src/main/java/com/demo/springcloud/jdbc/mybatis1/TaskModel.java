package com.demo.springcloud.jdbc.mybatis1;


/**
 * 
 * 
 * 
 * @author yuezh2   2018年9月20日 下午4:23:03
 *
 */
public class TaskModel {

	
	private Integer id ;
	
    private String cronExpression;
    
    private String description;
    
    
    
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getCronExpression() {
		return cronExpression;
	}
	public void setCronExpression(String cronExpression) {
		this.cronExpression = cronExpression;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	@Override
	public String toString() {
		return "TaskModel [id=" + id + ", cronExpression=" + cronExpression + ", description=" + description + "]";
	}
    
    
}
