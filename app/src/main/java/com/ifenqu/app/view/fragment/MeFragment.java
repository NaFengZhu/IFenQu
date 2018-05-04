package com.ifenqu.app.view.fragment;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.blankj.utilcode.util.CacheUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.google.gson.Gson;
import com.ifenqu.app.R;
import com.ifenqu.app.http.HttpConstant;
import com.ifenqu.app.http.HttpRequest;
import com.ifenqu.app.http.response.ProductListResponse;
import com.ifenqu.app.http.response.UserInfoResponse;
import com.ifenqu.app.http.response.OnHttpResponseListener;
import com.ifenqu.app.model.ProductModel;
import com.ifenqu.app.model.TokenModel;
import com.ifenqu.app.model.UserModel;
import com.ifenqu.app.util.CacheConstant;
import com.ifenqu.app.util.NetworkUtil;
import com.ifenqu.app.util.RecyclerManager;
import com.ifenqu.app.view.BaseFragment;
import com.ifenqu.app.view.adapter.ShopHeadAdapter;
import com.ifenqu.app.widget.HeadView;
import com.ifenqu.app.widget.ShopHeadView;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * 主页 - 我的
 */
public class MeFragment extends BaseFragment implements OnHttpResponseListener {

    @BindView(R.id.rv_recyclerView)
    SwipeMenuRecyclerView rv_recyclerView;

    @BindView(R.id.headView)
    HeadView headView;


    private ShopHeadAdapter mShopRecyclerViewAdapter;

    public MeFragment() {
        // Required empty public constructor
    }

    public static MeFragment newInstance() {
        MeFragment fragment = new MeFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_me, null, false);
        ButterKnife.bind(this, view);
        return view;

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initRecyclerView();

        httpGetProductList();
    }

    @Override
    public void onResume() {
        super.onResume();
        httpGetUserInfo();
    }

    private void httpGetUserInfo() {
        TokenModel tokenModel = (TokenModel) CacheUtils.getInstance().getSerializable(CacheConstant.KEY_TOKEN);
        if (tokenModel == null || TextUtils.isEmpty(tokenModel.getAccessToken())) return;
        HttpRequest.getUser(tokenModel.getAccessToken(), HttpConstant.URL_USER_INFO_INDEX, this);
    }

    private void httpGetProductList() {
        if (!NetworkUtil.checkoutInternet())return;
        HttpRequest.getProductList(HttpConstant.URL_PRODUCT_LIST_INDEX,this);
    }

    private void initRecyclerView() {
        RecyclerManager.getInstance().initWithGridLayoutmanager(rv_recyclerView, 3);

//        initRecyclerViewHeadViews();

        mShopRecyclerViewAdapter = new ShopHeadAdapter(ShopHeadView.SHOP_HEAD_ITEM_HOT_TYPE);
        rv_recyclerView.setAdapter(mShopRecyclerViewAdapter);

        headView.setHttpResponseListener(MeFragment.this);
    }

    private void initRecyclerViewHeadViews() {
        headView = new HeadView(getContext());
        headView.setHttpResponseListener(MeFragment.this);
        rv_recyclerView.addHeaderView(headView);
    }

    @Override
    public void onHttpResponse(int requestCode, String resultJson, Exception e) {
        if (requestCode == HttpConstant.URL_USER_INFO_INDEX) {
            //获取用户信息
            getUserInfo(resultJson);
        } else if (requestCode == HttpConstant.URL_SIGNOUT_INDEX) {
            CacheUtils.getInstance().remove(CacheConstant.KEY_TOKEN);
            headView.checkCurrentStatus();
        }else if (requestCode == HttpConstant.URL_PRODUCT_LIST_INDEX){
            Gson gson = new Gson();
            ProductListResponse response = gson.fromJson(resultJson,ProductListResponse.class);
            if (response == null)return;
            if (!response.checkDataValidate())return;
            List<ProductModel> list = response.getData();
            List<ProductModel> temporyList = new ArrayList<>();

            if (list != null && list.size() > 3){
                temporyList = list.subList(0,3);
            }
            mShopRecyclerViewAdapter.update(temporyList);
        }
    }

    private void getUserInfo(String resultJson) {
        if (TextUtils.isEmpty(resultJson)) {
            ToastUtils.showShort(getResources().getString(R.string.login_fail));
            return;
        }

        Gson gson = new Gson();
        UserInfoResponse response = gson.fromJson(resultJson,UserInfoResponse.class);
        if (response.checkDataValidate()){
            UserModel model = response.getData();
            CacheUtils.getInstance().put(CacheConstant.KEY_CURRENT_USER_INFO, model);
            headView.updateData(model);
        }
    }

}
