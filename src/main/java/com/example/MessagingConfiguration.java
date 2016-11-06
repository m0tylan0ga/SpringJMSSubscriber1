package com.example;

import javax.jms.ConnectionFactory;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.core.JmsTemplate;

import javax.jms.Session;
import org.springframework.jms.annotation.EnableJms;

@Configuration
@EnableJms
public class MessagingConfiguration {

    private static final String BROKER_URL = "tcp://localhost:61616?jms.useAsyncSend=true";
    private final ConnectionFactory connectionFactory = connectionFactory();
    
    public ActiveMQConnectionFactory connectionFactory() {
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory();
        connectionFactory.setTrustAllPackages(true);
        connectionFactory.setBrokerURL(BROKER_URL);
        connectionFactory.setClientID("subscriber-1");
        return connectionFactory;
    }

    @Bean
    public JmsTemplate jmsTemplate() {
        JmsTemplate template = new JmsTemplate();
        template.setConnectionFactory(connectionFactory);
        template.setSessionAcknowledgeMode(Session.AUTO_ACKNOWLEDGE);
        template.setPubSubDomain(true);
        return template;
    }

    @Bean
    public DefaultJmsListenerContainerFactory jmsListenerContainerFactory() {
        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
        factory.setSessionAcknowledgeMode(Session.AUTO_ACKNOWLEDGE);
        factory.setConnectionFactory(connectionFactory);
        factory.setSessionTransacted(true);
        factory.setSubscriptionDurable(true);
        factory.setPubSubDomain(true);
        factory.setConcurrency("5");
        return factory;
    }
}
