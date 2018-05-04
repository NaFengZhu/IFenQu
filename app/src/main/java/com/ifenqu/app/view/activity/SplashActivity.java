package com.ifenqu.app.view.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.ifenqu.app.R;
import com.ifenqu.app.http.HttpConstant;
import com.ifenqu.app.http.HttpRequest;
import com.ifenqu.app.http.response.OnHttpResponseListener;
import com.ifenqu.app.http.response.SplashResponse;
import com.ifenqu.app.model.SplashModel;
import com.ifenqu.app.util.NetworkUtil;
import com.ifenqu.app.view.BaseActivity;
import java.util.List;

/**
 * 启动页
 * 请求配置界面的内容，当无内容或者发生错误时，执行默认展示，然后进入主界面
 */
public class SplashActivity extends BaseActivity implements OnHttpResponseListener{

    private static final int TIMER = 2000;

    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            goMain();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splansh);
        httpGet();
    }

    /**
     * 请求配置内容
     */
    private void httpGet() {
        if (!NetworkUtil.checkoutInternet()){
            sendMessageToHandler();
            return;
        }

        HttpRequest.getSplashInfo(HttpConstant.URL_SPLASH_INDEX,this);

    }

    //进入主界面
    private void goMain(){
        MainActivity.start(this);
        finish();
    }

    @Override
    public void onHttpResponse(int requestCode, String resultJson, Exception e) {
        if (requestCode == HttpConstant.URL_SPLASH_INDEX){
            if (TextUtils.isEmpty(resultJson)){
                sendMessageToHandler();
                return;
            }

            Gson gson = new Gson();
            SplashResponse response = gson.fromJson(resultJson,SplashResponse.class);

            if (!response.checkDataValidate()){
                sendMessageToHandler();
                return;
            }
            List<SplashModel> modelList = response.getData();
            SplashModel model = modelList.get(0);
            WebViewActivity.startHtml(this,model.getContent(),true,false);
            finish();
        }
    }

    private void sendMessageToHandler(){
        mHandler.sendEmptyMessageDelayed(0,TIMER);
    }
}
