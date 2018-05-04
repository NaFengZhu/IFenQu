package com.ifenqu.app.model;

import java.io.Serializable;

public class CardModel implements Serializable {
    private long balance;
    private String beginTimestamp;
    private String brandName;
    private String cardId;
    /**
     * 卡券类型: CASH 表示为现金
     * 1现金减免券，
     * 2下单有礼券，
     * 3下单有礼券（需额外自付），
     * 4.电子兑换券（自核销）
     * 5.电子兑换券（第三方核销），
     * 6实物兑换券
     */
    private String cardType;
    private String description;
    private String endTimestamp;
    private long fixedTerm;
    private long getLimit;
    private String lastUpdateTimestamp;
    private String notice;
    private String platformId;
    private long quantity;
    private String servicePhone;
    /**
     * 状态:
     * 0 UNKNOWN, // 未知 
     * 1 VALID, // 有效的
     *  2 EXPIRED, // 过期后无效.
     *  3 INVALID, // 已删除
     */
    private String status;
    private String subTitle;
    private String title;
    /**
     * 是否自定义Code码 ,填写true 或false， 默认为false 数据库用0表示 
     */
    private boolean useCustomCode;

    public long getBalance() {
        return balance;
    }

    public void setBalance(long balance) {
        this.balance = balance;
    }

    public String getBeginTimestamp() {
        return beginTimestamp;
    }

    public void setBeginTimestamp(String beginTimestamp) {
        this.beginTimestamp = beginTimestamp;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getEndTimestamp() {
        return endTimestamp;
    }

    public void setEndTimestamp(String endTimestamp) {
        this.endTimestamp = endTimestamp;
    }

    public long getFixedTerm() {
        return fixedTerm;
    }

    public void setFixedTerm(long fixedTerm) {
        this.fixedTerm = fixedTerm;
    }

    public long getGetLimit() {
        return getLimit;
    }

    public void setGetLimit(long getLimit) {
        this.getLimit = getLimit;
    }

    public String getLastUpdateTimestamp() {
        return lastUpdateTimestamp;
    }

    public void setLastUpdateTimestamp(String lastUpdateTimestamp) {
        this.lastUpdateTimestamp = lastUpdateTimestamp;
    }

    public String getNotice() {
        return notice;
    }

    public void setNotice(String notice) {
        this.notice = notice;
    }

    public String getPlatformId() {
        return platformId;
    }

    public void setPlatformId(String platformId) {
        this.platformId = platformId;
    }

    public long getQuantity() {
        return quantity;
    }

    public void setQuantity(long quantity) {
        this.quantity = quantity;
    }

    public String getServicePhone() {
        return servicePhone;
    }

    public void setServicePhone(String servicePhone) {
        this.servicePhone = servicePhone;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isUseCustomCode() {
        return useCustomCode;
    }

    public void setUseCustomCode(boolean useCustomCode) {
        this.useCustomCode = useCustomCode;
    }
}
