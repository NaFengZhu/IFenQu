package com.ifenqu.app.model;

import java.io.Serializable;
import java.util.Date;

public class OrderDeliveryInfo implements Serializable {
    private long deliveryId;
    private long userId;
    private long orderId;
    /**
     * 订单详情ID数组，格式“，20，21，22，”
     */
    private String orderDetaildIdList;
    private long addressId;
    private String deliveryTime;
    private String deliveryNumber;
    private long deliveryCompanyId;
    private long deliveryStatus;
    private long createUserId;
    private String createTime;
    private long updateUserId;
    private String updateTime;

    public long getDeliveryId() {
        return deliveryId;
    }

    public void setDeliveryId(long deliveryId) {
        this.deliveryId = deliveryId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getOrderId() {
        return orderId;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

    public String getOrderDetaildIdList() {
        return orderDetaildIdList;
    }

    public void setOrderDetaildIdList(String orderDetaildIdList) {
        this.orderDetaildIdList = orderDetaildIdList;
    }

    public long getAddressId() {
        return addressId;
    }

    public void setAddressId(long addressId) {
        this.addressId = addressId;
    }

    public String getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(String deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    public String getDeliveryNumber() {
        return deliveryNumber;
    }

    public void setDeliveryNumber(String deliveryNumber) {
        this.deliveryNumber = deliveryNumber;
    }

    public long getDeliveryCompanyId() {
        return deliveryCompanyId;
    }

    public void setDeliveryCompanyId(long deliveryCompanyId) {
        this.deliveryCompanyId = deliveryCompanyId;
    }

    public long getDeliveryStatus() {
        return deliveryStatus;
    }

    public void setDeliveryStatus(long deliveryStatus) {
        this.deliveryStatus = deliveryStatus;
    }

    public long getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(long createUserId) {
        this.createUserId = createUserId;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public long getUpdateUserId() {
        return updateUserId;
    }

    public void setUpdateUserId(long updateUserId) {
        this.updateUserId = updateUserId;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }
}
