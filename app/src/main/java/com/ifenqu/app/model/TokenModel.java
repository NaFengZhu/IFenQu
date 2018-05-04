package com.ifenqu.app.model;

import java.io.Serializable;

public class TokenModel implements Serializable {
    private String accessToken;
    private String accessTokenExpire;
    private String refreshToken;
    private String refreshTokenExpire;
    private long userId;
    private boolean isLogin;

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public boolean isLogin() {
        return isLogin;
    }

    public void setLogin(boolean login) {
        isLogin = login;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getAccessTokenExpire() {
        return accessTokenExpire;
    }

    public void setAccessTokenExpire(String accessTokenExpire) {
        this.accessTokenExpire = accessTokenExpire;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public String getRefreshTokenExpire() {
        return refreshTokenExpire;
    }

    public void setRefreshTokenExpire(String refreshTokenExpire) {
        this.refreshTokenExpire = refreshTokenExpire;
    }

}
