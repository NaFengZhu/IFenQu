package com.ifenqu.app.model;

import android.support.annotation.Nullable;

import java.io.Serializable;
import java.util.List;

public class ProductModel implements Serializable {
    private long goodsId;
    private String goodsName;
    private String goodsAbout;
    private String describe;
    /**
     * 对应sys_dictionary_data字典表中主键id
     */
    private int detailType;
    private double amount;
    private long baseType;
    private long subType;
    private long stock;

    // 产品列表所对应的对象
    private long productId;
    private String url;
    private String productName;
    private List<Integer> terms;
    @Nullable
    private double totalPrice;

    public long getProductId() {
        return productId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public List<Integer> getTerms() {
        return terms;
    }

    public void setTerms(List<Integer> terms) {
        this.terms = terms;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    // 产品列表所对应的对象

    public long getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(long goodsId) {
        this.goodsId = goodsId;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getGoodsAbout() {
        return goodsAbout;
    }

    public void setGoodsAbout(String goodsAbout) {
        this.goodsAbout = goodsAbout;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public int getDetailType() {
        return detailType;
    }

    public void setDetailType(int detailType) {
        this.detailType = detailType;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public long getBaseType() {
        return baseType;
    }

    public void setBaseType(long baseType) {
        this.baseType = baseType;
    }

    public long getSubType() {
        return subType;
    }

    public void setSubType(long subType) {
        this.subType = subType;
    }

    public long getStock() {
        return stock;
    }

    public void setStock(long stock) {
        this.stock = stock;
    }
}
