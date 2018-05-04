package com.ifenqu.app.model;

import java.io.Serializable;

public class ConfirmBusinessModel implements Serializable {
    private String productId;
    private String goodsId;
    private String productName;
    private String totalPrice;
    private String discount;
    private GoodDetailModel colorModel;
    private GoodDetailModel styleModel;

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public GoodDetailModel getColorModel() {
        return colorModel;
    }

    public void setColorModel(GoodDetailModel colorModel) {
        this.colorModel = colorModel;
    }

    public GoodDetailModel getStyleModel() {
        return styleModel;
    }

    public void setStyleModel(GoodDetailModel styleModel) {
        this.styleModel = styleModel;
    }
}
