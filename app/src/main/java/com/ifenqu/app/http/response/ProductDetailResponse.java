package com.ifenqu.app.http.response;

import com.ifenqu.app.http.Response;
import com.ifenqu.app.model.ProductDetailModel;

public class ProductDetailResponse extends Response {
    private ProductDetailModel data;

    public ProductDetailModel getData() {
        return data;
    }

    public void setData(ProductDetailModel data) {
        this.data = data;
    }

    @Override
    public boolean checkDataValidate() {
        return super.checkDataValidate();
    }
}
