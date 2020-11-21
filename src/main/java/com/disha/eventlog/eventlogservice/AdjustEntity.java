package com.disha.quickride.eventlogservice;

import com.google.gson.Gson;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

@Component
public class AdjustEntity {
    @Autowired
    AdjustInputFormat adjustInputFormat;

    private static Logger log = Logger.getLogger(AdjustEntity.class);
    @Value("${adjust_base_url}")
    private String baseUrl;

    public void pushToAdjust(Map<String,String> eventParamsObject) throws Throwable
    {
        Map<String,String> eventParams =new HashMap<String,String>();
        for (Map.Entry<String, String> entry : eventParamsObject.entrySet()) {
                eventParams.put(entry.getKey(),entry.getValue());
        }

        String event_token = adjustInputFormat.adjustEventName(eventParams.get("eventName"));
        eventParams.remove("eventName");
        eventParams.remove("androidVersion");
        eventParams.remove("iosVersion");
        eventParams.remove("appsflyerid");
        String body = "";
        //String body = "s2s=1&event_token="+event_token+"&app_token=acqv6qndmy9s&idfa="+ "C7767A6B-B19A-46C4-960B-5921477EDC3F" +"&callback_params=";
        if(eventParams.containsKey("idfa")){
            body = "s2s=1&event_token="+event_token+"&app_token=acqv6qndmy9s&idfa="+ eventParams.get("idfa") +"&callback_params=";
            eventParams.remove("idfa");
        }
        else if(eventParams.containsKey("advertisingId")){
            body = "s2s=1&event_token="+event_token+"&app_token=acqv6qndmy9s&idfa="+ eventParams.get("advertisingId") +"&callback_params=";
            eventParams.remove("advertisingId");
        }

        Gson gson = new Gson();
        String json_string = gson.toJson(eventParams);
        String encoded_string = URLEncoder.encode(json_string,"UTF-8");
        body = body + encoded_string;
        StringEntity messageToSend = new StringEntity(body);
        messageToSend.setContentType("application/x-www-form-urlencoded");
        HttpClient httpClient = HttpClientBuilder.create().build();
        HttpPost post = new HttpPost(baseUrl);

        post.setEntity(messageToSend);

        HttpResponse response = httpClient.execute(post);
        log.debug(response);

    }
}
