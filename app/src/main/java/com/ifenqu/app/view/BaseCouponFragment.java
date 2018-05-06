package com.ifenqu.app.view;

import android.view.View;

import com.github.nuptboyzhb.lib.SuperSwipeRefreshLayout;
import com.ifenqu.app.R;
import com.ifenqu.app.model.CouponModel;
import com.ifenqu.app.model.SysOrderModel;
import com.ifenqu.app.util.NetworkUtil;
import com.ifenqu.app.util.RecyclerManager;
import com.ifenqu.app.view.adapter.CouponAdapter;
import com.ifenqu.app.view.fragment.BaseStatusFragment;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 处理优惠券的基类
 *
 */
public class BaseCouponFragment extends BaseStatusFragment {

    @BindView(R.id.srl_refresh)
    SuperSwipeRefreshLayout srl_refresh;

    @BindView(R.id.rv_recyclerView)
    SwipeMenuRecyclerView rv_recyclerView;

    protected CouponAdapter mCouponAdapter;

    @Override
    public void initViews() {
        ButterKnife.bind(this,getRootView());
        commonTitleView.setVisibility(View.GONE);
        RecyclerManager.getInstance().initWithLinearLayoutmanager(rv_recyclerView);
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_base_coupon;
    }

    public void initRecyclerViewAdapter(int viewType) {
        mCouponAdapter = new CouponAdapter(viewType);
        rv_recyclerView.setAdapter(mCouponAdapter);
    }

    public void updateAdapter(List<CouponModel> objectList){
        if (mCouponAdapter != null){
            mCouponAdapter.updateData(objectList);
        }
    }

    public int getCount(){
        return mCouponAdapter == null?0:mCouponAdapter.getItemCount();
    }

    public boolean checkoutNetWork(){
        return NetworkUtil.checkoutInternet();
    }

    public SuperSwipeRefreshLayout getRefreshView(){
        return srl_refresh;
    }
}
