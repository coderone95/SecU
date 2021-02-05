package com.coderone95.secu.model;

public class SuccessResponse {
    private String message;
    private Status status;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Status getStatus() {
        status.setStatus("SUCCESS");
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public SuccessResponse(){}
    public SuccessResponse(String message){
        this.message = message;
    }
    public SuccessResponse(String message, Status status){
        this.message = message;
        this.status = status;
    }
    public SuccessResponse(String message, String s){
        this.message = message;
        this.status = new Status(s);
    }
}
