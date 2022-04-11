package org.demo.netty.store.waiting.listener;

import org.demo.netty.session.Customer;

/**
 * 客户排队监听器
 * @author yuezh2
 */
public interface CustomerWaitStoreListener {

    /**
     * 进入队列
     * @param customer
     * @param queueSize
     */
    void enterQueue(Customer customer, int queueSize);

    /**
     * 离开队列
     * @param customer
     * @param queueSize
     */
    void leaveQueue(Customer customer, int queueSize);
}
