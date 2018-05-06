package com.ifenqu.app.view.adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.ifenqu.app.model.CouponModel;
import com.ifenqu.app.view.fragment.ConfirmationCouponAvailableFragment;
import com.ifenqu.app.view.fragment.ConfirmationCouponUnAvailableFragment;

import java.util.ArrayList;
import java.util.List;

public class ConfirmationCouponPagerAdapter extends FragmentPagerAdapter {

    private String[] titleStr = {"可用","不可用"};

    private ArrayList<CouponModel> list;
    public ConfirmationCouponPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    public ConfirmationCouponPagerAdapter(FragmentManager fm,ArrayList<CouponModel> list) {
        super(fm);
        this.list = list;
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0){
            return ConfirmationCouponAvailableFragment.newInstance(list);
        }else {
            return ConfirmationCouponUnAvailableFragment.newInstance(list);
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


    public void updateData(ArrayList<CouponModel> list){
        this.list = list;
        notifyDataSetChanged();
    }
}
