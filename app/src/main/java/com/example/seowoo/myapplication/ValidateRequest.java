package com.example.seowoo.myapplication;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by seowoo on 2018-04-26.
 */

public class ValidateRequest extends StringRequest {

    final static private String URL = "http://alsmwsk3.dothome.co.kr/Android/UserValidate.php";
    private Map<String, String> parameters;

    public ValidateRequest(String userID,  Response.Listener listener
    ){
        super(Method.POST, URL, listener, null);
        parameters = new HashMap<>();
        parameters.put("userID", userID);

    }

    @Override
    public Map<String, String> getParams(){
        return parameters;
    }
}
