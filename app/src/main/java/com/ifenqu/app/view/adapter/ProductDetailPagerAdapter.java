package com.ifenqu.app.view.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.ifenqu.app.model.ProductDetailRelativedModel;
import com.ifenqu.app.view.fragment.ProductDetailFragment;

import java.util.ArrayList;
import java.util.List;

public class ProductDetailPagerAdapter extends FragmentPagerAdapter {

    private List<String> contentList;
    public ProductDetailPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        String content = contentList.get(position);
        ProductDetailFragment fragment = ProductDetailFragment.getInstance(content);
        return fragment;
    }

    @Override
    public int getCount() {
        return contentList == null?0:contentList.size();
    }

    public void update(List<String> stringList){
        this.contentList = stringList;
        notifyDataSetChanged();
    }
}
