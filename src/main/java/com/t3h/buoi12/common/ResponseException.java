package com.t3h.buoi12.common;

public class ResponseException extends RuntimeException{
    private String messageError;
    private int code;

    public ResponseException( String messageError, int code) {
        super(messageError);
        this.messageError = messageError;
        this.code = code;
    }

    public String getMessageError() {
        return messageError;
    }

    public void setMessageError(String messageError) {
        this.messageError = messageError;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
