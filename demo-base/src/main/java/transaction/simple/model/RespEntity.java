package transaction.simple.model;


/**
 * 
 * 
 * 
 * @author yuezh2   2018年10月12日 下午2:40:38
 *
 */
public class RespEntity<T> {
	
	private String key;
	
	private T entity;

	
	
	public T getEntity() {
		return entity;
	}

	public void setEntity(T entity) {
		this.entity = entity;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}
	

}
