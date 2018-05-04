package com.ifenqu.app.view.fragment;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.format.DateUtils;

import com.blankj.utilcode.util.CacheUtils;
import com.github.nuptboyzhb.lib.SuperSwipeRefreshLayout;
import com.ifenqu.app.R;
import com.ifenqu.app.app.Constants;
import com.ifenqu.app.model.PushModel;
import com.ifenqu.app.util.CacheConstant;
import com.ifenqu.app.util.RecyclerManager;
import com.ifenqu.app.view.adapter.CouponAdapter;
import com.ifenqu.app.view.adapter.viewholder.ViewHolderConstant;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 主页 - 发现
 */
public class DiscoveryFragment extends BaseStatusFragment {

    @BindView(R.id.rv_recyclerView)
    SwipeMenuRecyclerView rv_recyclerView;
    //    @BindView(R.id.srl_refresh)
//    SuperSwipeRefreshLayout srl_refresh;
    private CouponAdapter couponAdapter;
    private PushNotificationReceiver pushNotificationReceiver;

    public DiscoveryFragment() {
        // Required empty public constructor
    }

    public static DiscoveryFragment newInstance() {
        DiscoveryFragment fragment = new DiscoveryFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        registerReceiver();
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_discovery;
    }


    @Override
    public void initViews() {
        ButterKnife.bind(this, getRootView());
        initRecyclerView();
        // TODO: 26/3/18 如果有缓存数据 加载缓存数据
        // TODO: 26/3/18 如果没有缓存数据 加载数据
        // TODO: 26/3/18 检查网络
        // TODO: 26/3/18 如果没有网络并且之前没有数据显示 显示无网络状态
        // TODO: 26/3/18 请求消息数据数据

        getMessageList();
    }

    private void getMessageList() {
        ArrayList<PushModel> list = (ArrayList<PushModel>) CacheUtils.getInstance().getSerializable(CacheConstant.KEY_PUSH_MESSAGES_LIST);
        if (list == null || list.size() == 0) {
            showWithoutData(R.drawable.no_news_icon, R.string.discovery_no_news);
            return;
        }
        showContentView();
        couponAdapter.updateData(list);
    }

    private void initRecyclerView() {
        RecyclerManager.getInstance().initWithLinearLayoutmanager(rv_recyclerView);
        couponAdapter = new CouponAdapter(ViewHolderConstant.TYPE_DISCOVERY);
        rv_recyclerView.setAdapter(couponAdapter);

        commonTitleView.setBackBtnVisibility(false);
        commonTitleView.setTitle(R.string.discovery_title);

    }

    class PushNotificationReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            getMessageList();
        }
    }

    private void registerReceiver() {
        pushNotificationReceiver = new PushNotificationReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(Constants.IFENQU_PUSH_NOTIFICATION);
        getContext().registerReceiver(pushNotificationReceiver, filter);
    }

    private void unRegisterReceiver() {
        if (pushNotificationReceiver != null) {
            getContext().unregisterReceiver(pushNotificationReceiver);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unRegisterReceiver();
    }
}
