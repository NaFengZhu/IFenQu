package com.ifenqu.app.http.response;

import com.ifenqu.app.http.Response;
import com.ifenqu.app.model.TokenModel;

/**
 * Created by zhunafeng on 17/3/18.
 * 手机号 + 验证码登录以后 服务器相应对象
 */

public class TokenResponse extends Response {
    private TokenModel data;

    public TokenModel getData() {
        return data;
    }

    public void setData(TokenModel data) {
        this.data = data;
    }

    @Override
    public boolean checkDataValidate() {
        if (!super.checkDataValidate())return false;
        if (data == null)return false;
        return true;
    }
}
