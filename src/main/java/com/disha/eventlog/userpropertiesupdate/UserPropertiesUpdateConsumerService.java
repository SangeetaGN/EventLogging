package com.disha.quickride.userpropertiesupdate;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class UserPropertiesUpdateConsumerService {

    private static final Logger log = LogManager.getLogger(UserPropertiesUpdateConsumerService.class);

    @Autowired
    private ClevertapRestClient clevertapRestClient;

    @KafkaListener(topics = "${clevertap.topic.user_properties_update}", groupId = "${clevertap.topic.user_properties_update.group_id}")
    public void messageArrivedClevertapTopic(String message) {
        log.debug("messageArrivedClevertapTopic: " + message);
        clevertapRestClient.triggerClevertapEvent(message);
    }
}
