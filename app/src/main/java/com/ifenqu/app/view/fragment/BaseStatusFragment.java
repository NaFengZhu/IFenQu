package com.ifenqu.app.view.fragment;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ifenqu.app.R;
import com.ifenqu.app.util.PerfectClickListener;
import com.ifenqu.app.view.BaseFragment;
import com.ifenqu.app.widget.CommonTitleView;

public abstract class BaseStatusFragment extends BaseFragment {

    private ViewGroup bindingView;
    private LinearLayout ll_progress_bar;
    private View fail_refresh;
    private AnimationDrawable mAnimationDrawable;
    private ViewGroup mBaseBinding;
    private View ll_no_internet_refresh;
    private View ll_no_data_refresh;
    private ImageView img_data;
    private TextView tv_data_status;
    public CommonTitleView commonTitleView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_base,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
    }

    protected <T extends View> T getView(int id) {
        return (T) getView().findViewById(id);
    }

    public View getRootView(){
        return mBaseBinding;
    }

    public void init(){
        mBaseBinding = (ViewGroup) getView();
        bindingView = (ViewGroup) LayoutInflater.from(getContext()).inflate(getLayoutId(), null, false);

        // content
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        bindingView.setLayoutParams(params);
        RelativeLayout mContainer = mBaseBinding.findViewById(R.id.container);
        mContainer.addView(bindingView);

        ll_progress_bar = getView(R.id.ll_progress_bar);
        ll_no_internet_refresh = getView(R.id.ll_no_internet_refresh);
        ll_no_data_refresh = getView(R.id.ll_data_refresh);
        img_data = getView(R.id.img_data);
        tv_data_status = getView(R.id.tv_data_status);
        fail_refresh = getView(R.id.ll_error_refresh);
        commonTitleView = getView(R.id.comm_title);
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
                showLoading();
                onRefresh();
            }
        });
        bindingView.setVisibility(View.GONE);

        initViews();
    }

    /**
     * 继承BaseStatusFragment 只需要实现次方法
     * 无需加载{@link #onCreateView(LayoutInflater, ViewGroup, Bundle)}
     * 无需加载{@link #onViewCreated(View, Bundle)}
     */
    public abstract void initViews();

    /**
     *
     * @return
     */
    public abstract int getLayoutId();


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

    protected void showWithoutData(int resId,int resStr) {
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
            tv_data_status.setText(resStr);
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
