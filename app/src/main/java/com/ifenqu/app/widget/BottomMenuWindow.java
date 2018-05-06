package com.ifenqu.app.widget;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;
import com.ifenqu.app.R;
import com.ifenqu.app.model.ConfirmBusinessModel;
import com.ifenqu.app.model.GoodDetailModel;
import com.ifenqu.app.model.GoodModel;
import com.ifenqu.app.model.ProductDetailGoodsModel;
import com.ifenqu.app.model.eventbusmodel.ProductDetailEven;
import com.ifenqu.app.util.RecyclerManager;
import com.ifenqu.app.util.StringUtil;
import com.ifenqu.app.view.BaseActivity;
import com.ifenqu.app.view.activity.ProductConfirmationActivity;
import com.ifenqu.app.view.activity.ProductDetailActivity;
import com.ifenqu.app.view.adapter.ProductDetailAdapter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.text.DecimalFormat;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 通用底部弹出菜单
 */
public class BottomMenuWindow extends BaseActivity implements IFenQuWebView.IFenQuWebViewListener {
    private static final String KEY_GOOD = "KEY_GOOD";

    @BindView(R.id.vBaseBottomWindowRoot)
    LinearLayout vBaseBottomWindowRoot;

    @BindView(R.id.recyclerView_bottom)
    RecyclerView recyclerView_bottom;

    @BindView(R.id.iv_prodct_pic)
    ImageView iv_prodct_pic;

    @BindView(R.id.total_price)
    TextView total_price;

    @BindView(R.id.tv_each_terms_price)
    TextView tv_each_terms_price;

    @BindView(R.id.tv_each_terms_price_tag)
    TextView tv_each_terms_price_tag;

    @BindView(R.id.wv_product_img)
    IFenQuWebView wv_product_img;

    private boolean isExit = false;
    private ProductDetailAdapter mProductDetailAdapter;
    private GoodModel goodModel;
    private GoodDetailModel colorModel;
    private GoodDetailModel styleModel;

