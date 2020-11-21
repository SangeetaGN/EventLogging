package com.disha.quickride.eventlogservice;

import org.json.simple.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class AppsflyerInputFormat {

    public Map<String,String> reFormat(Map<String,String> params)
    {
        Map<String,String> obj = new HashMap<String,String>();
        obj.put("appsflyer_id",params.get("appsflyerid"));
        //obj.put("appsflyer_id","1577863919483-6005070665390876377");
        obj.put("eventName",params.get("eventName"));
        if(params.containsKey("idfa")){
            obj.put("idfa",params.get("idfa"));
            params.remove("idfa");
        }
        else if(params.containsKey("advertisingId")){
            obj.put("idfa",params.get("advertisingId"));
            params.remove("advertisingId");
        }

        params.remove("eventName");
        params.remove("appsflyerid");

        obj.put("af_events_api","true");
        obj.put("eventValue",new JSONObject(params).toString());
        return obj;
    }
}
