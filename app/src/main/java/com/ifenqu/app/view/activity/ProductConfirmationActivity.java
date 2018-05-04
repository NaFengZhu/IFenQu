package com.ifenqu.app.view.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.blankj.utilcode.util.CacheUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.google.gson.Gson;
import com.ifenqu.app.R;
import com.ifenqu.app.http.HttpConstant;
import com.ifenqu.app.http.HttpRequest;
import com.ifenqu.app.http.response.OnHttpResponseListener;
import com.ifenqu.app.http.response.OrderResponse;
import com.ifenqu.app.model.AddressBusiness;
import com.ifenqu.app.model.ConfirmBusinessModel;
import com.ifenqu.app.model.ProductCouponTagResponse;
import com.ifenqu.app.model.ReceiptBusiness;
import com.ifenqu.app.util.CacheConstant;
import com.ifenqu.app.util.LoginUtil;
import com.ifenqu.app.util.NetworkUtil;
import com.ifenqu.app.util.StringUtil;
import com.ifenqu.app.view.BaseActivity;
import com.ifenqu.app.widget.AlertDialog;
import com.ifenqu.app.widget.CommonTitleView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 确认订单页面
 */
public class ProductConfirmationActivity extends BaseActivity implements OnHttpResponseListener {

    public static final String KEY_BUNDLE = "KEY_BUNDLE";
    public static final int REQUEST_CODE_COUPONS = 0;

    @BindView(R.id.comm_title)
    CommonTitleView comm_title;

    @BindView(R.id.confirm_product_name)
    TextView confirm_product_name;

    @BindView(R.id.iv_receipt_type)
    TextView iv_receipt_type;

    /**
     * 爱分趣的价格
     */
    @BindView(R.id.confirm_product_total_price)
    TextView confirm_product_total_price;

    /**
     * 官网价格
     */
    @BindView(R.id.confirm_product_discount)
    TextView confirm_product_discount;

    @BindView(R.id.confirm_product_config)
    TextView confirm_product_config;

    /**
     * 没有算优惠券之前的价格
     */
    @BindView(R.id.tv_product_calculate_price)
    TextView tv_product_calculate_price;

    @BindView(R.id.tv_address_parent)
    ViewGroup tv_address_parent;

    @BindView(R.id.tv_new_address_parent)
    ViewGroup tv_new_address_parent;

    @BindView(R.id.tv_address_title)
    TextView tv_address_title;

    @BindView(R.id.tv_address_subtitle)
    TextView tv_address_subtitle;

    @BindView(R.id.tv_pay_price)
    TextView tv_pay_price;

    @BindView(R.id.each_term)
    TextView each_term;

    @BindView(R.id.tv_discount_amount)
    TextView tv_discount_amount;

    /**
     * 减去优惠券以后的价格
     */
    @BindView(R.id.tv_final_price)
    TextView tv_final_price;

    private static final int REQUEST_CODE_ADDRESS = 100;
    private static final int REQUEST_CODE_RECEIPT = 102;
    private ReceiptBusiness business;
    private AddressBusiness addressBusiness;
    private ConfirmBusinessModel confirmBusinessModel;
    private String[] couponTags;

    public static void start(Context context, ConfirmBusinessModel colorModel) {
        if (context == null) return;
        Intent intent = new Intent(context, ProductConfirmationActivity.class);
        intent.putExtra(KEY_BUNDLE, colorModel);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (!LoginUtil.checkLoginStatus()) {
            LoginActivity.start(this);
        }

        setContentView(R.layout.activity_confirmation);
        ButterKnife.bind(this);
        comm_title.setTitle(R.string.product_confirmation_title);

        initData();
    }

    @Override
    protected void onResume() {
        super.onResume();
        httpGetProductCouponTag();
    }

    private void httpGetProductCouponTag() {
        if (confirmBusinessModel == null)return;
        HttpRequest.getProductCouponInfo(confirmBusinessModel.getProductId(), HttpConstant.URL_PRODUCT_COUNPON_INDEX,this);
    }

