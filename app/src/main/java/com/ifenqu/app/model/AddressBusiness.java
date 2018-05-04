package com.ifenqu.app.model;

import java.io.Serializable;

public class AddressBusiness implements Serializable {
    private ProvinceModel province;
    private CityModel city;
    private SuburbModel area;
    private String addressCode;
    private String phone;
    private String name;
    private String specificAddress;

    public String getAddressCode() {
        return addressCode;
    }

    public void setAddressCode(String addressCode) {
        this.addressCode = addressCode;
    }

    public ProvinceModel getProvince() {
        return province;
    }

    public void setProvince(ProvinceModel province) {
        this.province = province;
    }

    public CityModel getCity() {
        return city;
    }

    public void setCity(CityModel city) {
        this.city = city;
    }

    public SuburbModel getArea() {
        return area;
    }

    public void setArea(SuburbModel area) {
        this.area = area;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSpecificAddress() {
        return specificAddress;
    }

    public void setSpecificAddress(String specificAddress) {
        this.specificAddress = specificAddress;
    }
}
