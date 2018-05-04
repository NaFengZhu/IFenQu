package com.ifenqu.app.http.response;

import com.ifenqu.app.http.Response;
import com.ifenqu.app.model.SplashModel;

import java.util.List;

public class SplashResponse extends Response {
    private List<SplashModel> data;

    public List<SplashModel> getData() {
        return data;
    }

    public void setData(List<SplashModel> data) {
        this.data = data;
    }

    @Override
    public boolean checkDataValidate() {
        if (!super.checkDataValidate())return false;
        if (data == null)return false;
        return true;
    }
}
