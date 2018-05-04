package com.ifenqu.app.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import com.github.nuptboyzhb.lib.SuperSwipeRefreshLayout;
import com.google.gson.Gson;
import com.ifenqu.app.R;
import com.ifenqu.app.http.HttpConstant;
import com.ifenqu.app.http.HttpRequest;
import com.ifenqu.app.http.response.OnHttpResponseListener;
import com.ifenqu.app.http.response.OrderListResponse;
import com.ifenqu.app.model.SysOrderAgentModel;
import com.ifenqu.app.model.SysOrderModel;
import com.ifenqu.app.util.NetworkUtil;
import com.ifenqu.app.util.RecyclerManager;
import com.ifenqu.app.view.BaseStatusActivity;
import com.ifenqu.app.view.adapter.CouponAdapter;
import com.ifenqu.app.view.adapter.viewholder.ViewHolderConstant;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OrderListActivity extends BaseStatusActivity implements OnHttpResponseListener{

    @BindView(R.id.srl_refresh)
    SuperSwipeRefreshLayout srl_refresh;

    @BindView(R.id.rv_recyclerView)
    SwipeMenuRecyclerView rv_recyclerView;

    private CouponAdapter mCouponAdapter;
    private static final int PAGE_SIZE = 20;
    private static final int OFFSET = 1;

    public static void start(Context context) {
        if (context == null) return;
        context.startActivity(new Intent(context, OrderListActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_list);
        ButterKnife.bind(this);
        comm_title.setTitle(R.string.order_list_title);
        initRecyclerViewAdapter();
        httpPostOrderList();
    }

    private void httpPostOrderList() {
        if (!NetworkUtil.checkoutInternet()){
            showWithoutNetwork();
            return;
        }
//        showWithoutData(R.drawable.my_order_icon,R.string.order_list_no_data);
        showLoading();
        HttpRequest.getOrderList(OFFSET,PAGE_SIZE,HttpConstant.URL_ORDER_LIST_INDEX,null,this);
    }

    public void initRecyclerViewAdapter() {
        RecyclerManager.getInstance().initWithLinearLayoutmanager(rv_recyclerView);
        mCouponAdapter = new CouponAdapter(ViewHolderConstant.TYPE_ORDER_LIST);
        rv_recyclerView.setAdapter(mCouponAdapter);

    }

    @Override
    public void onHttpResponse(int requestCode, String resultJson, Exception e) {
        if (HttpConstant.URL_ORDER_LIST_INDEX == requestCode){
            if (TextUtils.isEmpty(resultJson)){
                showError();
                return;
            }

            Gson gson =new Gson();
            OrderListResponse response = gson.fromJson(resultJson,OrderListResponse.class);

            if (!response.checkDataValidate()){
                showError();
                return;
            }
            SysOrderAgentModel orderAgentModel = response.getData();
            if ((orderAgentModel.getTotal() == 0 || orderAgentModel.getRows() == null) && mCouponAdapter.getItemCount() == 0){
                showWithoutData();
                return;
            }
            List<SysOrderModel> list = orderAgentModel.getRows();
            showContentView();
//            mCouponAdapter.updateData(list);
        }
    }

    @Override
    protected void onRefresh() {
        super.onRefresh();
        httpPostOrderList();
    }
}
