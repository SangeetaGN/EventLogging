package com.disha.quickride.eventlogservice.controller;

import com.disha.quickride.install.UserAcivityEventService;
import com.disha.quickride.util.response.RestResponseHandler;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.ws.rs.core.Response;

@RestController
@RequestMapping("/UserActivityMonitor")
@PropertySource("classpath:log4j.properties")
@PropertySource("classpath:KafkaTopics.properties")
public class EventLogResource {
    private static Logger log = Logger.getLogger(EventLogResource.class);

    @Autowired
    private CheckChannelAndProduce checkChannelAndProduce;
    @Autowired
    private UserAcivityEventService userAcivityEventService;

    @RequestMapping(value = "/Events", method = RequestMethod.POST)
    public Response sendAppEvents(@RequestBody String payload) {
        log.debug("sendAppEvents() "+payload);
        Response.ResponseBuilder output = null;
        try {
            checkChannelAndProduce.checkChannel(payload);
            output = RestResponseHandler.buildQrServiceSuccessResponse(null);
        } catch (Throwable e) {
            log.error("sendAppEvents() failed ", e);
            output = RestResponseHandler.buildQrServiceSuccessResponse(null);
        }
        return output.build();
    }

    @RequestMapping(value = "/installEvent", method = RequestMethod.POST)
    public Response sendInstallEvents(@RequestBody String installEvent) {
        log.debug("storeInstallEvent() {}" + installEvent);
        Response.ResponseBuilder output = null;
        try {
            userAcivityEventService.saveInstallEvents(installEvent);
            output = RestResponseHandler.buildQrServiceSuccessResponse(null);
        } catch (Throwable e) {
            log.error("storeInstallEvent() failed ", e);
            output = RestResponseHandler.buildQrServiceSuccessResponse(null);
        }
        return output.build();
    }

    @RequestMapping(value = "/UpdateUserProperties", method = RequestMethod.POST)
    public Response updateUserProperties(@RequestBody String userProperties) {
        log.debug("updateUserProperties() {}" + userProperties);
        Response.ResponseBuilder output = null;
        try {
            userAcivityEventService.publishUserPropertiesUpdate(userProperties);
            output = RestResponseHandler.buildQrServiceSuccessResponse(null);
        } catch (Throwable e) {
            log.error("updateUserProperties() failed ", e);
            output = RestResponseHandler.buildQrServiceSuccessResponse(null);
        }
        return output.build();
    }


}
