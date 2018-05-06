package com.ifenqu.app.view.fragment;

import android.os.Bundle;

import com.ifenqu.app.model.CouponModel;
import com.ifenqu.app.view.BaseCouponFragment;
import com.ifenqu.app.view.adapter.viewholder.ViewHolderConstant;

import java.util.ArrayList;

public class ConfirmationCouponAvailableFragment extends BaseCouponFragment {

    public static final String KEY_COUPON_AVALIABLE = "KEY_COUPON_AVALIABLE";

    public static ConfirmationCouponAvailableFragment newInstance(ArrayList<CouponModel> list) {
        ConfirmationCouponAvailableFragment fragment = new ConfirmationCouponAvailableFragment();
        Bundle args = new Bundle();
        args.putSerializable(KEY_COUPON_AVALIABLE,list);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void initViews() {
        super.initViews();
        initRecyclerViewAdapter(ViewHolderConstant.TYPE_CONFIRMATION_COUPON_AVALIABLE);

        getRefreshView().setLoadMore(false);
        getRefreshView().setRefreshing(false);

        showContentView();
        ArrayList<CouponModel> dataList = (ArrayList<CouponModel>) getArguments().getSerializable(KEY_COUPON_AVALIABLE);
        updateAdapter(dataList);
    }

}
