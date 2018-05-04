package com.ifenqu.app.view.adapter.viewholder;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;

import com.ifenqu.app.R;

import butterknife.ButterKnife;


public class ValidateCouponViewHolder extends BaseViewHolder {

    public ValidateCouponViewHolder(ViewGroup parent) {
        super(parent,R.layout.item_validate);//???
        ButterKnife.bind(this, (Activity) parent.getContext());
        initView();
    }

    @Override
    public void bindData(Object o) {

    }

    private View initView() {
        return null;
    }


}
