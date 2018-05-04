package com.ifenqu.app.view.adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.ifenqu.app.view.fragment.ConfirmationCouponAvailableFragment;
import com.ifenqu.app.view.fragment.ConfirmationCouponUnAvailableFragment;

public class ConfirmationCouponPagerAdapter extends FragmentPagerAdapter {

    private String[] titleStr = {"可用","不可用"};
    public ConfirmationCouponPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0){
            return ConfirmationCouponAvailableFragment.newInstance();
        }else {
            return ConfirmationCouponUnAvailableFragment.newInstance();
        }
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return titleStr[position];
    }
}
