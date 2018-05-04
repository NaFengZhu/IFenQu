package com.ifenqu.app.model;

import java.io.Serializable;

/**
 * 每个市里的区
 */
public class SuburbModel implements Serializable {
    private String name;
    private String code;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
