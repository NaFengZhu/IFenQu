package com.ifenqu.app.util;

import android.content.Context;
import android.text.TextUtils;

import com.geetest.sdk.Bind.GT3GeetestBindListener;
import com.geetest.sdk.Bind.GT3GeetestUtilsBind;
import com.google.gson.Gson;
import com.ifenqu.app.http.HttpConstant;
import com.ifenqu.app.http.HttpRequest;
import com.ifenqu.app.http.response.OnHttpResponseListener;
import com.ifenqu.app.model.API2Model;
import com.ifenqu.app.model.API2ServerModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by zhunafeng on 25/3/18.
 * 处理Gee验证逻辑
 */

public class GeeVerificationUtil extends GT3GeetestBindListener {
    private GT3GeetestUtilsBind mGT3GeetestUtilsBind;
    private Context context;
    private String phone;
    private OnHttpResponseListener listener;

    public GeeVerificationUtil(Context context) {
        this.context = context;
        if (context instanceof OnHttpResponseListener) {
            this.listener = (OnHttpResponseListener) context;
        }
        mGT3GeetestUtilsBind = new GT3GeetestUtilsBind(context);
        //设置是否可以点击屏幕边缘关闭验证码
        mGT3GeetestUtilsBind.setDialogTouch(false);
    }

    public void startVerify(String phone) {
        this.phone = phone;
        if (listener != null) {
            HttpRequest.geeApi1(HttpConstant.URL_GEE_CAPTURE_INDEX, listener);
        }
    }

    @Override
    public boolean gt3SetIsCustom() {
        return true;
    }

    @Override
    public void gt3CloseDialog(int i) {
        super.gt3CloseDialog(i);
    }

    @Override
    public void gt3DialogReady() {
        super.gt3DialogReady();
    }

    @Override
    public void gt3FirstResult(JSONObject jsonObject) {
        super.gt3FirstResult(jsonObject);
    }

    @Override
    public Map<String, String> gt3CaptchaApi1() {
        return super.gt3CaptchaApi1();
    }

    @Override
    public void gt3GeetestStatisticsJson(JSONObject jsonObject) {
        super.gt3GeetestStatisticsJson(jsonObject);
    }


    @Override
    public void gt3DialogSuccessResult(String result) {
        super.gt3DialogSuccessResult(result);
        if (!TextUtils.isEmpty(result)) {
            try {
                JSONObject jobj = new JSONObject(result);
                String sta = jobj.getString("status");
                if ("success".equals(sta)) {
                    mGT3GeetestUtilsBind.gt3TestFinish();

                } else {
                    mGT3GeetestUtilsBind.gt3TestClose();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            mGT3GeetestUtilsBind.gt3TestClose();
        }
    }

    @Override
    public Map<String, String> gt3SecondResult() {
        return super.gt3SecondResult();
    }

    @Override
    public void gt3DialogOnError(String s) {
        super.gt3DialogOnError(s);
    }


    /**
     * 设置后，界面横竖屏不会关闭验证码，推荐设置
     */
    public void configChange() {
        mGT3GeetestUtilsBind.changeDialogLayout();
    }


    public void realse() {
        /**
         * 页面关闭时释放资源
         */
        mGT3GeetestUtilsBind.cancelUtils();
    }

    /**
     * 自定义二次验证，也就是当gtSetIsCustom为ture时才执行
     * 拿到第二个url（API2）需要的数据
     * 在该回调里面自行请求api2
     * 对api2的结果进行处理
     */
    @Override
    public void gt3GetDialogResult(boolean b, String s) {
        super.gt3GetDialogResult(b, s);
        Map<String, String> head = new HashMap<>();
        head.put("ifenqu-validate", getJson(s));
        if (listener != null) {
            HttpRequest.sendVerificationCode(phone, HttpConstant.URL_VERTIFICATION_CODE_INDEX, head, listener);
        }
    }

    /**
     * 工具类 生成@param ifenqu-validate 的Value 值
     * {@link #gt3GetDialogResult}
     *
     * @param s
     * @return
     */
    private String getJson(String s) {
        if (TextUtils.isEmpty(s)) return "";

        Gson gson = new Gson();
        API2Model model = gson.fromJson(s,API2Model.class);
        if (model != null) {
            API2ServerModel api2ServerModel = new API2ServerModel();
            api2ServerModel.setChallenge(model.getGeetest_challenge());
            api2ServerModel.setSeccode(model.getGeetest_seccode());
            api2ServerModel.setValidate(model.getGeetest_validate());
            String result = gson.toJson(api2ServerModel);
            return result;
        }
        return "";
    }

    public void setUpGee(JSONObject object) {
        mGT3GeetestUtilsBind.gtSetApi1Json(object);
        mGT3GeetestUtilsBind.getGeetest(context, HttpConstant.CAPTURE_URL, HttpConstant.VALUATE_URL, null, GeeVerificationUtil.this);
    }

    public void closeGeeSuccess() {
        //如果调用成功，执行以下操作，然后请求验证码
        mGT3GeetestUtilsBind.gt3TestFinish();
    }

    public void closeGeeFail() {
        //如果调用失败，执行以下操作，然后请求验证码
        mGT3GeetestUtilsBind.gt3TestFinish();
    }
}
