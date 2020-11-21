package com.disha.quickride.eventlogservice.controller;

import com.disha.quickride.eventlogservice.ClevertapEntity;
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
public class UserActivityMonitorKafkaConsumerClevertap {
    private static Logger log = Logger.getLogger(UserActivityMonitorKafkaConsumerClevertap.class);
    private Gson gson = new Gson();

    @Autowired
    ClevertapEntity clevertapEntity;

    @KafkaListener(topics = "${clevertap.consumer.message.topic}", groupId = "${clevertap.consumer.message.topic.group_id}")
    public void consumeMessage(String message)
    {
        log.debug("consumeEmail: " + message);
        Type type = new TypeToken<Map<String, String>>(){}.getType();
        Map<String,String> eventParams = null;
        String uniqueField = null;
        try {
            JsonObject jsonObject = new JsonParser().parse(message).getAsJsonObject();
            eventParams = gson.fromJson(gson.toJson(jsonObject), type);
            uniqueField = eventParams.get("eventUniqueField");
            eventParams.remove("eventUniqueField");
        }
        catch (Exception e)
        {
            log.debug("Class Name Not found", e);
        }

       try {
            clevertapEntity.pushtoclevertap(eventParams,uniqueField);
        }
        catch (Throwable th)
        {
            log.error("Error occurred",th);
        }

    }


}
