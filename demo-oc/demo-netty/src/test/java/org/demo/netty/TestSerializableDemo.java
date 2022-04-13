package org.demo.netty;

import org.demo.netty.im.fake.domain.Body;
import org.demo.netty.im.fake.domain.BodyType;
import org.demo.netty.im.fake.util.ClusterExternalizableUtil;
import org.demo.netty.im.fake.util.ExternalizableUtil;

/**
 * 
 * @author yuezh2
 * @date	  2022年3月14日 下午3:16:01
 */
public class TestSerializableDemo {

	
	
	public static void main(String[] args) throws Exception{
		
		ExternalizableUtil.getInstance().setStrategy(new ClusterExternalizableUtil());
		
		
		Body body = new Body();
		body.setContent("test1");
		body.setType(BodyType.ABORT);
        
        /**
         * 将book对象序列化到book.temp文件中去
         */
        String fileName = "d://body-extralizable.temp";
        SerializationUtil.serialize(body, fileName);
        
        /**
         * 从book.temp文件中，反序列化一个Book对象
         */
        Body deserializedBook = (Body) SerializationUtil.deserialize(fileName);
        //deserializedBook==>Book [name=Hello Java, isbn=ABC123456789, authors=[John, Eric]]
        System.out.println("deserializedBook==>" + deserializedBook);
	}
	
}
