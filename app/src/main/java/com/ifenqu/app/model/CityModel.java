package com.ifenqu.app.model;

import java.io.Serializable;
import java.util.List;

/**
 * 每个省里的市
 */
public class CityModel implements Serializable {
    private String name;
    private String code;
    private List<SuburbModel> sub;

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

    public List<SuburbModel> getSub() {
        return sub;
    }

    public void setSub(List<SuburbModel> sub) {
        this.sub = sub;
    }
}
