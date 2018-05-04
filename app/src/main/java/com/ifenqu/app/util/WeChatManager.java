package com.ifenqu.app.util;

import android.content.Context;

import com.ifenqu.app.app.Constants;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

public class WeChatManager {

    private IWXAPI iwxapi;
    private WeChatManager() {
    }

    private static class WeChatManagerHelper{
        private static WeChatManager INSTANCE = new WeChatManager();
    }

    public static WeChatManager getInstance(){
        return WeChatManagerHelper.INSTANCE;
    }


    /**
     * 注册
     * @param context
     */
    public void regToWx(Context context){
        iwxapi = WXAPIFactory.createWXAPI(context,Constants.WECHAT_APP_ID);
        iwxapi.registerApp(Constants.WECHAT_APP_ID);
    }

    public void sendReq(){
        final SendAuth.Req req = new SendAuth.Req();
        req.scope = "snsapi_userinfo";
        req.state = "none";
        iwxapi.sendReq(req);
    }

}
