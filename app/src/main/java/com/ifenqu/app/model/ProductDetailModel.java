package com.ifenqu.app.model;

import java.io.Serializable;
import java.util.List;

public class ProductDetailModel implements Serializable {
    private long productId;
    private String productName;
    private String productAbout;
    private String describe;
    private int companyId;
    private int productType;
    private int productStatus;
    private List<ProductDetailGoodsModel> goodsList;

    public long getProductId() {
        return productId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductAbout() {
        return productAbout;
    }

    public void setProductAbout(String productAbout) {
        this.productAbout = productAbout;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public int getCompanyId() {
        return companyId;
    }

    public void setCompanyId(int companyId) {
        this.companyId = companyId;
    }

    public int getProductType() {
        return productType;
    }

    public void setProductType(int productType) {
        this.productType = productType;
    }

    public int getProductStatus() {
        return productStatus;
    }

    public void setProductStatus(int productStatus) {
        this.productStatus = productStatus;
    }

    public List<ProductDetailGoodsModel> getGoodsList() {
        return goodsList;
    }

    public void setGoodsList(List<ProductDetailGoodsModel> goodsList) {
        this.goodsList = goodsList;
    }
}
