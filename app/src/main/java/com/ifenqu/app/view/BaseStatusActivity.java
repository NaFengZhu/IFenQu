package com.ifenqu.app.view;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ifenqu.app.R;
import com.ifenqu.app.util.CommonUtils;
import com.ifenqu.app.util.PerfectClickListener;
import com.ifenqu.app.util.StatusBarUtil;
import com.ifenqu.app.widget.CommonTitleView;

/**
 * 此类提供无网络状态/数据加载/数据加载失败 等状态展示
 *
 */
public class BaseStatusActivity extends BaseActivity {

    private ViewGroup bindingView;
    private LinearLayout ll_progress_bar;
    private View fail_refresh;
    private AnimationDrawable mAnimationDrawable;
    private ViewGroup mBaseBinding;
    private View ll_no_internet_refresh;
    private View ll_no_data_refresh;
    private ImageView img_data;
    private TextView tv_data_status;
    public CommonTitleView comm_title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    protected <T extends View> T getView(int id) {
        return (T) findViewById(id);
    }

    /**
     * 处理根布局view 逻辑
     * @param layoutResID
     */
    @Override
    public void setContentView(int layoutResID) {
        mBaseBinding = (ViewGroup) LayoutInflater.from(this).inflate(R.layout.activity_base, null, false);
        bindingView = (ViewGroup) LayoutInflater.from(this).inflate(layoutResID, null, false);

        // content
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        bindingView.setLayoutParams(params);
        RelativeLayout mContainer = mBaseBinding.findViewById(R.id.container);
        mContainer.addView(bindingView);
        getWindow().setContentView(mBaseBinding);

        // 设置透明状态栏，兼容4.4
        StatusBarUtil.setColor(this, CommonUtils.getColor(R.color.colorTheme),0);
        ll_progress_bar = getView(R.id.ll_progress_bar);
        ll_no_internet_refresh = getView(R.id.ll_no_internet_refresh);
        ll_no_data_refresh = getView(R.id.ll_data_refresh);
        img_data = getView(R.id.img_data);
        tv_data_status = getView(R.id.tv_data_status);
        fail_refresh = getView(R.id.ll_error_refresh);
        comm_title = getView(R.id.comm_title);
        ImageView img = getView(R.id.img_progress);

        // 加载动画
        mAnimationDrawable = (AnimationDrawable) img.getDrawable();
        // 默认进入页面就开启动画
        if (!mAnimationDrawable.isRunning()) {
            mAnimationDrawable.start();
        }

        // 点击加载失败布局
        fail_refresh.setOnClickListener(new PerfectClickListener() {
            @Override
            protected void onNoDoubleClick(View v) {
                onRefresh();
            }
        });
        bindingView.setVisibility(View.GONE);
    }


    protected void showLoading() {
        //状态加载
        if (ll_progress_bar.getVisibility() != View.VISIBLE) {
            ll_progress_bar.setVisibility(View.VISIBLE);
        }
        // 开始动画
        if (!mAnimationDrawable.isRunning()) {
            mAnimationDrawable.start();
        }

        //内容
        if (bindingView.getVisibility() != View.GONE) {
            bindingView.setVisibility(View.GONE);
        }

        //无数据状态
        if (ll_no_data_refresh.getVisibility() != View.GONE) {
            ll_no_data_refresh.setVisibility(View.GONE);
        }

        //网络状态
        if (ll_no_internet_refresh.getVisibility() != View.GONE) {
            ll_no_internet_refresh.setVisibility(View.GONE);
        }

        //失败状态
        if (fail_refresh.getVisibility() != View.GONE) {
            fail_refresh.setVisibility(View.GONE);
        }
    }

    protected void showContentView() {
        //状态加载
        if (ll_progress_bar.getVisibility() != View.GONE) {
            ll_progress_bar.setVisibility(View.GONE);
        }
        // 开始动画
        if (!mAnimationDrawable.isRunning()) {
            mAnimationDrawable.start();
        }

        //内容
        if (bindingView.getVisibility() != View.VISIBLE) {
            bindingView.setVisibility(View.VISIBLE);
        }

        //无数据状态
        if (ll_no_data_refresh.getVisibility() != View.GONE) {
            ll_no_data_refresh.setVisibility(View.GONE);
        }

        //网络状态
        if (ll_no_internet_refresh.getVisibility() != View.GONE) {
            ll_no_internet_refresh.setVisibility(View.GONE);
        }

        //失败状态
        if (fail_refresh.getVisibility() != View.GONE) {
            fail_refresh.setVisibility(View.GONE);
        }

    }

