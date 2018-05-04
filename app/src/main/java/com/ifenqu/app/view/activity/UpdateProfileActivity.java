package com.ifenqu.app.view.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.blankj.utilcode.util.CacheUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.RegexUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.google.gson.Gson;
import com.ifenqu.app.R;
import com.ifenqu.app.app.IfenquApplication;
import com.ifenqu.app.http.HttpConstant;
import com.ifenqu.app.http.HttpRequest;
import com.ifenqu.app.http.Response;
import com.ifenqu.app.http.response.OnHttpResponseListener;
import com.ifenqu.app.model.TokenModel;
import com.ifenqu.app.util.CacheConstant;
import com.ifenqu.app.util.GeeVerificationUtil;
import com.ifenqu.app.util.NetworkUtil;
import com.ifenqu.app.util.TimerManager;
import com.ifenqu.app.view.BaseActivity;
import com.ifenqu.app.widget.CommonTitleView;
import org.json.JSONException;
import org.json.JSONObject;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 微信登录以后
 * 更新信息
 */
public class UpdateProfileActivity extends BaseActivity implements TimerManager.TimerListener, OnHttpResponseListener {

    @BindView(R.id.comm_title)
    CommonTitleView comm_title;

    @BindView(R.id.et_new_phone)
    EditText et_new_phone;

    @BindView(R.id.et_verification_phone)
    EditText et_verification_phone;

    @BindView(R.id.tv_confirm)
    TextView tv_confirm;

    @BindView(R.id.tv_verification_code)
    TextView tv_verification_code;

    private static final String KEY_TOKENMODEL = "KEY_TOKENMODEL";
    private static final long TIME = 60000;

    private GeeVerificationUtil mGeeVerificationUtil;
    private TimerManager timerManager;


    public static void start(Activity context, int requestCode, TokenModel model) {
        if (context == null) return;
        Intent intent = new Intent(context, UpdateProfileActivity.class);
        intent.putExtra(KEY_TOKENMODEL, model);
        context.startActivityForResult(intent, requestCode);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        timerManager = new TimerManager();
        mGeeVerificationUtil = new GeeVerificationUtil(this);
    }

    @OnClick(R.id.tv_verification_code)
    public void OnClickVerificationCode(View view) {
        String phone = et_new_phone.getText().toString();
        if (!RegexUtils.isMobileExact(phone)) {
            ToastUtils.showShort(IfenquApplication.getInstance().getResources().getString(R.string.toast_check_phone_number));
            return;
        }

        if (mGeeVerificationUtil != null) {
            mGeeVerificationUtil.startVerify(phone);
        }

    }

    @OnClick(R.id.tv_confirm)
    public void onClickCompleted(View view) {
        String phone = et_new_phone.getText().toString();
        if (!RegexUtils.isMobileExact(phone)) {
            ToastUtils.showShort(IfenquApplication.getInstance().getResources().getString(R.string.toast_check_phone_number));
            return;
        }

        String code = et_verification_phone.getText().toString();
        if (TextUtils.isEmpty(code)) {
            ToastUtils.showShort(IfenquApplication.getInstance().getResources().getString(R.string.toast_check_verification_code));
            return;
        }

        CacheUtils.getInstance().put(CacheConstant.KEY_TOKEN, getIntent().getSerializableExtra(KEY_TOKENMODEL));
        if (!NetworkUtil.checkoutInternet()) return;
        HttpRequest.bingPhoneFromWechat(phone, code, HttpConstant.URL_BING_PHONE_INDEX, this);
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
            tv_verification_code.setText(getResources().getString(R.string.login_verification));
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

        timerManager = null;

    }

    @Override
    public void timer(boolean isFinished, long time) {
        if (isFinished) {
            updateVerificationTextViewStatus(isFinished);
        } else {
            updateTimer(time, isFinished);
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
        JSONObject object = null;
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

    @Override
    public void onHttpResponse(int requestCode, String resultJson, Exception e) {
        if (requestCode == HttpConstant.URL_GEE_CAPTURE_INDEX) {
            handleGeeResult(resultJson);
        } else if (requestCode == HttpConstant.URL_VERTIFICATION_CODE_INDEX) {
            handleVerificationCodeResult(resultJson);
        } else if (requestCode == HttpConstant.URL_BING_PHONE_INDEX) {
            if (TextUtils.isEmpty(resultJson)) {
                ToastUtils.showShort("绑定失败");
                return;
            }
            Gson gson = new Gson();
            Response response = gson.fromJson(resultJson, Response.class);
            if (!response.checkDataValidate()) {
                showErrorDialog(response.getMessage());
                return;
            }
            goMain();
        }
    }

    @Override
    public void finish() {
        super.finish();
        CacheUtils.getInstance().remove(CacheConstant.KEY_TOKEN);
    }

    private void showErrorDialog(String message) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = View.inflate(this, R.layout.expired_dialog, null);
        TextView tvAlertDialogTitle = view.findViewById(R.id.tvAlertDialogTitle);
        builder.setView(view);
        tvAlertDialogTitle.setText(message);
        final AlertDialog alertDialog = builder.create();
        alertDialog.show();
        view.findViewById(R.id.btnAlertDialogPositive).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
    }

    private void goMain() {
        MainActivity.start(this);
        setResult(RESULT_OK);
        finish();

        //登录成功 保存登录token
        CacheUtils.getInstance().put(CacheConstant.KEY_TOKEN, getIntent().getSerializableExtra(KEY_TOKENMODEL));
    }
}
