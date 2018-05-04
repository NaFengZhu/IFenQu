package com.ifenqu.app.view.adapter;

import android.content.res.Resources;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.ifenqu.app.R;
import com.ifenqu.app.app.IfenquApplication;
import com.ifenqu.app.view.fragment.ExpiredCouponFragment;
import com.ifenqu.app.view.fragment.UsedCouponFragment;
import com.ifenqu.app.view.fragment.ValidityCouponFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * 处理优惠券界面的不同tab adapter
 */
public class CouponViewPagerAdapter extends FragmentPagerAdapter {
    private List<Fragment> fragmentList = new ArrayList<>();
    private String[] titleStr = new String[3];

    public CouponViewPagerAdapter(FragmentManager fm) {
        super(fm);
        initFragments();
        initTitles();
    }

    private void initTitles() {
        Resources resources = IfenquApplication.getInstance().getResources();
        titleStr[0] = resources.getString(R.string.coupon_validate);
        titleStr[1] = resources.getString(R.string.coupon_used);
        titleStr[2] = resources.getString(R.string.coupon_expired);
    }

    private void initFragments() {
        ValidityCouponFragment validityCouponFragment = ValidityCouponFragment.newInstance();
        UsedCouponFragment usedCouponFragment = UsedCouponFragment.newInstance();
        ExpiredCouponFragment expiredCouponFragment = ExpiredCouponFragment.newInstance();

        fragmentList.add(validityCouponFragment);
        fragmentList.add(usedCouponFragment);
        fragmentList.add(expiredCouponFragment);
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return titleStr[position];
    }
}
