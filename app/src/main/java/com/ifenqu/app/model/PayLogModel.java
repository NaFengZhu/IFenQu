package com.ifenqu.app.model;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 支付
 */
public class PayLogModel implements Serializable {
    private long payLogId;
    private long orderId;
    private long accountBankId;
    private String sn;
    private long payChannel;
    private String payChannelStr;
    /**
     * 支付渠道状态  1.正常 2.异常
     */
    private long channelStatus;
    private String channelSN;
    private String payTime;
    private String expTime;
    private double amount;
    private double commission;
    private double interest;
    /**
     * 0：删除1：未支付2：已支付
     */
    private long status;
    private String StatusStr;
    private String createTime;
    private String updateTime;
    private String term;

    public long getPayLogId() {
        return payLogId;
    }

    public void setPayLogId(long payLogId) {
        this.payLogId = payLogId;
    }

    public long getOrderId() {
        return orderId;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

    public long getAccountBankId() {
        return accountBankId;
    }

    public void setAccountBankId(long accountBankId) {
        this.accountBankId = accountBankId;
    }

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public long getPayChannel() {
        return payChannel;
    }

    public void setPayChannel(long payChannel) {
        this.payChannel = payChannel;
    }

    public String getPayChannelStr() {
        return payChannelStr;
    }

    public void setPayChannelStr(String payChannelStr) {
        this.payChannelStr = payChannelStr;
    }

    public long getChannelStatus() {
        return channelStatus;
    }

    public void setChannelStatus(long channelStatus) {
        this.channelStatus = channelStatus;
    }

    public String getChannelSN() {
        return channelSN;
    }

    public void setChannelSN(String channelSN) {
        this.channelSN = channelSN;
    }

    public String getPayTime() {
        return payTime;
    }

    public void setPayTime(String payTime) {
        this.payTime = payTime;
    }

    public String getExpTime() {
        return expTime;
    }

    public void setExpTime(String expTime) {
        this.expTime = expTime;
    }

    public long getStatus() {
        return status;
    }

    public void setStatus(long status) {
        this.status = status;
    }

    public String getStatusStr() {
        return StatusStr;
    }

    public void setStatusStr(String statusStr) {
        StatusStr = statusStr;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public double getCommission() {
        return commission;
    }

    public void setCommission(double commission) {
        this.commission = commission;
    }

    public double getInterest() {
        return interest;
    }

    public void setInterest(double interest) {
        this.interest = interest;
    }
}
