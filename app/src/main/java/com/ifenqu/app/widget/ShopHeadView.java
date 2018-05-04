package com.ifenqu.app.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.ifenqu.app.R;
import com.ifenqu.app.model.ProductModel;
import com.ifenqu.app.util.RecyclerManager;
import com.ifenqu.app.view.activity.ProductListActivity;
import com.ifenqu.app.view.adapter.ShopHeadAdapter;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.listener.OnBannerListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ShopHeadView extends LinearLayout {

    public static final int SHOP_HEAD_ITEM_HOT_TYPE = 0;//热销
    public static final int SHOP_HEAD_ITEM_NEW_TYPE = 1;//新品
    public static final int SHOP_HEAD_ITEM_BRAND_TYPE = 2;//品牌

    @BindView(R.id.banner)
    Banner banner;

    @BindView(R.id.hot_recyclerView)
    RecyclerView hot_recyclerView;

    @BindView(R.id.new_product_recyclerView)
    RecyclerView new_product_recyclerView;

    @BindView(R.id.brand_area_recyclerView)
    RecyclerView brand_area_recyclerView;


    private ShopHeadAdapter hotAdapter;
    private ShopHeadAdapter newAdapter;
    private ShopHeadAdapter brandAdapter;

    public ShopHeadView(Context context) {
        this(context, null);
    }

    public ShopHeadView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ShopHeadView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initViews();
    }

    private void initViews() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.shop_head, null, false);
        addView(view);
        ButterKnife.bind(this, view);
        initBanner();
        initRecyclerView();
    }

    private void initRecyclerView() {
        RecyclerManager.getInstance().initWithLinearLayoutmanagerHorizontal(hot_recyclerView);
        hotAdapter = new ShopHeadAdapter(SHOP_HEAD_ITEM_HOT_TYPE);
        hot_recyclerView.setAdapter(hotAdapter);

        RecyclerManager.getInstance().initWithLinearLayoutmanagerHorizontal(new_product_recyclerView);
        newAdapter = new ShopHeadAdapter(SHOP_HEAD_ITEM_NEW_TYPE);
        new_product_recyclerView.setAdapter(newAdapter);

        RecyclerManager.getInstance().initWithLinearLayoutmanagerHorizontal(brand_area_recyclerView);
        brandAdapter = new ShopHeadAdapter(SHOP_HEAD_ITEM_BRAND_TYPE);
        brand_area_recyclerView.setAdapter(brandAdapter);
    }

    private List<Integer> imageUrl = new ArrayList<>();

    private void initBanner() {
        imageUrl.clear();
        imageUrl.add(R.drawable.banner03);


        banner.setBannerStyle(BannerConfig.NOT_INDICATOR);
        banner.setImageLoader(new GlideImageLoader());
        banner.isAutoPlay(true);
        banner.setImages(imageUrl);
        banner.setIndicatorGravity(BannerConfig.CENTER);
        banner.setDelayTime(1500);
        banner.start();

        banner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
                ProductListActivity.start(getContext());
            }
        });
    }

    public void bannerStartAutoPlay() {
        if (banner != null) {
            banner.startAutoPlay();
        }
    }

    public void bannerStopAutoPlay() {
        if (banner != null) {
            banner.stopAutoPlay();
        }
    }

    public void updateHotAdater(List<ProductModel> list) {
        hotAdapter.update(list);
    }

    public void updateNewAdater(List<ProductModel> list) {
        newAdapter.update(list);
        updateBrandAdater(null);//暂时显示数据而已
    }

    public void updateBrandAdater(List<ProductModel> list) {
        List<ProductModel> testList = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            ProductModel model = new ProductModel();
            model.setGoodsId(i);
            testList.add(model);
        }
        brandAdapter.update(testList);
    }

    @OnClick(R.id.ll_get_more)
    public void onClickMoreProducts(View view){
        ProductListActivity.start(view.getContext());
    }
}
