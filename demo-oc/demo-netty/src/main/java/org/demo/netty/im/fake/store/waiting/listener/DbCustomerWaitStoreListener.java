package org.demo.netty.im.fake.store.waiting.listener;

import org.demo.netty.im.fake.provider.TeamMonitorProvider;
import org.demo.netty.im.fake.session.Customer;

/**
 * 同步数据库
 * @author yuezh2
 */
public class DbCustomerWaitStoreListener implements CustomerWaitStoreListener {

    @Override
    public void enterQueue(Customer customer, int queueSize) {
        TeamMonitorProvider.getInst().updateWaitCount(customer.getTenantCode(), customer.getTeamCode(), queueSize);
    }

    @Override
    public void leaveQueue(Customer customer, int queueSize) {
        TeamMonitorProvider.getInst().updateWaitCount(customer.getTenantCode(), customer.getTeamCode(), queueSize);
    }
}
