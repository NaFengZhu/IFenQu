package com.ifenqu.app.view.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.ifenqu.app.view.fragment.DiscoveryFragment;
import com.ifenqu.app.view.fragment.ShopFragment;
import com.ifenqu.app.view.fragment.MeFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhunafeng on 17/3/18.
 */

public class BanSlideVPAdapter extends FragmentPagerAdapter {

    private List<Fragment> fragmentList = new ArrayList();

    public BanSlideVPAdapter(FragmentManager fm) {
        super(fm);
        initFragmentList();
    }

    private void initFragmentList() {
        MeFragment meFragment = MeFragment.newInstance();
        DiscoveryFragment discoveryFragment = DiscoveryFragment.newInstance();
        ShopFragment shopFragment = ShopFragment.newInstance();

        fragmentList.add(shopFragment);
        fragmentList.add(discoveryFragment);
        fragmentList.add(meFragment);
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }
}
