package com.ifenqu.app.model;

import java.io.Serializable;

public class GoodDetailModel implements Serializable {
    private int goodsId;
    private int baseTypeDictId;
    private int subTypeDictId;
    private String name;//非接口返回变量，用于在选取产品款项的时候展示类型名称
    private boolean isSelected;

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(int goodsId) {
        this.goodsId = goodsId;
    }

    public int getBaseTypeDictId() {
        return baseTypeDictId;
    }

    public void setBaseTypeDictId(int baseTypeDictId) {
        this.baseTypeDictId = baseTypeDictId;
    }

    public int getSubTypeDictId() {
        return subTypeDictId;
    }

    public void setSubTypeDictId(int subTypeDictId) {
        this.subTypeDictId = subTypeDictId;
    }
}
