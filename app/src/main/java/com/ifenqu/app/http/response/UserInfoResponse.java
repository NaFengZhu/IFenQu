package com.ifenqu.app.http.response;

import com.ifenqu.app.http.Response;
import com.ifenqu.app.model.UserModel;

/**
 * Created by zhunafeng on 16/3/18.
 * 获取用户信息时，服务器相应的数据
 */

public class UserInfoResponse extends Response {
    private UserModel data;

    public UserModel getData() {
        return data;
    }

    public void setData(UserModel data) {

        this.data = data;
    }

    @Override
    public boolean checkDataValidate() {
        if (!super.checkDataValidate())return false;
        if (data == null)return false;
        return true;
    }
}
