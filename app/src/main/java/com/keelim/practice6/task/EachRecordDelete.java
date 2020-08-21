package com.keelim.practice6.task;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;



public class EachRecordDelete extends StringRequest{
    final static private String URL = "hellodeleteEachRec.php";
    private Map<String, String> parameters;

    public EachRecordDelete(String userID, String datetime, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);
        parameters = new HashMap<>();
        parameters.put("userID", userID);
        parameters.put("datetime",datetime);
    }

    @Override
    public Map<String, String> getParams() {
        return parameters;
    }
}
