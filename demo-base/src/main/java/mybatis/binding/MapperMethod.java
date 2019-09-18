package mybatis.binding;


/**
 * 
 * @author yuezh2
 *
 * @date 2019年9月18日 下午9:32:58  
 *
 */
public class MapperMethod<T> {
	
	private String sql;
	
	private Class<T> type;
	
	
	
	public MapperMethod() {
		
	}
	

	public MapperMethod(String sql, Class<T> type) {
		this.sql = sql;
		this.type = type;
	}
	
	
	

	public String getSql() {
		return sql;
	}

	public void setSql(String sql) {
		this.sql = sql;
	}

	public Class<T> getType() {
		return type;
	}

	public void setType(Class<T> type) {
		this.type = type;
	}

	
	
	
}
