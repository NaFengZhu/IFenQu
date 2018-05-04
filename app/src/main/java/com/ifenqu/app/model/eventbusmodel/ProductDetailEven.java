package com.ifenqu.app.model.eventbusmodel;

import com.ifenqu.app.model.GoodDetailModel;

public class ProductDetailEven {
    private int type;
    private GoodDetailModel goodDetailModel;

    public ProductDetailEven(GoodDetailModel goodDetailModel,int type) {
        this.goodDetailModel = goodDetailModel;
        this.type = type;
    }

    public GoodDetailModel getGoodDetailModel() {
        return goodDetailModel;
    }

    public void setGoodDetailModel(GoodDetailModel goodDetailModel) {
        this.goodDetailModel = goodDetailModel;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
