package mybatis.binding;

import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @author yuezh2
 *
 * @date 2019年9月18日 下午9:15:11  
 *
 */
public class MapperRegistry {
	
	private Map<String, MapperMethod> knownMappers = new HashMap<String, MapperMethod>();

	
	
	public void setKnownMappers(Map<String, MapperMethod> knownMappers) {
		this.knownMappers = knownMappers;
	}

	public Map<String, MapperMethod> getKnownMappers() {
		return knownMappers;
	}

	

}
