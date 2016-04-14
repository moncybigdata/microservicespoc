package io.github.stansa.microservices.movies.service.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import javax.jms.Session;

/**
 * Created by stsantia on 4/13/2016.
 */
@Service
public class TopicPublisher {

    private final Logger logger = LoggerFactory.getLogger(TopicPublisher.class);

    @Autowired
    private JmsTemplate jmsTemplate;

    public void sendPong() {
        logger.info("Sending pong");
        jmsTemplate.send("boot_test", (Session session) -> session.createTextMessage("pong"));
    }

}
