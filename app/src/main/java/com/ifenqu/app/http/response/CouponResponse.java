package com.ifenqu.app.http.response;

import com.ifenqu.app.http.Response;
import com.ifenqu.app.model.CouponModel;

import java.util.ArrayList;
import java.util.List;

public class CouponResponse extends Response {
    private ArrayList<CouponModel> data;

    public ArrayList<CouponModel> getData() {
        return data;
    }

    public void setData(ArrayList<CouponModel> data) {
        this.data = data;
    }
}
