package com.ifenqu.app.http;

/**
 * Created by zhunafeng on 16/3/18.
 */

public class Response {
    private int code;
    private String message;

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

    public boolean checkDataValidate(){
        if (code != 0)return false;
        return true;
    }
}
