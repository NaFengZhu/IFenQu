package com.ifenqu.app.widget;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * 取消viewpager 左右滑动
 */

public class BanSlideViewPager extends ViewPager {

    private static final int MAX_LIMIT = 3;

    public BanSlideViewPager(Context context) {
        this(context,null);
    }

    public BanSlideViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        //处理viewpager 切换item时，重复调用各个fragment onResume
        setOffscreenPageLimit(MAX_LIMIT);
    }


    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return false;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return false;
    }
}
