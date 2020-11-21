package com.disha.quickride.eventlogservice;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Map;

@Component
public class ClevertapEntity {
    private static Logger log = Logger.getLogger(ClevertapEntity.class);

    @Value("${clevertap_base_url}")
    private String baseUrl;

    public void pushtoclevertap(Map<String,String> event, String uniqueField) throws Throwable
    {
        ClevertapInputFormat inputFormat = new ClevertapInputFormat();
        ArrayList<Object> event_arr= inputFormat.reformat(event, uniqueField);

        JSONObject obj = new JSONObject();
        obj.put("d",event_arr);
        HttpClient httpClient = HttpClientBuilder.create().build();
        HttpPost post = new HttpPost(baseUrl);

        StringEntity messageToSend = new StringEntity(obj.toString());
        messageToSend.setContentType("application/json");
        post.setEntity(messageToSend);
        post.setHeader("X-CleverTap-Account-Id", "677-74Z-765Z");
        post.setHeader("X-CleverTap-Passcode", "WFQ-BAD-IEKL");

        HttpResponse response = httpClient.execute(post);
        log.debug(response);
    }

}
