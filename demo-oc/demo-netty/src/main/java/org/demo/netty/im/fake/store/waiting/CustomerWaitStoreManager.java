package org.demo.netty.im.fake.store.waiting;

import java.util.ArrayList;
import java.util.List;

import org.demo.netty.im.fake.session.Customer;
import org.demo.netty.im.fake.store.waiting.listener.CustomerWaitStoreListener;
import org.demo.netty.im.fake.store.waiting.listener.DbCustomerWaitStoreListener;
import org.demo.netty.im.fake.store.waiting.listener.KafkaCustomerWaitStoreListener;

/**
 * 客户排队统计
 * @author yuezh2
 */
public class CustomerWaitStoreManager {

    private static List<CustomerWaitStoreListener> listeners = new ArrayList<>();

    private CustomerWaitStoreManager() {
        registerListener(new KafkaCustomerWaitStoreListener());
        registerListener(new DbCustomerWaitStoreListener());
    }

    /**
     * 注册消息监听
     * @param listener
     */
    public void registerListener(CustomerWaitStoreListener listener) {
        listeners.add(listener);
    }

    public void enterQueue(Customer customer, int queueSize) {
        //同步消息到监听者
        for (CustomerWaitStoreListener customerWaitStoreListener : listeners) {
            customerWaitStoreListener.enterQueue(customer, queueSize);
        }
    }

    public void leaveQueue(Customer customer, int queueSize) {
        //同步消息到监听者
        for (CustomerWaitStoreListener customerWaitStoreListener : listeners) {
            customerWaitStoreListener.leaveQueue(customer, queueSize);
        }
    }

    public static CustomerWaitStoreManager getInst() {
        return Single.inst;
    }

    private static class Single {
        private static CustomerWaitStoreManager inst = new CustomerWaitStoreManager();
    }
}
