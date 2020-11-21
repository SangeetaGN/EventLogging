package com.disha.quickride.install;

import com.disha.quickride.eventlogservice.controller.UserActivityMonitorKafkaProducer;
import com.disha.quickride.result.QuickRideException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class UserAcivityEventService {
    private static Logger log = Logger.getLogger(UserAcivityEventService.class);

    @Value("${install.events.message.topic}")
    private String topicName;

    @Value("${clevertap.topic.user_properties_update}")
    private String userPropertiesUpdateTopic;

    @Autowired
    private UserActivityMonitorKafkaProducer userActivityMonitorKafkaProducer;

    public void saveInstallEvents(String userInstallEvent) {
        log.debug("saveInstallEvents()" + userInstallEvent);
        try {
            userActivityMonitorKafkaProducer.sendMessage(userInstallEvent, topicName);
        } catch (QuickRideException e) {
            log.error("saveInstallEvents() failed" + userInstallEvent, e);
        }
    }

    public void publishUserPropertiesUpdate(String userProperties) {
        log.debug("publishUserPropertiesUpdate()" + userProperties);
        try {
            userActivityMonitorKafkaProducer.sendMessage(userProperties, userPropertiesUpdateTopic);
        } catch (QuickRideException e) {
            log.error("publishUserPropertiesUpdate() failed" + userProperties, e);
        }
    }
}
