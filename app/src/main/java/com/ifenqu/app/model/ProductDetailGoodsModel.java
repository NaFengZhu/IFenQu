package com.ifenqu.app.model;

import java.io.Serializable;
import java.util.List;

public class ProductDetailGoodsModel implements Serializable {
    private int goodsId;
    private String goodsName;
    private String goodsAbout;
    private String describe;
    private int detailType;
    private int amount;
    private int stock;
    private int baseType;
    private int subType;
    private List<GoodDetailModel> goodsDictDetailModelList;

    public List<GoodDetailModel> getGoodsDictDetailModelList() {
        return goodsDictDetailModelList;
    }

    public void setGoodsDictDetailModelList(List<GoodDetailModel> goodsDictDetailModelList) {
        this.goodsDictDetailModelList = goodsDictDetailModelList;
    }

    public int getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(int goodsId) {
        this.goodsId = goodsId;
    }

    public int getDetailType() {
        return detailType;
    }

    public void setDetailType(int detailType) {
        this.detailType = detailType;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public int getBaseType() {
        return baseType;
    }

    public void setBaseType(int baseType) {
        this.baseType = baseType;
    }

    public int getSubType() {
        return subType;
    }

    public void setSubType(int subType) {
        this.subType = subType;
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

}
