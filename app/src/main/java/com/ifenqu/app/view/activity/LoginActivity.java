package com.ifenqu.app.view.activity;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.blankj.utilcode.util.CacheUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.RegexUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.google.gson.Gson;
import com.ifenqu.app.R;
import com.ifenqu.app.app.Constants;
import com.ifenqu.app.app.IfenquApplication;
import com.ifenqu.app.http.HttpConstant;
import com.ifenqu.app.http.HttpRequest;
import com.ifenqu.app.http.Response;
import com.ifenqu.app.http.response.OnHttpResponseListener;
import com.ifenqu.app.http.response.TokenResponse;
import com.ifenqu.app.http.response.UserInfoResponse;
import com.ifenqu.app.model.TokenModel;
import com.ifenqu.app.model.UserModel;
import com.ifenqu.app.util.CacheConstant;
import com.ifenqu.app.util.GeeVerificationUtil;
import com.ifenqu.app.util.NetworkUtil;
import com.ifenqu.app.util.TimerManager;
import com.ifenqu.app.util.WeChatManager;
import com.ifenqu.app.view.BaseActivity;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 登录页面
 */
public class LoginActivity extends BaseActivity implements OnHttpResponseListener,
        TimerManager.TimerListener {

    @BindView(R.id.et_login_phone)
    EditText et_login_phone;

    @BindView(R.id.et_input_verification)
    EditText et_input_verification;

    @BindView(R.id.tv_verification_code)
    TextView tv_verification_code;

    @BindView(R.id.ll_phone_parent)
    ViewGroup ll_phone_parent;

    @BindView(R.id.ll_code_parent)
    ViewGroup ll_code_parent;

    private static final long TIME = 60000;
    private static final int REQUEST_CODE_UPDATE_PROFILE = 200;

    private GeeVerificationUtil mGeeVerificationUtil;
    private IWXAPI iwxapi;
    private TokenModel token;
    private TimerManager timerManager;

    public static void start(Context context) {
        if (context == null) return;
        Intent intent = new Intent(context, LoginActivity.class);
        context.startActivity(intent);
    }

    public static void start(Context context,String code) {
        if (context == null) return;
        Intent intent = new Intent(context, LoginActivity.class);
        intent.putExtra("weChatCode",code);
        context.startActivity(intent);
    }

    private void sendReqToWechat() {
        final SendAuth.Req req = new SendAuth.Req();
        req.scope = "snsapi_userinfo";
        req.state = "none";
        iwxapi.sendReq(req);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        init();

//        WeChatManager.getInstance().regToWx(this);
        iwxapi = WXAPIFactory.createWXAPI(this, Constants.WECHAT_APP_ID, true);
        iwxapi.registerApp(Constants.WECHAT_APP_ID);

    }

    @Override
    protected void onResume() {
        super.onResume();
        getWeChatCode();
    }

    private void getWeChatCode(){
        String weChatCode = CacheUtils.getInstance().getString(CacheConstant.KEY_WECHAT_CODE);
        if (TextUtils.isEmpty(weChatCode)) return;
        if (!NetworkUtil.checkoutInternet()) return;
        HttpRequest.loginWeChat(Constants.WECHAT_APP_ID, weChatCode, HttpConstant.URL_LOGIN_WECHAT_INDEX, this);
        CacheUtils.getInstance().remove(CacheConstant.KEY_WECHAT_CODE);
    }

    private void init() {
        timerManager = new TimerManager();
        mGeeVerificationUtil = new GeeVerificationUtil(this);
        WeChatManager.getInstance().regToWx(this);

        et_input_verification.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                ll_code_parent.setBackground(hasFocus ? getResources().getDrawable(R.drawable.login_bg3) : getResources().getDrawable(R.drawable.login_bg2));
            }
        });

        et_login_phone.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                ll_phone_parent.setBackground(hasFocus ? getResources().getDrawable(R.drawable.login_bg3) : getResources().getDrawable(R.drawable.login_bg2));
            }
        });


    }

    @OnClick(R.id.tv_login_sign_in)
    public void login(View view) {
        String phone = et_login_phone.getText().toString();
        if (!RegexUtils.isMobileExact(phone)) {
            ToastUtils.showShort(IfenquApplication.getInstance().getResources().getString(R.string.toast_check_phone_number));
            return;
        }
        String verificationCode = et_input_verification.getText().toString();
        if (TextUtils.isEmpty(verificationCode)) {
            ToastUtils.showShort(getResources().getString(R.string.toast_check_verification_code));
            return;
        }
        if (!NetworkUtil.checkoutInternet()) return;
        HttpRequest.loginWithPhone(phone, verificationCode, HttpConstant.URL_SIGNIN_PHONE_INDEX, this);

    }

    @OnClick(R.id.tv_login_forget_password)
    public void forgetPassword(View view) {

    }

    @OnClick(R.id.lv_login_wechat)
    public void loginWechat(View view) {
//        WeChatManager.getInstance().sendReq();
        sendReqToWechat();
    }

    @OnClick(R.id.tv_rule)
    public void rule(View view) {
        WebViewActivity.start(this, Constants.IFENQU_RETURN_PRIVACY);
    }

    @OnClick(R.id.tv_verification_code)
    public void getVerificationCode(View view) {
        String phone = et_login_phone.getText().toString();
        if (!RegexUtils.isMobileExact(phone)) {
            ToastUtils.showShort(IfenquApplication.getInstance().getResources().getString(R.string.toast_check_phone_number));
            return;
        }

        if (mGeeVerificationUtil != null) {
            mGeeVerificationUtil.startVerify(phone);
        }
    }

    private void updateVerificationTextViewStatus(boolean isFinished) {
        Resources resource = getResources();
        if (!isFinished) {
            timerManager.startTimer(TIME, this);
            updateTimer(TIME, isFinished);
            tv_verification_code.setTextColor(resource.getColor(R.color.login_logo_hint_text));
        } else {
            timerManager.cancelTimer();
            updateTimer(0, isFinished);
            tv_verification_code.setTextColor(resource.getColor(R.color.verification_code_color));
        }

        tv_verification_code.setEnabled(isFinished);
    }

    private void updateTimer(long time, boolean isFinished) {
        if (!isFinished) {
            tv_verification_code.setText(String.format(getResources().getString(R.string.login_verification_str), time));
        } else {
            tv_verification_code.setText(getResources().getString(R.string.login_request_verification));
        }
    }


    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (mGeeVerificationUtil != null) {
            mGeeVerificationUtil.configChange();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mGeeVerificationUtil != null) {
            mGeeVerificationUtil.realse();
        }

    }

    @Override
    public void onHttpResponse(int requestCode, String resultJson, Exception e) {
        if (requestCode == HttpConstant.URL_SIGNIN_PHONE_INDEX) {
            handleLoginResult(resultJson);
        } else if (requestCode == HttpConstant.URL_GEE_CAPTURE_INDEX) {
            handleGeeResult(resultJson);
        } else if (requestCode == HttpConstant.URL_VERTIFICATION_CODE_INDEX) {
            handleVerificationCodeResult(resultJson);
        } else if (requestCode == HttpConstant.URL_LOGIN_WECHAT_INDEX) {
            if (TextUtils.isEmpty(resultJson)) {
                finish();
                return;
            }
            handleLoginResult2(resultJson);
        } else if (requestCode == HttpConstant.URL_USER_INFO_INDEX) {
            //获取用户信息
            getUserInfo(resultJson);
        }
    }

    /**
     * 处理Gee api1 自定义验证结果
     *
     * @param resultJson
     */
    private void handleGeeResult(String resultJson) {
        if (TextUtils.isEmpty(resultJson)) return;
        LogUtils.d(resultJson);
        JSONObject object = new JSONObject();
        try {
            object = new JSONObject(resultJson);
        } catch (JSONException e1) {
            e1.printStackTrace();
        }

        if (object == null) return;
        mGeeVerificationUtil.setUpGee(object);
    }

    /**
     * 处理请求验证码返回结果
     *
     * @param resultJson
     */
    private void handleVerificationCodeResult(String resultJson) {
        //验证手机号码
        Gson gson = new Gson();
        Response response = gson.fromJson(resultJson, Response.class);

        if (response.checkDataValidate()) {
            //验证成功
            mGeeVerificationUtil.closeGeeSuccess();
            updateVerificationTextViewStatus(false);
        } else {
            mGeeVerificationUtil.closeGeeFail();
            //验证失败
        }
    }

    /**
     * 处理手机号+验证码登录结果
     *
     * @param resultJson
     */
    private void handleLoginResult(String resultJson) {
        Gson gson = new Gson();
        TokenResponse response = gson.fromJson(resultJson, TokenResponse.class);
        if (response == null) {
            ToastUtils.showShort("手机校验失败");
            return;
        }
        if (response.checkDataValidate()) {
            CacheUtils.getInstance().put(CacheConstant.KEY_TOKEN, response.getData());
            finishLoginAction();
        } else {
            //失败
            ToastUtils.showShort(response.getMessage());
        }

    }


    @Override
    public void timer(boolean isFinished, long time) {
        if (isFinished) {
            updateVerificationTextViewStatus(isFinished);
        } else {
            updateTimer(time, isFinished);
        }
    }

    @OnClick(R.id.iv_close)
    public void OnClickClose(View view) {
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_UPDATE_PROFILE) {
            if (resultCode == RESULT_OK) finish();
        }
    }


    private void goToUpdateProfile() {
        UpdateProfileActivity.start(this, 0, token);
        finish();
    }

    private void finishLoginAction() {
        finish();
    }

    private void getUserInfo(String resultJson) {
        if (TextUtils.isEmpty(resultJson)) {
            goToUpdateProfile();
            return;
        }

        Gson gson = new Gson();
        UserInfoResponse response = gson.fromJson(resultJson, UserInfoResponse.class);
        if (response.checkDataValidate()) {
            UserModel model = response.getData();
            if (model == null) {
                goToUpdateProfile();
                return;
            }

            if (TextUtils.isEmpty(model.getMobile())) {
                goToUpdateProfile();
            } else {
                finishLoginAction();
            }

        } else {
            goToUpdateProfile();
        }
    }

    private void handleLoginResult2(String resultJson) {
        Gson gson = new Gson();
        TokenResponse response = gson.fromJson(resultJson, TokenResponse.class);
        if (response == null) {
            finish();
            return;
        }
        if (response.checkDataValidate()) {
            token = response.getData();
            httpGetUserInfo();
        } else {
            finish();
            //失败
            ToastUtils.showShort(R.string.login_error);

        }
    }

    private void httpGetUserInfo() {
        if (!NetworkUtil.checkoutInternet()) return;
        HttpRequest.getUser(token.getAccessToken(), HttpConstant.URL_USER_INFO_INDEX, this);
    }
}
