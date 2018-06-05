package com.example.seowoo.myapplication;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by seowoo on 2018-04-26.
 */

//php 파일로 데이터를 보낸다.
public class DeleteRequest extends StringRequest {

    final static private String URL = "http://alsmwsk3.dothome.co.kr/Android/ScheduleDelete.php";
    private Map<String, String> parameters;

    public DeleteRequest(String userID, String courseID, Response.Listener listener
                           ){
        super(Method.POST, URL, listener, null);
        parameters = new HashMap<>();
        parameters.put("userID", userID);
        parameters.put("courseID", courseID);
    }

    @Override
    public Map<String, String> getParams(){
        return parameters;
    }
}
