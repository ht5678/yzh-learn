package dubbo;

import org.springframework.stereotype.Component;

/**
 * 
 * @author yuezh2
 *
 * @date 2019年11月27日 下午3:01:54  
 *
 */
@Component("demoDubboImpl")
public class DemoDubboImpl implements DemoDubbo {

	@Override
	public void hello() {
		System.out.println("hello");
		
	}

}
