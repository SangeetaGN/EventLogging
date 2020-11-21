package com.disha.quickride.eventlogservice;

import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.Map;


public class ClevertapInputFormat {
    public ArrayList<Object> reformat(Map<String,String> params, String uniqueField)
    {
        JSONObject obj = new JSONObject();
        obj.put("FBID",params.get(uniqueField));
        obj.put("type","event");
        obj.put("evtName",params.get("eventName"));
        obj.put("evtData",params);
        ArrayList<Object> event_arr = new ArrayList<>();
        event_arr.add(obj);
        return event_arr;
    }
}
