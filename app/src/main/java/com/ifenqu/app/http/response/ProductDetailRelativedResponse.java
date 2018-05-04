package com.ifenqu.app.http.response;

import com.ifenqu.app.http.Response;
import com.ifenqu.app.model.ProductDetailRelativedModel;

import java.util.List;


public class ProductDetailRelativedResponse extends Response {
    private List<ProductDetailRelativedModel> data;

    public List<ProductDetailRelativedModel> getData() {
        return data;
    }

    public void setData(List<ProductDetailRelativedModel> data) {
        this.data = data;
    }
}
