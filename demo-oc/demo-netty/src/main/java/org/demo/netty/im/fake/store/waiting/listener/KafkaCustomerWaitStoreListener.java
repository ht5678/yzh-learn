package org.demo.netty.im.fake.store.waiting.listener;

import java.io.IOException;

import org.apache.kafka.clients.KafkaClient;
import org.demo.netty.im.fake.provider.context.SpringContext;
import org.demo.netty.im.fake.session.Customer;
import org.demo.netty.im.fake.util.JsonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 同步kafka
 * @author pengzq1
 */
public class KafkaCustomerWaitStoreListener implements CustomerWaitStoreListener {

    private static Logger log = LoggerFactory.getLogger(KafkaCustomerWaitStoreListener.class);

    private static String END_CHAT_TOPIC = "ocim-customer-queue";
    private static KafkaClient kafkaClient = SpringContext.getBean(KafkaClient.class);

    @Override
    public void enterQueue(Customer customer, int queueSize) {
//        try {
//            kafkaClient.sendMsg(END_CHAT_TOPIC, JsonUtils.getJson().writeString(customer));
//        } catch (IOException e) {
//            log.info("Object to json string error: {}", e.getMessage());
//        }
    }

    @Override
    public void leaveQueue(Customer customer, int queueSize) {

    }
}
