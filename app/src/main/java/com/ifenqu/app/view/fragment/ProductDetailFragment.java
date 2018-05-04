package com.ifenqu.app.view.fragment;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;

import com.blankj.utilcode.util.LogUtils;
import com.ifenqu.app.R;
import com.ifenqu.app.util.DensityUtil;
import com.ifenqu.app.view.BaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 展示商品详情
 */
public class ProductDetailFragment extends BaseFragment {
    public static final String KEY_CONTENT_WEBVIEW = "KEY_CONTENT_WEBVIEW";
    @BindView(R.id.webView_container)
    LinearLayout webView_container;
    private WebView wvWebView;

    public static ProductDetailFragment getInstance(String content) {
        ProductDetailFragment fragment = new ProductDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putString(KEY_CONTENT_WEBVIEW,content);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_product_detail, null, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initData();
        configWebView();
        showContent();
    }

    private void showContent() {
        Bundle bundle = getArguments();
        if (bundle == null)return;
        String content = bundle.getString(KEY_CONTENT_WEBVIEW);
        wvWebView.loadDataWithBaseURL(null, content, "text/html", "utf-8", null);
        webView_container.addView(wvWebView);
    }

    @SuppressLint({"SetJavaScriptEnabled", "JavascriptInterface"})
    public void initData() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.web_view_layout,webView_container,false);
        wvWebView = view.findViewById(R.id.wv_webView);
    }

    private void configWebView() {
        WebSettings webSettings = wvWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        wvWebView.requestFocus();
        // 设置setWebChromeClient对象
        wvWebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
//                tv_webView_title.setTitle(StringUtil.getTrimedString(title));
            }

            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
            }
        });

        wvWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                wvWebView.loadUrl(url);
                return true;
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
//                tv_webView_title.setTitle(StringUtil.getTrimedString(wvWebView.getUrl()));
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
//                tv_webView_title.setTitle(StringUtil.getTrimedString(wvWebView.getTitle()));
//                onGetWebContentHeight();
            }
        });
    }

    public void onGetWebContentHeight() {
        //重新调整webview高度
        wvWebView.post(new Runnable() {
                           @Override
                           public void run() {
                               wvWebView.measure(0, 0);
                               int measuredHeight = wvWebView.getMeasuredHeight();
                               LogUtils.i("zzz", "measuredHeight=" + measuredHeight);
                               LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, DensityUtil.px2dip(measuredHeight));
                               wvWebView.setLayoutParams(layoutParams);
                               wvWebView.postInvalidate();
                           }
                       });
    }


    @Override
    public void onPause() {
        super.onPause();
        wvWebView.onPause();
    }

    @Override
    public void onResume() {
        wvWebView.onResume();
        super.onResume();
    }
}
