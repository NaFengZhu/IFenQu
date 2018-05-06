package com.ifenqu.app.model;

import java.io.Serializable;
import java.util.ArrayList;

public class CouponModel implements Serializable {
    private String _id;
    private int userId;
    private String userName;
    private String cardId;
    private int sourceUserId;
    private String code;
    private int platformId;
    private String status;
    private String createTimestamp;
    private String lastUpdateTimestamp;
    private String expireTimestamp;
    private String subTitle;
    private String title;
    private ArrayList<TicketTagsModel> ticketTags;
    private CardsModel cards;

    public CardsModel getCards() {
        return cards;
    }

    public void setCards(CardsModel cards) {
        this.cards = cards;
    }

    public ArrayList<TicketTagsModel> getTicketTags() {
        return ticketTags;
    }

    public void setTicketTags(ArrayList<TicketTagsModel> ticketTags) {
        this.ticketTags = ticketTags;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public int getSourceUserId() {
        return sourceUserId;
    }

    public void setSourceUserId(int sourceUserId) {
        this.sourceUserId = sourceUserId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getPlatformId() {
        return platformId;
    }

    public void setPlatformId(int platformId) {
        this.platformId = platformId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreateTimestamp() {
        return createTimestamp;
    }

    public void setCreateTimestamp(String createTimestamp) {
        this.createTimestamp = createTimestamp;
    }

    public String getLastUpdateTimestamp() {
        return lastUpdateTimestamp;
    }

    public void setLastUpdateTimestamp(String lastUpdateTimestamp) {
        this.lastUpdateTimestamp = lastUpdateTimestamp;
    }

    public String getExpireTimestamp() {
        return expireTimestamp;
    }

    public void setExpireTimestamp(String expireTimestamp) {
        this.expireTimestamp = expireTimestamp;
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

}
