package com.coderone95.secu.model;

import java.io.Serializable;

public class Status implements Serializable {
    private String status;

    public String getStatus() {
        return status;
    }

    public Status(){}
    public Status(String status){
        this.status = status;
    }


    public void setStatus(String status) {
        this.status = status;
    }
}
