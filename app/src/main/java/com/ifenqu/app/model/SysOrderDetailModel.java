package com.ifenqu.app.model;

import java.io.Serializable;
import java.util.Date;

public class SysOrderDetailModel implements Serializable {
    private long orderDetailId;
    private long orderId;
    private long refId;
    /**
     * @param orderType
     * 1. 车险保单
     * 2. 意健险保单
     * 3. 线下订单
     * 4. 大地意健险保单
     * 5. 购买优惠券
     * 6. 违章代缴订单
     * 7. 3C产品订单
     */
    private long orderType;
    private String orderTypeStr;
    private String createTime;
    private long createUserId;
    private long goodsId;
    private long productId;
    private String goodsDesc;

    public long getOrderDetailId() {
        return orderDetailId;
    }

    public void setOrderDetailId(long orderDetailId) {
        this.orderDetailId = orderDetailId;
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

    public long getOrderType() {
        return orderType;
    }

    public void setOrderType(long orderType) {
        this.orderType = orderType;
    }

    public String getOrderTypeStr() {
        return orderTypeStr;
    }

    public void setOrderTypeStr(String orderTypeStr) {
        this.orderTypeStr = orderTypeStr;
    }

    public long getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(long createUserId) {
        this.createUserId = createUserId;
    }

    public long getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(long goodsId) {
        this.goodsId = goodsId;
    }

    public long getProductId() {
        return productId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }

    public String getGoodsDesc() {
        return goodsDesc;
    }

    public void setGoodsDesc(String goodsDesc) {
        this.goodsDesc = goodsDesc;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}
