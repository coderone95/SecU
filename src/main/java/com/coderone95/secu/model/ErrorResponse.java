package com.coderone95.secu.model;

public class ErrorResponse  {

    private String message;
    private Status status;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Status getStatus() {
        status.setStatus("ERROR");
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public ErrorResponse(){}
    public ErrorResponse(String message){
        this.message = message;
    }
    public ErrorResponse(String message, Status status){
        this.message = message;
        this.status = status;
    }
}
