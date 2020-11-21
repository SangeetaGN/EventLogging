package com.disha.quickride.eventlogservice.controller;

import com.disha.quickride.util.kafka.QRKafkaProducer;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserActivityMonitorKafkaProducer {
    private static Logger log = Logger.getLogger(UserActivityMonitorKafkaProducer.class);

    @Autowired
    private QRKafkaProducer qrkafkaProducer;

    public void sendMessage(String payload, String topicKafkaProducer) {
        log.debug("Sending to kafka()payload :"+payload+"topic :"+topicKafkaProducer);
        qrkafkaProducer.sendMessage(topicKafkaProducer, payload);
    }
}
