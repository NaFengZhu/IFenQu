package com.ifenqu.app.view.fragment;


import android.os.Bundle;
import com.ifenqu.app.http.response.OnHttpResponseListener;
import com.ifenqu.app.view.BaseCouponFragment;
import com.ifenqu.app.view.adapter.viewholder.ViewHolderConstant;

import java.util.List;

/**
 * 我的优惠券 - 已过期
 *
 */
public class ExpiredCouponFragment extends BaseCouponFragment implements OnHttpResponseListener{


    public ExpiredCouponFragment() {
        // Required empty public constructor
    }

    public static ExpiredCouponFragment newInstance() {
        ExpiredCouponFragment fragment = new ExpiredCouponFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void initViews() {
        super.initViews();
        initRecyclerViewAdapter(ViewHolderConstant.TYPE_EXPIRED);
        showContentView();
    }

    @Override
    public void onHttpResponse(int requestCode, String resultJson, Exception e) {
        /**
         * 成功/失败
         * 更新adater {@link #updateAdapter(List)}
         */
    }
}