    /**
     * 启动BottomMenuWindow的Intent
     *
     * @param context
     * @param goodModel
     * @return
     */
    public static void startForResult(Activity context, GoodModel goodModel, int requestCode) {
        if (context == null) return;
        Intent intent = new Intent(context, BottomMenuWindow.class);
        intent.putExtra(KEY_GOOD, goodModel);
        context.startActivityForResult(intent, requestCode);
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bottom_menu_window);
        ButterKnife.bind(this);
        vBaseBottomWindowRoot.startAnimation(AnimationUtils.loadAnimation(this, R.anim.bottom_window_enter));
        initViews();
        setData();
        setInitialPrice();
    }

    private void setData() {
        goodModel = (GoodModel) getIntent().getSerializableExtra(KEY_GOOD);
        if (goodModel == null) return;
        mProductDetailAdapter.update(goodModel);
        wv_product_img.loadHTML(goodModel.getWebView_content());

        colorModel = goodModel.getCurrentColorItem();
        styleModel = goodModel.getCurrentStyleItem();
    }

    private void setInitialPrice() {
        if (goodModel == null) return;
        String priceStr, termPrice;

        if (goodModel.isOnlyOnePrice()) {
            termPrice = StringUtil.getPrice(goodModel.getTermsPrice());
            priceStr = StringUtil.getPrice(goodModel.getLowPrice());
        } else {
            termPrice = StringUtil.getPrice(goodModel.getTermsPrice());
            priceStr = StringUtil.getPrice(goodModel.getLowPrice()) + " - " + StringUtil.getPrice(goodModel.getHighPrice());
        }

        tv_each_terms_price_tag.setVisibility(goodModel.isOnlyOnePrice() ? View.GONE : View.VISIBLE);
        tv_each_terms_price.setText(termPrice);
        total_price.setText(priceStr);
    }

    private String generatedPriceInfo(double productPrice) {
        double result = productPrice / 12;
        DecimalFormat decimalFormat = new DecimalFormat("0.00");
        return decimalFormat.format(result);
    }

    @OnClick(R.id.view_empty)
    public void onClickEmpty(View view) {
        finish();
    }

    private void initViews() {
        RecyclerManager.getInstance().initWithLinearLayoutmanager(recyclerView_bottom);
        mProductDetailAdapter = new ProductDetailAdapter();
        recyclerView_bottom.setAdapter(mProductDetailAdapter);

        wv_product_img.initialScale(1);
        wv_product_img.setWebViewListener(this);
    }

    @SuppressLint("HandlerLeak")
    public Handler exitHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            Intent intent = new Intent();
            intent.putExtra(ProductDetailActivity.BUNDLE_KEY_COLOR, colorModel);
            intent.putExtra(ProductDetailActivity.BUNDLE_KEY_STYLE, styleModel);
            setResult(RESULT_OK, intent);

            BottomMenuWindow.super.finish();
        }
    };

    /**
     * 带动画退出,并使退出事件只响应一次
     */
    @Override
    public void finish() {
        if (isExit) {
            return;
        }
        isExit = true;

        vBaseBottomWindowRoot.startAnimation(AnimationUtils.loadAnimation(this, R.anim.bottom_window_exit));
        vBaseBottomWindowRoot.setVisibility(View.GONE);

        exitHandler.sendEmptyMessageDelayed(0, 200);
    }

    @OnClick(R.id.tv_pay)
    public void onClickPay(View view) {
        if (colorModel == null) {
            ToastUtils.showShort("请选择颜色");
            return;
        }

        if (styleModel == null) {
            ToastUtils.showShort("请选择版本配置");
            return;
        }

        goToPay();

        finish();
    }

    private void goToPay() {
        ConfirmBusinessModel confirmBusinessModel = new ConfirmBusinessModel();
        confirmBusinessModel.setColorModel(colorModel);
        confirmBusinessModel.setProductId(goodModel.getProductId());
        confirmBusinessModel.setStyleModel(styleModel);
        confirmBusinessModel.setProductName(goodModel.getProductName());
        List<ProductDetailGoodsModel> list = goodModel.getGoodsList();
        int colorModelSubTypeDictId = colorModel.getSubTypeDictId();
        int styleModelSubTypeDictId = styleModel.getSubTypeDictId();
        int amount = 0;
        int goodsId = 0;
        int doubleCheck = 0;
        for (int i = 0; i < list.size(); i++) {
            ProductDetailGoodsModel item = list.get(i);
            if (item == null) continue;
            List<GoodDetailModel> goodDetailModels = item.getGoodsDictDetailModelList();
            if (goodDetailModels == null) continue;
            for (int j = 0; j < goodDetailModels.size(); j++) {
                GoodDetailModel goodDetailModel = goodDetailModels.get(j);
                if (goodDetailModel == null) continue;
                int subTypeDictId = goodDetailModel.getSubTypeDictId();
                if (colorModelSubTypeDictId == subTypeDictId || styleModelSubTypeDictId == subTypeDictId) {
                    doubleCheck++;
                }
            }
            if (doubleCheck >= 2) {
                amount = item.getAmount();
                goodsId = item.getGoodsId();
                break;
            }
        }
        confirmBusinessModel.setGoodsId(goodsId + "");
        confirmBusinessModel.setTotalPrice(String.valueOf(amount));

        executeBuy(confirmBusinessModel);
    }

    private void executeBuy(ConfirmBusinessModel colorModel) {
        ProductConfirmationActivity.start(this, colorModel);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void eventChooseProductStyle(ProductDetailEven event) {
        if (event == null) return;
        if (event.getType() == 0) {
            this.colorModel = event.getGoodDetailModel();
        } else {
            this.styleModel = event.getGoodDetailModel();
        }

        if (styleModel == null && colorModel == null) {
            setInitialPrice();
            return;
        }

        calculateCurrentPrice();
    }

    private void calculateCurrentPrice() {
        if (goodModel == null) return;
        if (colorModel != null && styleModel != null) {
            List<ProductDetailGoodsModel> goodsModelList = goodModel.getGoodsList();
            if (goodsModelList == null || goodsModelList.size() == 0) return;
            int goodsSize = goodsModelList.size();
            for (int i = 0; i < goodsSize; i++) {
                ProductDetailGoodsModel item = goodsModelList.get(i);
                if (item == null) continue;
                boolean isHas = calculateCurrentType(item.getGoodsDictDetailModelList());
                tv_each_terms_price_tag.setVisibility(isHas?View.GONE:View.VISIBLE);
                if (isHas) {
                    total_price.setText(StringUtil.getPrice(item.getAmount()));
                    tv_each_terms_price.setText(generatedPriceInfo(item.getAmount()));
                    return;
                }
            }

        }
    }

    private boolean calculateCurrentType(List<GoodDetailModel> item) {
        if (item == null) return false;
        int num = item.size();
        int subColorId = colorModel.getSubTypeDictId();
        int subTypeId = styleModel.getSubTypeDictId();
        boolean hasColor = false, hasType = false;
        for (int i = 0; i < num; i++) {
            GoodDetailModel model = item.get(i);
            if (model == null) continue;
            if (model.getSubTypeDictId() == subColorId) {
                hasColor = true;
                continue;
            }

            if (model.getSubTypeDictId() == subTypeId) {
                hasType = true;
                continue;
            }
        }
        return hasColor && hasType;
    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onReceivedTitle(String str) {

    }

    @Override
    public void onPageLoadFinished(WebView view, String url) {
        iv_prodct_pic.setVisibility(View.GONE);
        wv_product_img.setVisibility(View.VISIBLE);
    }

}