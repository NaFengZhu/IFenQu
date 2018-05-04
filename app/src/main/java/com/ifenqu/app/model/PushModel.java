package com.ifenqu.app.model;

import java.io.Serializable;

public class PushModel extends IBaseModel implements Serializable {
    private String imageUrl; //展示的商品图片
    private String name;//展示的商品名称
    private long time;//展示的时间
    private String webUrl;//点击产看详情的url

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getWebUrl() {
        return webUrl;
    }

    public void setWebUrl(String webUrl) {
        this.webUrl = webUrl;
    }
}
