package com.t3h.buoi12.model;

public class BaseResponse {
    public static final int SUCCESS = 0;
    public static final int FAIL = 1;

    private int code;
    private String message;

    public static int getSUCCESS() {
        return SUCCESS;
    }

    public static int getFAIL() {
        return FAIL;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
