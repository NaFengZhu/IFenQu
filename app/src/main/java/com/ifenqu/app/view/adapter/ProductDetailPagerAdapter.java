package com.ifenqu.app.view.adapter;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import com.ifenqu.app.view.fragment.ProductDetailFragment;
import java.util.List;

public class ProductDetailPagerAdapter extends PagerAdapter {

    private String[] titles = {"产品详情","购前说明","常见回答"};
    private List<String> contentList;
    private List<View> contentList1;


//    public ProductDetailPagerAdapter(FragmentManager fm) {
//        super(fm);
//    }

//    @Override
//    public Fragment getItem(int position) {
//        String content = contentList.get(position);
//        ProductDetailFragment fragment = ProductDetailFragment.getInstance(content);
//        return fragment;
//    }

    @Override
    public int getCount() {
        return contentList1 == null?0:contentList1.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    public void update(List<String> stringList){
        this.contentList = stringList;
        notifyDataSetChanged();
    }

    public void update1(List<View> stringList){
        this.contentList1 = stringList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        container.addView(contentList1.get(position));
        return contentList1.get(position);
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView(contentList1.get(position));
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }

}
