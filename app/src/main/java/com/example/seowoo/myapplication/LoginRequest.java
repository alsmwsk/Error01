package com.example.seowoo.myapplication;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by seowoo on 2018-04-29.
 */

public class LoginRequest extends StringRequest {

    final static private String URL = "http://alsmwsk3.dothome.co.kr/Android/UserLogin.php";
    private Map<String, String> parameters;

    public LoginRequest(String userID, String userPassword,   Response.Listener listener
    ){
        super(Method.POST, URL, listener, null);
        parameters = new HashMap<>();
        parameters.put("userID", userID);
        parameters.put("userPassword", userPassword);
    }

    @Override
    public Map<String, String> getParams(){
        return parameters;
    }
}
