package com.ifenqu.app.view.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.TextView;
import com.ifenqu.app.R;
import com.ifenqu.app.util.StringUtil;
import com.ifenqu.app.util.TimerManager;
import com.ifenqu.app.view.BaseStatusActivity;
import com.ifenqu.app.widget.IFenQuWebView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * webview activity
 */
public class WebViewActivity extends BaseStatusActivity implements TimerManager.TimerListener, IFenQuWebView.IFenQuWebViewListener{

    public static final String INTENT_TITLE = "INTENT_TITLE";
    public static final String INTENT_TIMER = "INTENT_TIMER";
    public static final String INTENT_HAS_TITLE = "INTENT_HAS_TITLE";
    public static final String INTENT_URL = "INTENT_URL";
    public static final int TYPE_HTML = 0;
    public static final int TYPE_URL = 1;
    private static final int TIMER = 5000;
    private static String KEY_WEB_VIEW = "KEY_WEB_VIEW";

    private int type_web_view;
    private boolean hasTimer;
    private boolean hasTitle;
    private boolean isNeedCookie = true;
    private String url;

    @BindView(R.id.ifenqu_webView)
    IFenQuWebView ifenqu_webView;

    @BindView(R.id.tv_skip)
    TextView tv_skip;
    private TimerManager timerManager;

    @OnClick(R.id.tv_skip)
    public void OnClickSkip(View view) {
        finish();
    }


    /**
     * 获取启动这个Activity的Intent
     *
     * @param title
     * @param url
     */
    public static void start(Context context, String title, String url) {
        if (context == null) return;
        Intent intent = new Intent(context, WebViewActivity.class);
        intent.putExtra(INTENT_TITLE, title);
        intent.putExtra(INTENT_URL, url);
        intent.putExtra(KEY_WEB_VIEW, TYPE_URL);
        context.startActivity(intent);
    }

    /**
     * 没有title
     *
     * @param context
     * @param url
     */
    public static void start(Context context, String url) {
        start(context, "", url);
    }

    /**
     * 没有title
     *
     * @param context
     * @param html
     */
    public static void startHtml(Activity context, String html) {
        startHtml(context, html, false, true);
    }

    /**
     * 没有title
     *
     * @param context
     * @param html
     */
    public static void startHtml(Activity context, String html, boolean hasTimer, boolean hasTitle) {
        if (context == null) return;
        Intent intent = new Intent(context, WebViewActivity.class);
        intent.putExtra(INTENT_TITLE, "");
        intent.putExtra(INTENT_URL, html);
        intent.putExtra(KEY_WEB_VIEW, TYPE_HTML);
        intent.putExtra(INTENT_HAS_TITLE, hasTitle);
        intent.putExtra(INTENT_TIMER, hasTimer);
        context.startActivity(intent);
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.web_view_activity);//传this是为了全局滑动返回
        ButterKnife.bind(this);
        showContentView();
        hasTimer = getIntent().getBooleanExtra(INTENT_TIMER, false);
        hasTitle = getIntent().getBooleanExtra(INTENT_HAS_TITLE, true);
        comm_title.setVisibility(hasTitle ? View.VISIBLE : View.GONE);
        comm_title.setTitle(getIntent().getStringExtra(INTENT_TITLE));
        comm_title.setBackBtnListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ifenqu_webView.canGoBack()) {
                    ifenqu_webView.goBack();
                    return;
                }
                finish();
            }
        });
        loadContent();
    }

    private void showTimer() {
        tv_skip.setVisibility(hasTimer ? View.VISIBLE : View.GONE);
        if (hasTimer) {
            timerManager.startTimer(TIMER, this);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void loadContent() {
        timerManager = new TimerManager();
        ifenqu_webView.showProgressVisibility(true);
        ifenqu_webView.setWebViewListener(this);

        type_web_view = getIntent().getIntExtra(KEY_WEB_VIEW, TYPE_URL);
        url = getIntent().getStringExtra(INTENT_URL);
        if (StringUtil.isNotEmpty(url, true) == false) {
            finish();
            return;
        }

        if (type_web_view == TYPE_HTML) {
            ifenqu_webView.loadHTML(url);
        } else {
            url = StringUtil.getCorrectUrl(url);
            ifenqu_webView.startUpInjectCSS(false);
            ifenqu_webView.loadUrl(url);
        }
    }

    @Override
    public void onBackPressed() {
        if (ifenqu_webView.canGoBack()) {
            ifenqu_webView.goBack();
            return;
        }

        super.onBackPressed();
    }

    @Override
    public void finish() {
        super.finish();

        goMain();
        timerManager.cancelTimer();
    }

    //进入主界面
    private void goMain() {
        if (hasTimer) {
            overridePendingTransition(0, 0);
            MainActivity.start(this);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        ifenqu_webView.onPause();
    }

    @Override
    protected void onResume() {
        ifenqu_webView.onResume();
        super.onResume();
    }

    @Override
    public void timer(boolean isFinished, long time) {
        if (!isFinished) {
            tv_skip.setText("跳转 " + time);
        } else {
            finish();
        }
    }

    @Override
    public void onReceivedTitle(String str) {
        comm_title.setTitle(StringUtil.getTrimedString(str));
    }

    @Override
    public void onPageLoadFinished(WebView view, String url) {
        showTimer();
    }
}