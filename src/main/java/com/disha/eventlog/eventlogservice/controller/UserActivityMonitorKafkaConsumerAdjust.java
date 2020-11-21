package com.disha.quickride.eventlogservice.controller;

import com.disha.quickride.eventlogservice.AdjustEntity;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.util.Map;

@Component
@Service
@PropertySource("classpath:KafkaTopics.properties")
public class UserActivityMonitorKafkaConsumerAdjust {
    private static Logger log = Logger.getLogger(UserActivityMonitorKafkaConsumerAdjust.class);
    @Autowired
    AdjustEntity adjustEntity;
    private Gson gson = new Gson();

    @KafkaListener(topics = "${adjust.consumer.message.topic}", groupId = "${adjust.consumer.message.topic.group_id}")
    public void consumeMessage(String message) {
        log.debug("consumeAdjustMessage: " + message);
        Type type = new TypeToken<Map<String, String>>(){}.getType();
        Map<String, String> eventParams = null;
        try {
            JsonObject jsonObject = new JsonParser().parse(message).getAsJsonObject();
            eventParams = gson.fromJson(gson.toJson(jsonObject), type);
            eventParams.remove("eventUniqueField");
        } catch (Exception e) {
            log.debug("Class Name Not found", e);
        }
        try {
            adjustEntity.pushToAdjust(eventParams);
        } catch (Throwable th) {
            log.error("consumeAdjustMessage failed", th);
        }


    }
}