    protected void showWithoutNetwork() {
        //状态加载
        if (ll_progress_bar.getVisibility() != View.GONE) {
            ll_progress_bar.setVisibility(View.GONE);
        }
        // 开始动画
        if (!mAnimationDrawable.isRunning()) {
            mAnimationDrawable.start();
        }

        //内容
        if (bindingView.getVisibility() != View.GONE) {
            bindingView.setVisibility(View.GONE);
        }

        //无数据状态
        if (ll_no_data_refresh.getVisibility() != View.GONE) {
            ll_no_data_refresh.setVisibility(View.GONE);
        }

        //网络状态
        if (ll_no_internet_refresh.getVisibility() != View.VISIBLE) {
            ll_no_internet_refresh.setVisibility(View.VISIBLE);
        }

        //失败状态
        if (fail_refresh.getVisibility() != View.GONE) {
            fail_refresh.setVisibility(View.GONE);
        }
    }

    /**
     * 自定义无数据状态的图片和显示内容
     * @param resId
     * @param str
     */
    protected void showWithoutData(int resId,int str) {
        //状态加载
        if (ll_progress_bar.getVisibility() != View.GONE) {
            ll_progress_bar.setVisibility(View.GONE);
        }
        // 开始动画
        if (!mAnimationDrawable.isRunning()) {
            mAnimationDrawable.start();
        }

        //内容
        if (bindingView.getVisibility() != View.GONE) {
            bindingView.setVisibility(View.GONE);
        }

        //无数据状态
        if (ll_no_data_refresh.getVisibility() != View.VISIBLE) {
            ll_no_data_refresh.setVisibility(View.VISIBLE);

            img_data.setImageResource(resId);
            tv_data_status.setText(str);
        }

        //网络状态
        if (ll_no_internet_refresh.getVisibility() != View.GONE) {
            ll_no_internet_refresh.setVisibility(View.GONE);
        }

        //失败状态
        if (fail_refresh.getVisibility() != View.GONE) {
            fail_refresh.setVisibility(View.GONE);
        }
    }

    /**
     * 自定义无数据状态的图片和显示内容
     * @param resId
     * @param str
     */
    protected void showWithoutData(int resId,String str) {
        //状态加载
        if (ll_progress_bar.getVisibility() != View.GONE) {
            ll_progress_bar.setVisibility(View.GONE);
        }
        // 开始动画
        if (!mAnimationDrawable.isRunning()) {
            mAnimationDrawable.start();
        }

        //内容
        if (bindingView.getVisibility() != View.GONE) {
            bindingView.setVisibility(View.GONE);
        }

        //无数据状态
        if (ll_no_data_refresh.getVisibility() != View.VISIBLE) {
            ll_no_data_refresh.setVisibility(View.VISIBLE);

            img_data.setImageResource(resId);
            tv_data_status.setText(str);
        }

        //网络状态
        if (ll_no_internet_refresh.getVisibility() != View.GONE) {
            ll_no_internet_refresh.setVisibility(View.GONE);
        }

        //失败状态
        if (fail_refresh.getVisibility() != View.GONE) {
            fail_refresh.setVisibility(View.GONE);
        }
    }

    protected void showWithoutData() {
        //状态加载
        if (ll_progress_bar.getVisibility() != View.GONE) {
            ll_progress_bar.setVisibility(View.GONE);
        }
        // 开始动画
        if (!mAnimationDrawable.isRunning()) {
            mAnimationDrawable.start();
        }

        //内容
        if (bindingView.getVisibility() != View.GONE) {
            bindingView.setVisibility(View.GONE);
        }

        //无数据状态
        if (ll_no_data_refresh.getVisibility() != View.VISIBLE) {
            ll_no_data_refresh.setVisibility(View.VISIBLE);
        }

        //网络状态
        if (ll_no_internet_refresh.getVisibility() != View.GONE) {
            ll_no_internet_refresh.setVisibility(View.GONE);
        }

        //失败状态
        if (fail_refresh.getVisibility() != View.GONE) {
            fail_refresh.setVisibility(View.GONE);
        }
    }

    protected void showError() {
        //状态加载
        if (ll_progress_bar.getVisibility() != View.GONE) {
            ll_progress_bar.setVisibility(View.GONE);
        }
        // 开始动画
        if (!mAnimationDrawable.isRunning()) {
            mAnimationDrawable.start();
        }

        //内容
        if (bindingView.getVisibility() != View.GONE) {
            bindingView.setVisibility(View.GONE);
        }

        //无数据状态
        if (ll_no_data_refresh.getVisibility() != View.GONE) {
            ll_no_data_refresh.setVisibility(View.GONE);
        }

        //网络状态
        if (ll_no_internet_refresh.getVisibility() != View.GONE) {
            ll_no_internet_refresh.setVisibility(View.GONE);
        }

        //失败状态
        if (fail_refresh.getVisibility() != View.VISIBLE) {
            fail_refresh.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 失败后点击刷新
     */
    protected void onRefresh() {

    }

}
