package com.ifenqu.app.http.response;

import com.ifenqu.app.http.Response;
import com.ifenqu.app.model.BannerModel;

import java.util.List;

public class BannerResponse extends Response {
    private List<BannerModel> data;

    public List<BannerModel> getData() {
        return data;
    }

    public void setData(List<BannerModel> data) {
        this.data = data;
    }
}
