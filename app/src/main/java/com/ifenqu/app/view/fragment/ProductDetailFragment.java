package com.ifenqu.app.view.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import com.ifenqu.app.R;
import com.ifenqu.app.view.BaseFragment;
import com.ifenqu.app.widget.IFenQuWebView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 展示商品详情
 */
public class ProductDetailFragment extends BaseFragment implements IFenQuWebView.IFenQuWebViewListener{
    public static final String KEY_CONTENT_WEBVIEW = "KEY_CONTENT_WEBVIEW";

    @BindView(R.id.ifenqu_webView)
    IFenQuWebView ifenqu_webView;

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
        View view = inflater.inflate(R.layout.fragment_product_detail, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        showContent();
    }

    private void showContent() {
        Bundle bundle = getArguments();
        if (bundle == null)return;
        String content = bundle.getString(KEY_CONTENT_WEBVIEW);
        ifenqu_webView.loadHTML(content);
    }

    @Override
    public void onPause() {
        super.onPause();
        ifenqu_webView.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        ifenqu_webView.onResume();
    }

    @Override
    public void onReceivedTitle(String str) {

    }

    @Override
    public void onPageLoadFinished(WebView view, String url) {
//        ifenqu_webView.setVisibility(View.VISIBLE);
        int height = view.getContentHeight();
    }
}
