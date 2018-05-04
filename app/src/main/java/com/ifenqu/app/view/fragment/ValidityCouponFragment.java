package com.ifenqu.app.view.fragment;


import android.os.Bundle;

import com.ifenqu.app.R;
import com.ifenqu.app.http.response.OnHttpResponseListener;
import com.ifenqu.app.view.BaseCouponFragment;
import com.ifenqu.app.view.adapter.viewholder.ViewHolderConstant;

import java.util.List;

/**
 * 我的优惠券 - 未使用
 *
 */
public class ValidityCouponFragment extends BaseCouponFragment implements OnHttpResponseListener{

    public static ValidityCouponFragment newInstance() {
        ValidityCouponFragment fragment = new ValidityCouponFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void initViews() {
        super.initViews();
        initRecyclerViewAdapter(ViewHolderConstant.TYPE_VALIDATE);
    }

    @Override
    public void onHttpResponse(int requestCode, String resultJson, Exception e) {
        /**
         * 成功/失败
         * 更新adater {@link #updateAdapter(List)}
         */
    }
}
