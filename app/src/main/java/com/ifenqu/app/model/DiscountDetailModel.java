package com.ifenqu.app.model;

import android.icu.math.BigDecimal;

import java.io.Serializable;
import java.util.Date;

/**
 * 订单列表返回的discount detail 类
 */
public class DiscountDetailModel implements Serializable {
    private long orderDiscountDetailId;
    private long orderId;
    private long refId;
    private long discountType;
    private double amount;
    private String createTime;
    private long createUserId;
    private long productDiscountDetailId;
    private String ticketCode;

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public long getOrderDiscountDetailId() {
        return orderDiscountDetailId;
    }

    public void setOrderDiscountDetailId(long orderDiscountDetailId) {
        this.orderDiscountDetailId = orderDiscountDetailId;
    }

    public long getOrderId() {
        return orderId;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

    public long getRefId() {
        return refId;
    }

    public void setRefId(long refId) {
        this.refId = refId;
    }

    public long getDiscountType() {
        return discountType;
    }

    public void setDiscountType(long discountType) {
        this.discountType = discountType;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public long getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(long createUserId) {
        this.createUserId = createUserId;
    }

    public long getProductDiscountDetailId() {
        return productDiscountDetailId;
    }

    public void setProductDiscountDetailId(long productDiscountDetailId) {
        this.productDiscountDetailId = productDiscountDetailId;
    }

    public String getTicketCode() {
        return ticketCode;
    }

    public void setTicketCode(String ticketCode) {
        this.ticketCode = ticketCode;
    }
}
