package utils;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

/**
 * 
 * @author yuezh2
 *
 * @date 2020年10月30日 下午4:18:55  
 *
 */
public class InstantUtil {

	public static final ZoneId OFP_TIMEZONE = ZoneId.of("Asia/Shanghai");
	
	
	
	public static void main(String[] args) {
		Instant curTime = Instant.now();
		System.out.println(curTime);
		
		
		curTime = Instant.now().plusMillis(TimeUnit.HOURS.toMillis(8));
		System.out.println(curTime);
		
		
		curTime = Instant.now();
		System.out.println("秒数:"+curTime.getEpochSecond());
		System.out.println("毫秒数:"+curTime.toEpochMilli());
		
		
		
		System.out.println(Instant.now().atZone(OFP_TIMEZONE).toEpochSecond());
		
		
		TimeZone zone = TimeZone.getDefault();  
		System.out.println(zone.getRawOffset()/(60*60*1000));  
		System.out.println(zone.getDisplayName());
		
		
		
		
		System.out.println(ZonedDateTime.ofInstant(Instant.now(),ZoneId.systemDefault()).toInstant());
//		System.out.println(ZonedDateTime.ofInstant(Instant.now(),"Australia/Darwin").toInstant());
	}
	
}
