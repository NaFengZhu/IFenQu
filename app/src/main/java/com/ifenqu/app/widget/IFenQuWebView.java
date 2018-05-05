package com.ifenqu.app.widget;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.blankj.utilcode.util.LogUtils;
import com.ifenqu.app.R;
import com.ifenqu.app.util.LoginUtil;

import java.io.InputStream;

/**
 * 专供爱分趣使用，其他产品请绕道
 *
 */
public class IFenQuWebView extends LinearLayout {

    private ProgressBar progressBar;
    private WebView ifenqu_webView;

    private IFenQuWebViewListener listener;
    private boolean isOpenInject = true; //默认是打开的
    private boolean isShowProgress;
    private boolean isSetCookie;
    private WebChromeClient mWebChromClient = new WebChromeClient(){
        @Override
        public void onReceivedTitle(WebView view, String title) {
            super.onReceivedTitle(view, title);
            if (listener != null){
                listener.onReceivedTitle(title);
            }
        }

        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            super.onProgressChanged(view, newProgress);
            if (isShowProgress){
                progressBar.setProgress(newProgress);
            }
        }
    };

    private WebViewClient mWebViewClient = new WebViewClient(){
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            LogUtils.d("当前的 url - "+url);
            ifenqu_webView.loadUrl(url);
            return true;
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            if (isShowProgress){
                progressBar.setVisibility(View.VISIBLE);
            }
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            if (isOpenInject){
                injectCSS();
            }
//            reSizeWebView();
            super.onPageFinished(view, url);

            if (isShowProgress){
                progressBar.setVisibility(View.GONE);
            }

            if (listener != null){
                listener.onPageLoadFinished(view,url);
            }
        }
    };

    private void reSizeWebView(){
        ifenqu_webView.loadUrl("javascript:Ifenqu.reSize(document.body.getBoundingClientRect().height)");
    }

    public IFenQuWebView(Context context) {
        this(context,null);
    }

    public IFenQuWebView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public IFenQuWebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        buildWebViewGroup(attrs);
    }

    private void buildWebViewGroup(AttributeSet attrs) {
        TypedArray ta = getContext().obtainStyledAttributes(attrs, R.styleable.IfenquWebView);
        String heightStyle = ta.getString(R.styleable.IfenquWebView_webView_height_style);

        View groupView = LayoutInflater.from(getContext()).inflate(R.layout.ifenqu_webview,this,false);
        progressBar = groupView.findViewById(R.id.pbWebView);
        ifenqu_webView = groupView.findViewById(R.id.ifenqu_webView);

        if ("0".equalsIgnoreCase(heightStyle)){
            ifenqu_webView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
        }else {
            ifenqu_webView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        }
        addView(groupView);
        configWebView();
    }

    /**
     * 是否显示进度条
     * @param isShow
     */
    public void showProgressVisibility(boolean isShow){
        this.isShowProgress = isShow;
    }


    //是否开启Android 注入css
    public void startUpInjectCSS(boolean isOpen){
        this.isOpenInject = isOpen;
    }

    @SuppressLint({"SetJavaScriptEnabled", "JavascriptInterface"})
    private void configWebView() {
        WebSettings settings = ifenqu_webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);
        requestFocus();
        ifenqu_webView.setWebChromeClient(mWebChromClient);
        ifenqu_webView.setWebViewClient(mWebViewClient);
//        ifenqu_webView.addJavascriptInterface(this,"Ifenqu");
    }

    //由于H5这边改动比较大，Android对于所有H5样式，都进行了注入style 文件(ifenqu_style.css)进行修改
    private void injectCSS() {
        try {
            InputStream inputStream = getContext().getAssets().open("ifenqu_style.css");
            byte[] buffer = new byte[inputStream.available()];
            inputStream.read(buffer);
            inputStream.close();
            String encoded = Base64.encodeToString(buffer, Base64.NO_WRAP);
            ifenqu_webView.loadUrl("javascript:(function() {" +
                    "var parent = document.getElementsByTagName('head').item(0);" +
                    "var style = document.createElement('style');" +
                    "style.type = 'text/css';" +
                    // Tell the browser to BASE64-decode the string into your script !!!
                    "style.innerHTML = window.atob('" + encoded + "');" +
                    "parent.appendChild(style)" +
                    "})()");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setCookie(boolean isSet){
        this.isSetCookie = isSet;
    }

    private void setCookie(String url){
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.setAcceptCookie(true);
        String value = "ifq_access_token=" + LoginUtil.getLoginToken().getAccessToken() + ";Max-Age=; Expires=" + LoginUtil.getLoginToken().getAccessTokenExpire() + ";Domain=ifenqu.com;Path=/";
        String value1 = "ifq_refresh_token=" + LoginUtil.getLoginToken().getRefreshToken() + ";Max-Age=; Expires=" + LoginUtil.getLoginToken().getRefreshTokenExpire() + ";Domain=ifenqu.com;Path=/";
        cookieManager.setCookie(url, value);
        cookieManager.setCookie(url, value1);
    }

    /**
     * 加载url
     * @param url
     */
    public void loadUrl(String url){
        if (isSetCookie){
            setCookie(url);
        }
        ifenqu_webView.loadUrl(url);
    }


    /**
     * load html
     * @param url
     */
    public void loadHTML(String url){
        if (isSetCookie){
            setCookie(url);
        }
//        ifenqu_webView.setVisibility(GONE);
        ifenqu_webView.loadDataWithBaseURL(null,url, "text/html", "utf-8",null);
//        ifenqu_webView.reload();
//        ifenqu_webView.setVisibility(VISIBLE);
    }

    public void setWebViewListener(IFenQuWebViewListener listener){
        this.listener = listener;
    }

    public interface IFenQuWebViewListener {
        void onReceivedTitle(String str);
        void onPageLoadFinished (WebView view, String url);
    }

    public void reLoadUrl(){
        ifenqu_webView.reload();
    }

    public boolean canGoBack(){
        return ifenqu_webView.canGoBack();
    }

    public void initialScale(int scale){
        ifenqu_webView.setInitialScale(scale);
    }

    public void goBack(){
        ifenqu_webView.goBack();
    }

    public void onPause(){
        ifenqu_webView.onPause();
    }

    public void onResume(){
        ifenqu_webView.onResume();
    }

    @JavascriptInterface
    public void reSize(final float height){
        ((Activity)getContext()).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ifenqu_webView.setLayoutParams(new LinearLayout.LayoutParams(getResources().getDisplayMetrics().widthPixels, (int) (height * getResources().getDisplayMetrics().density)));
            }
        });
    }

}
