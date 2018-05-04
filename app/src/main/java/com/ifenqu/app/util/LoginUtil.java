package com.ifenqu.app.util;

import android.text.TextUtils;

import com.blankj.utilcode.util.CacheUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.ifenqu.app.R;
import com.ifenqu.app.app.IfenquApplication;
import com.ifenqu.app.model.TokenModel;
import com.ifenqu.app.model.UserModel;

public class LoginUtil {

    public static boolean checkLoginStatus() {
        TokenModel tokenModel = (TokenModel) CacheUtils.getInstance().getSerializable(CacheConstant.KEY_TOKEN);
        if (tokenModel != null && !TextUtils.isEmpty(tokenModel.getAccessToken())) {
            return true;
        }
        return false;
    }

    private boolean checkLoginStatusWithToast() {
        boolean isLogin = LoginUtil.checkLoginStatus();
        if (!isLogin) {
            ToastUtils.showShort(IfenquApplication.getInstance().getResources().getString(R.string.login_tip2));
        }

        return isLogin;
    }

    public static TokenModel getLoginToken(){
        return (TokenModel) CacheUtils.getInstance().getSerializable(CacheConstant.KEY_TOKEN);
    }

    public static UserModel getUserMode(){
        return (UserModel) CacheUtils.getInstance().getSerializable(CacheConstant.KEY_CURRENT_USER_INFO);
    }

}
