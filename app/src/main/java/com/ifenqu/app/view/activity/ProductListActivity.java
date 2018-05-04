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
import com.ifenqu.app.http.response.ProductListResponse;
import com.ifenqu.app.util.NetworkUtil;
import com.ifenqu.app.util.RecyclerManager;
import com.ifenqu.app.view.BaseStatusActivity;
import com.ifenqu.app.view.adapter.ProductListAdapter;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProductListActivity extends BaseStatusActivity implements OnHttpResponseListener {

    @BindView(R.id.srl_refresh)
    SuperSwipeRefreshLayout srl_refresh;

    @BindView(R.id.rv_recyclerView)
    SwipeMenuRecyclerView rv_recyclerView;

    protected ProductListAdapter productListAdapter;

    public static void start(Context context) {
        if (context == null) return;
        Intent intent = new Intent(context, ProductListActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);
        ButterKnife.bind(this);
        initViews();
        comm_title.setTitle(R.string.product_list_title);
        httpGetProductList();
    }

    private void httpGetProductList() {
        if (!NetworkUtil.checkoutInternet())return;
        showLoading();
        HttpRequest.getProductList(HttpConstant.URL_PRODUCT_LIST_INDEX,this);
    }

    private void initViews() {
        RecyclerManager.getInstance().initWithLinearLayoutmanager(rv_recyclerView);
        productListAdapter = new ProductListAdapter();
        rv_recyclerView.setAdapter(productListAdapter);

        srl_refresh.setOnPullRefreshListener(new SuperSwipeRefreshLayout.OnPullRefreshListener() {
            @Override
            public void onRefresh() {
                if (srl_refresh.isRefreshing())srl_refresh.setRefreshing(false);
            }

            @Override
            public void onPullDistance(int i) {

            }

            @Override
            public void onPullEnable(boolean b) {

            }
        });

        srl_refresh.setOnPushLoadMoreListener(new SuperSwipeRefreshLayout.OnPushLoadMoreListener() {
            @Override
            public void onLoadMore() {
                srl_refresh.setLoadMore(false);
            }

            @Override
            public void onPushDistance(int i) {

            }

            @Override
            public void onPushEnable(boolean b) {

            }
        });
    }

    @Override
    public void onHttpResponse(int requestCode, String resultJson, Exception e) {
        if (requestCode == HttpConstant.URL_PRODUCT_LIST_INDEX){
            if (TextUtils.isEmpty(resultJson) && productListAdapter.getItemCount() ==0){
                showWithoutData();
                return;
            }

            Gson gson = new Gson();
            ProductListResponse response = gson.fromJson(resultJson,ProductListResponse.class);

            if (!response.checkDataValidate())return;
            showContentView();
            productListAdapter.update(response.getData());
        }
    }
}
