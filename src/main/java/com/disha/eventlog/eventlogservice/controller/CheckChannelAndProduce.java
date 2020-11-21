package com.disha.quickride.eventlogservice.controller;

import com.disha.quickride.domain.model.EventEntity;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.util.*;

@Component
public class CheckChannelAndProduce {
    private static Logger log = Logger.getLogger(CheckChannelAndProduce.class);

    @Autowired
    private UserActivityMonitorKafkaProducer userActivityMonitorKafkaProducer;

    @Value("${clevertap.consumer.message.topic}")
    private String cleverTapTopic;

    @Value("${appsflyer.consumer.message.topic}")
    private String appsFlyerTopic;

    @Value("${adjust.consumer.message.topic}")
    private String adjustTopic;
    private List<String> eventChannel;
    private String Appsflyer = "Appsflyer";
    private String Clevertap = "Clevertap";
    private String Adjust = "Adjust";
    private String Database = "Database";

    public void checkChannel(String eventString) {
        EventEntity eventEntity = null;
        ObjectMapper mapper = new ObjectMapper();
        try {
            eventEntity = mapper.readValue(eventString, EventEntity.class);
            String topicKafkaChannel = null;
            List<String> eventChannelTemp = clientEventChannel(eventEntity.getEventName());
            if(eventChannelTemp.size()==0) {
                log.error("Event not found");
            }
            else
            {
                eventChannel = eventChannelTemp;
            }


            for (String channelName : eventChannel) {
                if (Clevertap.equalsIgnoreCase(channelName)) {
                    topicKafkaChannel = cleverTapTopic;
                } else if (Appsflyer.equalsIgnoreCase(channelName)) {
                    topicKafkaChannel = appsFlyerTopic;
                } else if (Adjust.equalsIgnoreCase(channelName)) {
                    topicKafkaChannel = adjustTopic;
                } else if (Database.equalsIgnoreCase(channelName)) {
                    topicKafkaChannel = "qrdb." + eventEntity.getEventName();
                }
                if (topicKafkaChannel != null)
                    userActivityMonitorKafkaProducer.sendMessage(eventString, topicKafkaChannel);
            }
        } catch (Throwable e) {
            log.error("checkChannel failed ", e);
        }
    }

    private List<String> clientEventChannel(String eventName)
    {
        Map<String,String> propertyMap = new HashMap();
        Properties p = new Properties();
        try
        {
            InputStream inputStream= this.getClass().getResourceAsStream("/clientEvents.properties");
            p.load(inputStream);
            Enumeration keys = p.propertyNames();
            while(keys.hasMoreElements()) {
                String key = (String)keys.nextElement();
                propertyMap.put(key,p.getProperty(key));
            }
            inputStream.close();
        }
        catch (Exception e)
        {
            log.error("getProperties Failed", e);
        }
        String channelName = propertyMap.get(eventName);
        List<String> channelNames = Arrays.asList(channelName.split(","));
        return channelNames;
    }

}
