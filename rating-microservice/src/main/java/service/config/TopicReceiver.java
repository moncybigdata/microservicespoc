package service.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;

/**
 * Created by stsantia on 4/13/2016.
 */

@Service
public class TopicReceiver {

    private final Logger logger = LoggerFactory.getLogger(TopicReceiver.class);


    @JmsListener(destination = "boot_test/subscriptions/boottest_subscription",
            containerFactory = "topicJmsListenerContainerFactory",
            subscription = "boottest_subscription")
    public void onMessage(String message) {
        logger.info("Received message from topic: {}", message);
        try {
            Thread.sleep(3000L);

        } catch (InterruptedException ex) {
        }

    }


}
