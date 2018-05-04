package com.ifenqu.app.wxapi;

import android.os.Bundle;
import android.text.TextUtils;

import com.blankj.utilcode.util.CacheUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.ifenqu.app.app.Constants;
import com.ifenqu.app.util.CacheConstant;
import com.ifenqu.app.view.BaseActivity;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

public class WXEntryActivity extends BaseActivity implements IWXAPIEventHandler{

    private IWXAPI iwxapi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        iwxapi = WXAPIFactory.createWXAPI(this, Constants.WECHAT_APP_ID, true);
        iwxapi.registerApp(Constants.WECHAT_APP_ID);
        iwxapi.handleIntent(getIntent(), this);

    }

    @Override
    public void onReq(BaseReq baseReq) {
    }


    @Override
    public void onResp(BaseResp resp) {
        if (resp.errCode == BaseResp.ErrCode.ERR_OK) {
            String code = ((SendAuth.Resp) resp).code;
            if (TextUtils.isEmpty(code)) {
                ToastUtils.showShort("微信登录失败");
                finish();
                return;
            }

            CacheUtils.getInstance().put(CacheConstant.KEY_WECHAT_CODE,code);
            finish();

        }

    }


}
