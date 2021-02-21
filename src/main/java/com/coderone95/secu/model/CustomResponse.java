package com.coderone95.secu.model;

import org.json.simple.JSONObject;

import java.io.Serializable;

public class CustomResponse implements Serializable {

    private JSONObject response;

    public CustomResponse(){}
    public CustomResponse(JSONObject jsonObject){
        this.response = jsonObject;
    }

    public JSONObject getResponse() {
        return response;
    }

    public void setResponse(JSONObject response) {
        this.response = response;
    }
}
