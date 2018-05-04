package com.ifenqu.app.http.response;

import com.ifenqu.app.http.Response;
import com.ifenqu.app.model.ProductModel;

import java.util.List;

public class ProductListResponse extends Response {
    private List<ProductModel> data;

    public List<ProductModel> getData() {
        return data;
    }

    public void setData(List<ProductModel> data) {
        this.data = data;
    }

    @Override
    public boolean checkDataValidate() {
        if (!super.checkDataValidate())return false;
        if (data == null)return false;
        return true;
    }
}
