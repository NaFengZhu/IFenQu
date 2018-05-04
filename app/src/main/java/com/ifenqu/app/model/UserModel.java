package com.ifenqu.app.model;

import java.io.Serializable;

/**
 * Created by zhunafeng on 16/3/18.
 * 用户model,包含用户的基本信息
 */

public class UserModel implements Serializable {

    private long userId;
    private String userName;
    private long userType;
    private String nickName;
    private String mobile;
    private String birthday;
    private String headImage;
    private String signature;
    private boolean subscribe;

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public long getUserType() {
        return userType;
    }

    public void setUserType(long userType) {
        this.userType = userType;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getHeadImage() {
        return headImage;
    }

    public void setHeadImage(String headImage) {
        this.headImage = headImage;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public boolean isSubscribe() {
        return subscribe;
    }

    public void setSubscribe(boolean subscribe) {
        this.subscribe = subscribe;
    }
}
