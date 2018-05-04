package com.ifenqu.app.view.fragment;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.ifenqu.app.R;
import com.ifenqu.app.http.HttpConstant;
import com.ifenqu.app.http.HttpRequest;
import com.ifenqu.app.http.response.OnHttpResponseListener;
import com.ifenqu.app.http.response.ProductListResponse;
import com.ifenqu.app.util.NetworkUtil;
import com.ifenqu.app.util.RecyclerManager;
import com.ifenqu.app.view.BaseFragment;
import com.ifenqu.app.view.activity.ProductListActivity;
import com.ifenqu.app.view.adapter.ShopRecyclerViewAdapter;
import com.ifenqu.app.widget.ShopHeadView;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 主页 - 商城
 */
public class ShopFragment extends BaseFragment implements OnHttpResponseListener{

    @BindView(R.id.rv_recyclerView)
    SwipeMenuRecyclerView rv_recyclerView;

    private ShopRecyclerViewAdapter mShopRecyclerViewAdapter;
    private ShopHeadView shopHeadView;

    public ShopFragment() {
        // Required empty public constructor
    }

    public static ShopFragment newInstance() {
        ShopFragment fragment = new ShopFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_shop, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        initRecyclerView();

        httpGetHot();
        httpGetBrand();
        httpGetNew();
        httpGetProductList();
        // TODO: 26/3/18 如果有缓存数据 加载缓存数据
        // TODO: 26/3/18 如果没有缓存数据 加载数据
        // TODO: 26/3/18 检查网络
        // TODO: 26/3/18 如果没有网络并且之前没有数据显示 显示无网络状态
        // TODO: 26/3/18 请求商店数据
    }

    private void httpGetHot() {
        if (!NetworkUtil.checkoutInternet())return;
        HttpRequest.getHotProducts(HttpConstant.URL_PRODUCT_V1_PRODUCTS_HOT_SALE_INDEX,this);
    }

    private void httpGetBrand() {
        if (!NetworkUtil.checkoutInternet())return;
//        HttpRequest.getNewProducts(HttpConstant.URL_PRODUCT_V1_PRODUCTS_HOT_SALE_INDEX,this);
    }

    private void httpGetProductList() {
        if (!NetworkUtil.checkoutInternet())return;
        HttpRequest.getProductList(HttpConstant.URL_PRODUCT_LIST_INDEX,this);
    }

    private void httpGetNew() {
        if (!NetworkUtil.checkoutInternet())return;
        HttpRequest.getNewProducts(HttpConstant.URL_PRODUCT_V1_PRODUCTS_NEW_PRODUCT_INDEX,this);
    }

    private void initRecyclerView() {
        RecyclerManager.getInstance().initWithGridLayoutmanager(rv_recyclerView,2);

        initRecyclerViewHeadViews();

        mShopRecyclerViewAdapter = new ShopRecyclerViewAdapter();
        rv_recyclerView.setAdapter(mShopRecyclerViewAdapter);
    }

    private void initRecyclerViewHeadViews() {
        shopHeadView = new ShopHeadView(getContext());
        rv_recyclerView.addHeaderView(shopHeadView);

        View footView = LayoutInflater.from(getContext()).inflate(R.layout.layout_foot,rv_recyclerView,false);
        footView.findViewById(R.id.rl_more).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProductListActivity.start(v.getContext());
            }
        });
        rv_recyclerView.addFooterView(footView);
    }

    @Override
    public void onStart() {
        super.onStart();
        shopHeadView.bannerStartAutoPlay();
    }

    @Override
    public void onStop() {
        super.onStop();
        shopHeadView.bannerStopAutoPlay();

    }

    private View getView(int layoutResId) {
        return LayoutInflater.from(getContext()).inflate(layoutResId, null, false);
    }

    @Override
    public void onHttpResponse(int requestCode, String resultJson, Exception e) {
        if (requestCode == HttpConstant.URL_SIGNIN_PHONE_INDEX){
            // TODO: 26/3/18 获取成功
            // TODO: 26/3/18 获取失败
            // TODO: 26/3/18 判断当前是否有数据展示，没有显示无数据状态，有泽显示加载框框
            // TODO: 26/3/18 如果有数据 缓存数据
        }else if (requestCode == HttpConstant.URL_PRODUCT_V1_PRODUCTS_HOT_SALE_INDEX){
            //热销榜
            Gson gson = new Gson();
            ProductListResponse response = gson.fromJson(resultJson,ProductListResponse.class);
            if (response == null)return;
            if (!response.checkDataValidate())return;
            shopHeadView.updateHotAdater(response.getData());
        }else if (requestCode == HttpConstant.URL_PRODUCT_V1_PRODUCTS_NEW_PRODUCT_INDEX){
            //新品首发
            Gson gson = new Gson();
            ProductListResponse response = gson.fromJson(resultJson,ProductListResponse.class);
            if (response == null)return;
            if (!response.checkDataValidate())return;
            shopHeadView.updateNewAdater(response.getData());
        }else if (requestCode == HttpConstant.URL_PRODUCT_LIST_INDEX){
            Gson gson = new Gson();
            ProductListResponse response = gson.fromJson(resultJson,ProductListResponse.class);
            if (response== null)return;
            if (!response.checkDataValidate())return;
            mShopRecyclerViewAdapter.update(response.getData());
        }
    }
}
