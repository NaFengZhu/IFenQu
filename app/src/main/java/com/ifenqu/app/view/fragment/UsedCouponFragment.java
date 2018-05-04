package com.ifenqu.app.view.fragment;


import android.os.Bundle;
import com.ifenqu.app.http.response.OnHttpResponseListener;
import com.ifenqu.app.view.BaseCouponFragment;
import com.ifenqu.app.view.adapter.viewholder.ViewHolderConstant;

import java.util.List;

/**
 * 我的优惠券 - 已使用
 *
 */
public class UsedCouponFragment extends BaseCouponFragment implements OnHttpResponseListener{


    public UsedCouponFragment() {
        // Required empty public constructor
    }

    public static UsedCouponFragment newInstance() {
        UsedCouponFragment fragment = new UsedCouponFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void initViews() {
        super.initViews();
        initRecyclerViewAdapter(ViewHolderConstant.TYPE_USED);
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
