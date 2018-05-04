package com.ifenqu.app.http.response;

import com.ifenqu.app.http.Response;

public class OrderResponse extends Response {
    private String data;

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
