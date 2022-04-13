/**
 * 
 */
package org.demo.netty.im.fake.dispatcher.room;

import java.util.List;

import org.demo.netty.im.fake.session.Customer;

/**
 * @Description:
 * @author pengzq1
 * @createDate 2019年2月25日
 * @version v 1.0
 */
public interface CustomerRoom {

	int addWait(Customer customer);

	Customer acquire(String teamCode);
	
	void removeWait(Customer customer);
	
	List<Customer> getWaits(String teamCode);

	int size(String teamCode);
}
