package com.ifenqu.app.model;

import com.blankj.utilcode.util.LogUtils;
import com.ifenqu.app.http.Response;


public class ProductCouponTagResponse extends Response {
    private String data;

    public String[] getData() {
        String result = "";
        if (data.endsWith(",")) {
            result = data.substring(0, data.lastIndexOf(","));
            LogUtils.d("result- 类型" + result);
        }
        return result.split(",");
    }

    public void setData(String data) {
        this.data = data;
    }
}
