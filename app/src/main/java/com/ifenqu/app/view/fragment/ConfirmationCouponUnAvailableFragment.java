package com.ifenqu.app.view.fragment;

import android.os.Bundle;

import com.ifenqu.app.model.CouponModel;
import com.ifenqu.app.view.BaseCouponFragment;
import com.ifenqu.app.view.adapter.viewholder.ViewHolderConstant;

import java.util.ArrayList;

public class ConfirmationCouponUnAvailableFragment extends BaseCouponFragment {

    public static final String KEY_COUPON_UNAVALIABLE = "KEY_COUPON_UNAVALIABLE";

    public static ConfirmationCouponUnAvailableFragment newInstance(ArrayList<CouponModel> list) {
        ConfirmationCouponUnAvailableFragment fragment = new ConfirmationCouponUnAvailableFragment();
        Bundle args = new Bundle();
        args.putSerializable(KEY_COUPON_UNAVALIABLE,list);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void initViews() {
        super.initViews();
        initRecyclerViewAdapter(ViewHolderConstant.TYPE_CONFIRMATION_COUPON_UNAVAILABLE);

        getRefreshView().setLoadMore(false);
        getRefreshView().setRefreshing(false);

        showContentView();
        ArrayList<CouponModel> dataList = (ArrayList<CouponModel>) getArguments().getSerializable(KEY_COUPON_UNAVALIABLE);
        updateAdapter(dataList);
    }

}
