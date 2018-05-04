package com.ifenqu.app.view.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.ifenqu.app.R;
import com.ifenqu.app.http.HttpConstant;
import com.ifenqu.app.http.HttpRequest;
import com.ifenqu.app.http.response.OnHttpResponseListener;
import com.ifenqu.app.model.ProductCouponTagResponse;
import com.ifenqu.app.util.NetworkUtil;
import com.ifenqu.app.view.BaseActivity;
import com.ifenqu.app.view.adapter.ConfirmationCouponPagerAdapter;
import com.ifenqu.app.widget.CommonTitleView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 确认订单界面 选择优惠券的activity
 */
public class ConfirmationCouponActivity extends BaseActivity implements OnHttpResponseListener{

    public static final String KEY_COUPON_TAG = "KEY_COUPON_TAG";

    @BindView(R.id.vp_viewPager)
    ViewPager vp_viewPager;

    @BindView(R.id.tl_tabLayout)
    TabLayout tl_tabLayout;

    @BindView(R.id.comm_title)
    CommonTitleView comm_title;
    private String[] couponTags;

    public static void start(Activity context, String[] tags,int requestCode) {
        if (context == null) return;
        Intent intent = new Intent(context, ConfirmationCouponActivity.class);
        intent.putExtra(KEY_COUPON_TAG,tags);
        context.startActivityForResult(intent,requestCode);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmation_coupon);
        ButterKnife.bind(this);

        initViewPager();
        initTabs();

        comm_title.setTitle(R.string.coupon_title1);

        couponTags = getIntent().getStringArrayExtra(KEY_COUPON_TAG);
        httpPostCouponInfo();
    }

    private void httpPostCouponInfo() {
        if (!NetworkUtil.checkoutInternet())return;
        HttpRequest.getUserCoupons(HttpConstant.URL_USER_COUNPONS_INDEX,this);
    }

    private void initTabs() {
        tl_tabLayout.setupWithViewPager(vp_viewPager);
    }

    private void initViewPager() {
        ConfirmationCouponPagerAdapter couponViewPagerAdapter = new ConfirmationCouponPagerAdapter(getSupportFragmentManager());
        vp_viewPager.setAdapter(couponViewPagerAdapter);
    }

    @Override
    public void onHttpResponse(int requestCode, String resultJson, Exception e) {
        if (requestCode == HttpConstant.URL_USER_COUNPONS_INDEX){
            if (TextUtils.isEmpty(resultJson))return;
            Gson gson = new Gson();
        }
    }
}
