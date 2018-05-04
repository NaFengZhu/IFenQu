package com.ifenqu.app.model;

import java.io.Serializable;
import java.util.List;

public class SysOrderModel extends IBaseModel implements Serializable {
    public static final String PAY_STATUS_DELETED = "0";
    public static final String PAY_STATUS_UNPAY = "1";
    public static final String PAY_STATUS_PAID = "2";
    private long orderId;
    private long userId;
    private long ownerId;
    private long addressId;
    private AccountAddressModel accountAddress;
    private List<SysOrderDetailModel> sysOrderDetailList;
    private List<DiscountDetailModel> discountDetailList;
    private double amount;
    private double settlementAmount;
    /**
     * 订单状态
     * 1: 新建；
     * 2：支付成功，未对账；
     * 3：支付成功，已对账；
     * 4：支付失败；
     * 5：订单超时；
     * 6：出单中；
     * 7：订单成功；
     * 8：订单失败待退款；
     * 9：订单失败已退款；
     * 10：草稿
     */
    private long orderStatus;
    private String orderStatusStr;
    private String succeededTime;
    private String expireTime;
    private String createTime;
    private long createUserId;
    private long updateUserId;
    private long servantId;
    private String orderCode;
    private String payTerms;
    private String desc;
    private String remark;
    private String agentInfo;
    private String nickName;
    private String userMobile;
    /**
     * 状态(0：删除1：未支付2：已支付)
     */
    private String payStatus;
    private String payStatusStr;
    private long discountDetailCount;
    private PayLogModel payLog;
    private List goodsDescs;
    private List<PayLogModel> allPayLog;
    private AgentViewModel agentView;
    private List<OrderDeliveryInfo> sysOrderDeliveryList;


    public long getOrderId() {
        return orderId;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(long ownerId) {
        this.ownerId = ownerId;
    }

    public long getAddressId() {
        return addressId;
    }

    public void setAddressId(long addressId) {
        this.addressId = addressId;
    }

    public AccountAddressModel getAccountAddress() {
        return accountAddress;
    }

    public void setAccountAddress(AccountAddressModel accountAddress) {
        this.accountAddress = accountAddress;
    }

    public List<SysOrderDetailModel> getSysOrderDetailList() {
        return sysOrderDetailList;
    }

    public void setSysOrderDetailList(List<SysOrderDetailModel> sysOrderDetailList) {
        this.sysOrderDetailList = sysOrderDetailList;
    }

    public List<DiscountDetailModel> getDiscountDetailList() {
        return discountDetailList;
    }

    public void setDiscountDetailList(List<DiscountDetailModel> discountDetailList) {
        this.discountDetailList = discountDetailList;
    }

    public long getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(long orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getOrderStatusStr() {
        return orderStatusStr;
    }

    public void setOrderStatusStr(String orderStatusStr) {
        this.orderStatusStr = orderStatusStr;
    }

    public String getSucceededTime() {
        return succeededTime;
    }

    public void setSucceededTime(String succeededTime) {
        this.succeededTime = succeededTime;
    }

    public String getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(String expireTime) {
        this.expireTime = expireTime;
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

    public long getUpdateUserId() {
        return updateUserId;
    }

    public void setUpdateUserId(long updateUserId) {
        this.updateUserId = updateUserId;
    }

    public long getServantId() {
        return servantId;
    }

    public void setServantId(long servantId) {
        this.servantId = servantId;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public String getPayTerms() {
        return payTerms;
    }

    public void setPayTerms(String payTerms) {
        this.payTerms = payTerms;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getAgentInfo() {
        return agentInfo;
    }

    public void setAgentInfo(String agentInfo) {
        this.agentInfo = agentInfo;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getUserMobile() {
        return userMobile;
    }

    public void setUserMobile(String userMobile) {
        this.userMobile = userMobile;
    }

    public String getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(String payStatus) {
        this.payStatus = payStatus;
    }

    public String getPayStatusStr() {
        return payStatusStr;
    }

    public void setPayStatusStr(String payStatusStr) {
        this.payStatusStr = payStatusStr;
    }

    public long getDiscountDetailCount() {
        return discountDetailCount;
    }

    public void setDiscountDetailCount(long discountDetailCount) {
        this.discountDetailCount = discountDetailCount;
    }

    public PayLogModel getPayLog() {
        return payLog;
    }

    public void setPayLog(PayLogModel payLog) {
        this.payLog = payLog;
    }

    public List getGoodsDescs() {
        return goodsDescs;
    }

    public void setGoodsDescs(List goodsDescs) {
        this.goodsDescs = goodsDescs;
    }

    public List<PayLogModel> getAllPayLog() {
        return allPayLog;
    }

    public void setAllPayLog(List<PayLogModel> allPayLog) {
        this.allPayLog = allPayLog;
    }

    public AgentViewModel getAgentView() {
        return agentView;
    }

    public void setAgentView(AgentViewModel agentView) {
        this.agentView = agentView;
    }

    public List<OrderDeliveryInfo> getSysOrderDeliveryList() {
        return sysOrderDeliveryList;
    }

    public void setSysOrderDeliveryList(List<OrderDeliveryInfo> sysOrderDeliveryList) {
        this.sysOrderDeliveryList = sysOrderDeliveryList;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public double getSettlementAmount() {
        return settlementAmount;
    }

    public void setSettlementAmount(double settlementAmount) {
        this.settlementAmount = settlementAmount;
    }
}
