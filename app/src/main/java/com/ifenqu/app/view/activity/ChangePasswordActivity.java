package com.ifenqu.app.view.activity;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

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

public class ChangePasswordActivity extends BaseActivity implements OnHttpResponseListener, TimerManager.TimerListener {

    @BindView(R.id.et_original_phone)
    EditText et_original_phone;

    @BindView(R.id.et_new_phone)
    EditText et_new_phone;

    @BindView(R.id.et_verification_phone)
    EditText et_verification_phone;

    @BindView(R.id.tv_verification_code)
    TextView tv_verification_code;

    @BindView(R.id.tv_confirm)
    TextView tv_confirm;

    @BindView(R.id.comm_title)
    CommonTitleView comm_title;

    private static final long TIME = 60000;
    private GeeVerificationUtil mGeeVerificationUtil;
    private TimerManager timerManager;

    public static void start(Context context) {
        if (context != null) {
            Intent intent = new Intent(context, ChangePasswordActivity.class);
            context.startActivity(intent);
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        ButterKnife.bind(this);

        init();
        updateConfirmBtnStatus(0.25f);
    }

    private void updateConfirmBtnStatus(float status) {
        tv_confirm.setAlpha(status);
        if (status < 1) {
            tv_confirm.setEnabled(false);
        } else {
            tv_confirm.setEnabled(true);
        }
    }

    @OnClick(R.id.tv_confirm)
    public void onClickConfirm(View view) {
        /**
         * 检查网络
         * 检查手机号的有效性
         * 检查新手机号的有效性
         * 检查验证码是否有效
         *
         */
        String phone = et_original_phone.getText().toString();
        String newPhone = et_new_phone.getText().toString();
        if (!RegexUtils.isMobileExact(phone)) {
            ToastUtils.showShort(IfenquApplication.getInstance().getResources().getString(R.string.toast_check_old_phone_number));
            return;
        }

        if (!RegexUtils.isMobileExact(newPhone)) {
            ToastUtils.showShort(IfenquApplication.getInstance().getResources().getString(R.string.toast_check_new_phone_number));
            return;
        }

        if (phone.equalsIgnoreCase(newPhone)) {
            ToastUtils.showShort(IfenquApplication.getInstance().getResources().getString(R.string.toast_check_same_phone_number));
            return;
        }

        String verificationCode = et_verification_phone.getText().toString();
        if (TextUtils.isEmpty(verificationCode)) {
            ToastUtils.showShort(getResources().getString(R.string.toast_check_verification_code));
            return;
        }

        if (!NetworkUtil.checkoutInternet()) return;
        HttpRequest.updatePhone(phone, newPhone, verificationCode, HttpConstant.URL_UPDATE_PHONE_INDEX, this);
    }

    @OnClick(R.id.tv_verification_code)
    public void onClickVerificationCode(View view) {
        String phone = et_new_phone.getText().toString();
        if (!RegexUtils.isMobileExact(phone)) {
            ToastUtils.showShort(IfenquApplication.getInstance().getResources().getString(R.string.toast_check_new_phone_number));
            return;
        }

        String originalPhone = et_original_phone.getText().toString();
        if (!TextUtils.isEmpty(originalPhone) && originalPhone.equalsIgnoreCase(phone)) {
            ToastUtils.showShort(IfenquApplication.getInstance().getResources().getString(R.string.toast_check_same_phone_number));
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
    public void onHttpResponse(int requestCode, String resultJson, Exception e) {
        /**
         * 检查requestCode
         * 检查往回结果，如果失败，显示失败逻辑，如果成功，？结束调此应用
         *
         */
        if (requestCode == HttpConstant.URL_GEE_CAPTURE_INDEX) {
            if (TextUtils.isEmpty(resultJson)) {
                mGeeVerificationUtil.closeGeeFail();
                return;
            }
            handleGeeResult(resultJson);
        } else if (requestCode == HttpConstant.URL_VERTIFICATION_CODE_INDEX) {
            if (TextUtils.isEmpty(resultJson)) {
                mGeeVerificationUtil.closeGeeFail();
                return;
            }
            handleVerificationCodeResult(resultJson);
        } else if (requestCode == HttpConstant.URL_UPDATE_PHONE_INDEX) {
            //更新完手机号码以后
            if (TextUtils.isEmpty(resultJson)) {
                ToastUtils.showShort("更新手机号失败");
                return;
            }
            Gson gson = new Gson();
            Response response = gson.fromJson(resultJson, Response.class);
            if (response == null) {
                ToastUtils.showShort("更新手机号失败");
                return;
            }
            if (!response.checkDataValidate()) {
                ToastUtils.showShort(response.getMessage());
                return;
            }
            ToastUtils.showShort("更新手机号码成功");
            finish();
        }
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
            //更新按钮状态 显示倒计时 不能点击
            updateVerificationTextViewStatus(false);
        } else {
            mGeeVerificationUtil.closeGeeFail();
            //验证失败
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

    private void init() {
        timerManager = new TimerManager();
        comm_title.setTitle(R.string.change_password_title);
        mGeeVerificationUtil = new GeeVerificationUtil(this);

        et_original_phone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                checkConfirmBtnStatus();
            }
        });

        et_verification_phone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                checkConfirmBtnStatus();
            }

        });

        et_new_phone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                checkConfirmBtnStatus();
            }
        });
    }

    private void checkConfirmBtnStatus() {
        String originalPhone = et_original_phone.getText().toString();
        String newPhone = et_new_phone.getText().toString();
        String code = et_verification_phone.getText().toString();

        if (!TextUtils.isEmpty(originalPhone) && !TextUtils.isEmpty(newPhone) && !TextUtils.isEmpty(code)) {
            updateConfirmBtnStatus(1f);
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
}
