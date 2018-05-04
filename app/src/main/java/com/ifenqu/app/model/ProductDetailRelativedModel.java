package com.ifenqu.app.model;

import java.io.Serializable;

public class ProductDetailRelativedModel implements Serializable {
    private String contentLocationType;
    private String content;

    public String getContentLocationType() {
        return contentLocationType;
    }

    public void setContentLocationType(String contentLocationType) {
        this.contentLocationType = contentLocationType;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
