package com.ifenqu.app.model;

import java.io.Serializable;

public class BannerModel implements Serializable {
    private int dicDataId;
    private String content;
    private String ur;
    private String type;
    private int sort;
    private int status;

    public int getDicDataId() {
        return dicDataId;
    }

    public void setDicDataId(int dicDataId) {
        this.dicDataId = dicDataId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUr() {
        return ur;
    }

    public void setUr(String ur) {
        this.ur = ur;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
