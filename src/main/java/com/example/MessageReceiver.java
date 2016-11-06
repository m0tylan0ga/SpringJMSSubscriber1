package com.example;

import com.example.models.Subscriber1;
import com.example.models.SubscriberDao;
import com.example.models.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.jms.JMSException;
import org.springframework.messaging.MessageHeaders;

@Component
public class MessageReceiver {
   
    @Autowired
    private SubscriberDao subscriberDao;

    static final Logger log = LoggerFactory.getLogger(MessageReceiver.class);

    private static final String ORDER_TOPIC = "order-topic";

    @Transactional(rollbackFor = RuntimeException.class)
    @JmsListener(destination = ORDER_TOPIC, containerFactory = "jmsListenerContainerFactory", selector = "topic_type like 'BOOKS'")
    public void receiveMessage(final Message<User> message) throws JMSException {
//        throw new RuntimeException("Expected runtime exe thrown");
        MessageHeaders headers = message.getHeaders();
        log.info("Application : headers received : {}", headers);
        User response = message.getPayload();
        Subscriber1 subscriber1 = new Subscriber1();
        subscriber1.setUser(response);
        subscriberDao.create(subscriber1);
        log.info("Application : response received : {}", response);
    }
}
