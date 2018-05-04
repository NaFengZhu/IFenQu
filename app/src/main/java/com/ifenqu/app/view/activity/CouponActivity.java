package com.ifenqu.app.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;

import com.ifenqu.app.R;
import com.ifenqu.app.view.BaseStatusActivity;
import com.ifenqu.app.view.adapter.CouponViewPagerAdapter;
import com.ifenqu.app.widget.CommonTitleView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 我的优惠券
 */
public class CouponActivity extends BaseStatusActivity {

    @BindView(R.id.comm_title)
    CommonTitleView comm_title;

    @BindView(R.id.vp_viewPager)
    ViewPager vp_viewPager;

    @BindView(R.id.tl_tabLayout)
    TabLayout tl_tabLayout;

    public static void start(Context context) {
        if (context == null) return;
        context.startActivity(new Intent(context, CouponActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coupon);
        ButterKnife.bind(this);
        comm_title.setTitle(R.string.coupon_title);
        showWithoutData(R.drawable.my_coupon_icon,R.string.coupon_no_data);
        initViewPager();
        initTabs();
    }

    private void initTabs() {
        tl_tabLayout.setupWithViewPager(vp_viewPager);
//        tl_tabLayout.addTab(tl_tabLayout.newTab());
//        tl_tabLayout.addTab(tl_tabLayout.newTab());
//        tl_tabLayout.addTab(tl_tabLayout.newTab());
    }

    private void initViewPager() {
        CouponViewPagerAdapter couponViewPagerAdapter = new CouponViewPagerAdapter(getSupportFragmentManager());
        vp_viewPager.setAdapter(couponViewPagerAdapter);
    }
}
