package com.disha.quickride.eventlogservice;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class AppsflyerEntity {

    private static Logger log = Logger.getLogger(AppsflyerEntity.class);

    @Value("${appsflyer_base_url}")
    private String baseUrl;
    @Value("${appsflyer_auth}")
    private String auth;
    @Value("${appsflyer_android_app_name}")
    private String androidAppName;
    @Value("${appsflyer_ios_app_name}")
    private String iosAppName;

    private ObjectMapper mapper = new ObjectMapper();
    public void pushtoappsflyer(Map<String, String> event) throws Throwable {
        String basePath = "";
        if (Double.parseDouble(event.get("androidVersion")) > 0 && Double.parseDouble(event.get("iosVersion")) == 0) {
            basePath = baseUrl + androidAppName;
        } else if (Double.parseDouble(event.get("androidVersion")) == 0 && Double.parseDouble(event.get("iosVersion")) > 0) {
            basePath = baseUrl + iosAppName;
        } else if (Double.parseDouble(event.get("androidVersion")) == 0 && Double.parseDouble(event.get("iosVersion")) == 0) {
            basePath = baseUrl + androidAppName;
        } else if (Double.parseDouble(event.get("androidVersion")) > 0 && Double.parseDouble(event.get("iosVersion")) > 0) {
            basePath = baseUrl + androidAppName;
        }

        AppsflyerInputFormat appsflyerInputFormat = new AppsflyerInputFormat();
        Map<String, String> obj = appsflyerInputFormat.reFormat(event);
        HttpClient httpClient = HttpClientBuilder.create().build();
        HttpPost post = new HttpPost(basePath);

        post.addHeader("authentication", auth);
        post.addHeader("Content-Type", "application/json");
        String msg = mapper.writeValueAsString(obj);
        StringEntity messageToSend = new StringEntity(msg);
        messageToSend.setContentType("application/json");
        post.setEntity(messageToSend);

        HttpResponse response = httpClient.execute(post);
        log.debug(response);
    }

}