    private void initData() {
        confirmBusinessModel = (ConfirmBusinessModel) getIntent().getSerializableExtra(KEY_BUNDLE);
        if (confirmBusinessModel == null) return;

        httpGetProductCouponTag();

        confirm_product_name.setText(confirmBusinessModel.getProductName());
        if (confirmBusinessModel.getColorModel() != null && confirmBusinessModel.getStyleModel() != null) {
            String colorStr = confirmBusinessModel.getColorModel().getName();
            String styleStr = confirmBusinessModel.getStyleModel().getName();
            confirm_product_config.setText(String.format(getResources().getString(R.string.confirmation_str), colorStr, styleStr));
        }

        confirm_product_total_price.setText(StringUtil.getPrice(confirmBusinessModel.getTotalPrice(), StringUtil.PRICE_FORMAT_PREFIX));
        confirm_product_discount.setText(StringUtil.getPrice(confirmBusinessModel.getTotalPrice(), StringUtil.PRICE_FORMAT_PREFIX));
        confirm_product_discount.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        //中间横线textview.getPaint()
        confirm_product_discount.getPaint().setAntiAlias(true);
        tv_product_calculate_price.setText(StringUtil.getPrice(confirmBusinessModel.getTotalPrice(),StringUtil.PRICE_FORMAT_PREFIX));
        tv_final_price.setText(StringUtil.getPrice(confirmBusinessModel.getTotalPrice(), StringUtil.PRICE_FORMAT_PREFIX));
        tv_pay_price.setText(StringUtil.getPrice(confirmBusinessModel.getTotalPrice(), StringUtil.PRICE_FORMAT_PREFIX));
        each_term.setText(StringUtil.getPrice(Double.parseDouble(confirmBusinessModel.getTotalPrice()) / 12,StringUtil.PRICE_FORMAT_PREFIX));

        Gson gson = new Gson();
        try{
            addressBusiness = gson.fromJson(CacheUtils.getInstance().getString(CacheConstant.KEY_CACHE_ADDRESS),AddressBusiness.class);
            if (addressBusiness !=null){
                tv_address_title.setText(addressBusiness.getName() + " " + addressBusiness.getPhone());
                tv_address_subtitle.setText(addressBusiness.getProvince().getName() + " " + addressBusiness.getCity().getName() + addressBusiness.getArea().getName() + addressBusiness.getSpecificAddress());
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    private void httpPostOrder() {
        if (!NetworkUtil.checkoutInternet()) {
            return;
        }
//        getIntent().getBundleExtra()
        HttpRequest.makeOrder(confirmBusinessModel.getTotalPrice(),confirmBusinessModel.getTotalPrice(),"","",confirmBusinessModel.getProductId(),confirmBusinessModel.getGoodsId(),
                addressBusiness.getAddressCode(),HttpConstant.URL_MALL_ORDER_INDEX,this);

    }

    @OnClick({R.id.tv_new_address_parent, R.id.tv_address_parent})
    public void newAddress(View view) {
        AddressModificationActivity.start(this,addressBusiness, REQUEST_CODE_ADDRESS);
    }

    @OnClick(R.id.tv_buy_now)
    public void buyNow(View view) {
        String title = tv_address_title.getText().toString();
        String subTitle = tv_address_subtitle.getText().toString();
        if (TextUtils.isEmpty(title) || TextUtils.isEmpty(subTitle)) {
            showAddressDialog();
            return;
        }

        httpPostOrder();
    }

    private void showAddressDialog() {
        new AlertDialog(this, "请先完善收货地址", "", "去设置", "取消", 0, new AlertDialog.OnDialogButtonClickListener() {

            @Override
            public void onDialogButtonClick(int requestCode, boolean isPositive) {
                if (isPositive) {
                    AddressModificationActivity.start(ProductConfirmationActivity.this, REQUEST_CODE_ADDRESS);
                }
            }
        }).show();
    }

    private void updateContent(boolean hasAddress) {
        tv_address_parent.setVisibility(hasAddress ? View.VISIBLE : View.GONE);
        tv_new_address_parent.setVisibility(hasAddress ? View.GONE : View.VISIBLE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) return;
        if (requestCode == REQUEST_CODE_ADDRESS) {
            if (data == null) return;
            addressBusiness = (AddressBusiness) data.getSerializableExtra("zhunafeng");
            if (addressBusiness == null) return;

            Gson gson = new Gson();
            LogUtils.d("JSON -"+gson.toJson(addressBusiness));
            //保存地址
            CacheUtils.getInstance().put(CacheConstant.KEY_CACHE_ADDRESS,gson.toJson(addressBusiness));

            updateContent(true);
            tv_address_title.setText(addressBusiness.getName() + " " + addressBusiness.getPhone());
            tv_address_subtitle.setText(addressBusiness.getProvince().getName() + " " + addressBusiness.getCity().getName() + addressBusiness.getArea().getName() + addressBusiness.getSpecificAddress());
        } else if (requestCode == REQUEST_CODE_RECEIPT) {
            if (data == null) return;
            business = (ReceiptBusiness) data.getSerializableExtra(ReceiptActivity.KEY_RECEIPTT);
            if (business == null) return;
            if (business.isIndividual()) {
                //个人
                iv_receipt_type.setText("个人");
            } else {
                //公司
                iv_receipt_type.setText(business.getCompanyTitle());
            }
        }else if (requestCode == REQUEST_CODE_COUPONS){
//            tv_discount_amount.setText();
        }
    }

    @OnClick(R.id.rl_receipt_parent)
    public void onClickReceipt(View view) {
        ReceiptActivity.start(this, REQUEST_CODE_RECEIPT);
    }

    @OnClick(R.id.rr_confirmation_coupon)
    public void onClickConfirmationCoupon(View view) {
        if (couponTags != null){
            ConfirmationCouponActivity.start(this,couponTags,REQUEST_CODE_COUPONS);
        }else {
            ToastUtils.showShort("目前没有可使用的优惠券");
        }
    }

    @Override
    public void onHttpResponse(int requestCode, String resultJson, Exception e) {
        if (requestCode == HttpConstant.URL_MALL_ORDER_INDEX) {
            if (TextUtils.isEmpty(resultJson)) {
                showError("请求失败");
                return;
            }
            Gson gson = new Gson();
            OrderResponse response = gson.fromJson(resultJson, OrderResponse.class);
            if (!response.checkDataValidate()) {
                showError(response.getMessage());
                return;
            }

            WebViewActivity.start(this, "支付", response.getData());
        }else if (requestCode == HttpConstant.URL_PRODUCT_COUNPON_INDEX){
            if (TextUtils.isEmpty(resultJson))return;
            Gson gson = new Gson();
            ProductCouponTagResponse response = gson.fromJson(resultJson,ProductCouponTagResponse.class);
            if (response == null)return;
            couponTags = response.getData();
        }
    }

    private void showError(String message) {
        ToastUtils.showShort(message);
    }
}
