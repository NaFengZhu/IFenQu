package com.ifenqu.app.model;

import com.contrarywind.interfaces.IPickerViewData;

import java.io.Serializable;
import java.util.List;

/**
 * 每个国家的省
 */
public class ProvinceModel implements Serializable, IPickerViewData {
    private String name;
    private String code;
    private List<CityModel> sub;

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

    public List<CityModel> getSub() {
        return sub;
    }

    public void setSub(List<CityModel> sub) {
        this.sub = sub;
    }

    @Override
    public String getPickerViewText() {
        return getName();
    }
}
