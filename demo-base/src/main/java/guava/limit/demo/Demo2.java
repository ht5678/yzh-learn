package guava.limit.demo;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.util.concurrent.RateLimiter;

import guava.limit.model.GoodInfo;
 


/**
 * 
 * 抢购场景限流
	譬如我们预估数据库能承受并发10，超过了可能会造成故障，我们就可以对该请求接口进行限流。
	
 * 
 * 
 * 
 * 
 * 
 * @author yuezh2   2018年9月7日 下午5:20:12
 *
 */
@RestController
public class Demo2 {
	
    @Resource(name = "db")
    private GoodInfoService goodInfoService;
 
    RateLimiter rateLimiter = RateLimiter.create(10);
 
    @RequestMapping("/miaosha")
    public Object miaosha(int count, String code) {
        System.out.println("等待时间" + rateLimiter.acquire());
        if (goodInfoService.update(code, count) > 0) {
            return "购买成功";
        }
        return "购买失败";
    }
 
 
 
    @RequestMapping("/add")
    public Object add() {
        for (int i = 0; i < 100; i++) {
            GoodInfo goodInfo = new GoodInfo();
            goodInfo.setCode("iphone" + i);
            goodInfo.setAmount(100);
            goodInfoService.add(goodInfo);
        }
 
        return "添加成功";
    }
}