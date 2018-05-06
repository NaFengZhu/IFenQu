package com.ifenqu.app.model;

import java.io.Serializable;

public class CardsModel implements Serializable {
    private int id;
    private String cardId;
    private int balance;
    private String beginTimestamp;
    private String brandName;
    private String cardType;
    private String createTimestamp;
    private String dateType;
    private String description;
    private String endTimestamp;
    private int fixedTerm;
    private int getLimit;
    private String notice;
    private String platformId;
    private int quantity;
    private String servicePhone;
    private String subTitle;
    private String title;
    private boolean useCustomCode;
    private String codes;
    private String status;
    private String ticketDiscountVoList;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
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

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public String getCreateTimestamp() {
        return createTimestamp;
    }

    public void setCreateTimestamp(String createTimestamp) {
        this.createTimestamp = createTimestamp;
    }

    public String getDateType() {
        return dateType;
    }

    public void setDateType(String dateType) {
        this.dateType = dateType;
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

    public int getFixedTerm() {
        return fixedTerm;
    }

    public void setFixedTerm(int fixedTerm) {
        this.fixedTerm = fixedTerm;
    }

    public int getGetLimit() {
        return getLimit;
    }

    public void setGetLimit(int getLimit) {
        this.getLimit = getLimit;
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

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getServicePhone() {
        return servicePhone;
    }

    public void setServicePhone(String servicePhone) {
        this.servicePhone = servicePhone;
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

    public String getCodes() {
        return codes;
    }

    public void setCodes(String codes) {
        this.codes = codes;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTicketDiscountVoList() {
        return ticketDiscountVoList;
    }

    public void setTicketDiscountVoList(String ticketDiscountVoList) {
        this.ticketDiscountVoList = ticketDiscountVoList;
    }
}
