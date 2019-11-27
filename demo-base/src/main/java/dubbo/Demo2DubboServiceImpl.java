package dubbo;

import com.alibaba.dubbo.config.annotation.Service;

/**
 * 
 * @author yuezh2
 *
 * @date 2019年11月27日 下午3:46:39  
 *
 */
@Service
public class Demo2DubboServiceImpl implements Demo2DubboService {

	@Override
	public void test2() {
		System.out.println("test2");
	}

}
