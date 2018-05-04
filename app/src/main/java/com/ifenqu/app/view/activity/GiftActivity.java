package com.ifenqu.app.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.github.nuptboyzhb.lib.SuperSwipeRefreshLayout;
import com.ifenqu.app.R;
import com.ifenqu.app.http.response.OnHttpResponseListener;
import com.ifenqu.app.util.RecyclerManager;
import com.ifenqu.app.view.BaseStatusActivity;
import com.ifenqu.app.view.adapter.CouponAdapter;
import com.ifenqu.app.view.adapter.viewholder.ViewHolderConstant;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 我的权益券
 */
public class GiftActivity extends BaseStatusActivity implements OnHttpResponseListener{

    @BindView(R.id.srl_refresh)
    SuperSwipeRefreshLayout srl_refresh;

    @BindView(R.id.rv_recyclerView)
    SwipeMenuRecyclerView rv_recyclerView;

    private CouponAdapter mCouponAdapter;

    public static void start(Context context) {
        if (context == null) return;
        context.startActivity(new Intent(context, GiftActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gift);
        ButterKnife.bind(this);

        comm_title.setTitle(R.string.gift_title);
        initRecyclerViewAdapter();
        showWithoutData(R.drawable.my_gift,R.string.gift_no_data);
    }

    public void initRecyclerViewAdapter() {
        RecyclerManager.getInstance().initWithLinearLayoutmanager(rv_recyclerView);
        mCouponAdapter = new CouponAdapter(ViewHolderConstant.TYPE_GIFT);
        rv_recyclerView.setAdapter(mCouponAdapter);
    }

    @Override
    public void onHttpResponse(int requestCode, String resultJson, Exception e) {

    }
}
