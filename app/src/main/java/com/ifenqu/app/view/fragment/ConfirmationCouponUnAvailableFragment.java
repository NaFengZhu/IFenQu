package com.ifenqu.app.view.fragment;

import android.os.Bundle;

import com.ifenqu.app.view.BaseCouponFragment;
import com.ifenqu.app.view.adapter.viewholder.ViewHolderConstant;

public class ConfirmationCouponUnAvailableFragment extends BaseCouponFragment {


    public static ConfirmationCouponUnAvailableFragment newInstance() {
        ConfirmationCouponUnAvailableFragment fragment = new ConfirmationCouponUnAvailableFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void initViews() {
        super.initViews();
        initRecyclerViewAdapter(ViewHolderConstant.TYPE_CONFIRMATION_COUPON_UNAVAILABLE);
    }

}
