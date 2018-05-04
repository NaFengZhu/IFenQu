package com.ifenqu.app.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;
import com.emilsjolander.components.StickyScrollViewItems.StickyScrollView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ifenqu.app.R;
import com.ifenqu.app.http.HttpConstant;
import com.ifenqu.app.http.HttpRequest;
import com.ifenqu.app.http.response.OnHttpResponseListener;
import com.ifenqu.app.http.response.ProductDetailRelativedResponse;
import com.ifenqu.app.http.response.ProductDetailResponse;
import com.ifenqu.app.model.ConfirmBusinessModel;
import com.ifenqu.app.model.GoodDetailModel;
import com.ifenqu.app.model.GoodModel;
import com.ifenqu.app.model.ProductDetailGoodsModel;
import com.ifenqu.app.model.ProductDetailModel;
import com.ifenqu.app.model.ProductDetailRelativedModel;
import com.ifenqu.app.util.LoginUtil;
import com.ifenqu.app.util.NetworkUtil;
import com.ifenqu.app.util.StringUtil;
import com.ifenqu.app.view.BaseActivity;
import com.ifenqu.app.view.adapter.ProductDetailPagerAdapter;
import com.ifenqu.app.widget.BottomMenuWindow;
import com.ifenqu.app.widget.CommonTitleView;
import com.ifenqu.app.widget.IFenQuWebView;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 产品详情页
 */
public class ProductDetailActivity extends BaseActivity implements OnHttpResponseListener, IFenQuWebView.IFenQuWebViewListener {

    private static final int TYPE_COLOR = 71;
    private static final int TYPE_STYLE = 72;

    public static final String BUNDLE_KEY_COLOR = "BUNDLE_KEY_COLOR";
    public static final String BUNDLE_KEY_STYLE = "BUNDLE_KEY_STYLE";

    private static final int TAG_TERMS = 12; //每个产品都是默认的分12期
    private static final String KEY_PRODUCT_ID = "KEY_PRODUCT_ID";

    @BindView(R.id.comm_title)
    CommonTitleView comm_title;

    @BindView(R.id.tv_product_name)
    TextView tv_product_name;

    @BindView(R.id.tv_type_name)
    TextView tv_type_name;

    @BindView(R.id.tv_dot_number)
    TextView tv_dot_number;

    @BindView(R.id.tv_type_name_holder)
    TextView tv_type_name_holder;

    @BindView(R.id.rr_type_parent)
    ViewGroup rr_type_parent;

    @BindView(R.id.scrollView)
    StickyScrollView scrollView;

    @BindView(R.id.materialViewPager)
    ViewPager materialViewPager;

    @BindView(R.id.tv_detail)
    TextView tv_detail;

    @BindView(R.id.tv_explain)
    TextView tv_explain;

    @BindView(R.id.tv_qv)
    TextView tv_qv;

    @BindView(R.id.tv_detail_line)
    View tv_detail_line;

    @BindView(R.id.tv_explain_line)
    View tv_explain_line;

    @BindView(R.id.tv_qv_line)
    View tv_qv_line;

    @BindView(R.id.banner_webView)
    IFenQuWebView banner_webView;

    @BindView(R.id.tv_terms_price)
    TextView tv_terms_price;

    @BindView(R.id.each_term)
    TextView each_term;

    @BindView(R.id.contentWebView)
    IFenQuWebView contentWebView;

    @BindView(R.id.pre_order_content)
    IFenQuWebView pre_order_content;

    @BindView(R.id.qa_content)
    IFenQuWebView qa_content;

    private static final int REQUEST_CODE_GOODS = 10;

    private String productId;
    private ProductDetailPagerAdapter adapter;
    private List<GoodDetailModel> colorList = new ArrayList<>();
    private List<GoodDetailModel> styleList = new ArrayList<>();
    private GoodModel goodModel;
    private Map<String, String> typeMap;
    private ProductDetailModel productModel;
    private ConfirmBusinessModel confirmBusinessModel;
    private int productPrice; //商品价格值
    private String termsPrice;
    private String productImageContent;
    private GoodDetailModel colorModel;
    private GoodDetailModel styleModel;

    public static void start(Context context, long productId) {
        if (context == null) return;
        Intent intent = new Intent(context, ProductDetailActivity.class);
        intent.putExtra(KEY_PRODUCT_ID, productId + "");
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);
        ButterKnife.bind(this);
        productId = getIntent().getStringExtra(KEY_PRODUCT_ID);
        initView();
        httpGetProductInfo();
        httpGetProductMoreInfo();

    }


    private void httpGetProductMoreInfo() {
        if (!NetworkUtil.checkoutInternet()) return;
        HttpRequest.getProductRelativedContent(productId, HttpConstant.URL_PRODUCT_REALATIVED_CONTENT_INDEX, this);
    }

    private void updateCurrentFragment(int index) {
//        materialViewPager.setCurrentItem(index, false);
        if (index == 0) {
            contentWebView.setVisibility(View.VISIBLE);
            pre_order_content.setVisibility(View.GONE);
            qa_content.setVisibility(View.GONE);
        } else if (index == 1) {
            contentWebView.setVisibility(View.GONE);
            pre_order_content.setVisibility(View.VISIBLE);
            qa_content.setVisibility(View.GONE);
        } else if (index == 2) {
            contentWebView.setVisibility(View.GONE);
            pre_order_content.setVisibility(View.GONE);
            qa_content.setVisibility(View.VISIBLE);
        }
    }

    private void initView() {
        adapter = new ProductDetailPagerAdapter(getSupportFragmentManager());
        materialViewPager.setAdapter(adapter);

        updateCurrentFragment(0);

        banner_webView.setWebViewListener(this);
    }

    private void httpGetProductInfo() {
        if (!NetworkUtil.checkoutInternet()) return;
        HttpRequest.getProductDetail(productId, HttpConstant.URL_PRODUCT_DETAIL_INDEX, this);
    }

    @Override
    public void onHttpResponse(int requestCode, String resultJson, Exception e) {
        if (TextUtils.isEmpty(resultJson)) return;
        Gson gson = new Gson();
        if (requestCode == HttpConstant.URL_PRODUCT_DETAIL_INDEX) {

            getTypeMap(resultJson);

            ProductDetailResponse response = gson.fromJson(resultJson, ProductDetailResponse.class);
            if (response == null || response.getData() == null)
                return;

            productModel = response.getData();
            if (productModel == null) return;
            if (productModel.getProductStatus() == 3) {//3代表已过期
                showExpiredProductDialog();
            }
            tv_product_name.setText(productModel.getProductName());
            comm_title.setTitle(productModel.getProductName());

            setProductTypeData();
        } else if (requestCode == HttpConstant.URL_PRODUCT_REALATIVED_CONTENT_INDEX) {
            ProductDetailRelativedResponse responses = gson.fromJson(resultJson, ProductDetailRelativedResponse.class);
            List<ProductDetailRelativedModel> list = responses.getData();
            if (list == null) return;
            for (int i = 0; i < list.size(); i++) {
                ProductDetailRelativedModel model = list.get(i);
                if (model == null) continue;
                String type = model.getContentLocationType();
                switch (type) {
                    case "BANNER":
                        productImageContent = model.getContent();
                        banner_webView.loadHTML(productImageContent);
                        break;
                    case "DETAILS":
                        contentWebView.loadHTML(model.getContent());
                        break;
                    case "BUY_EXPLAIN":
                        pre_order_content.loadHTML(model.getContent());
                        break;
                    case "QA":
                        pre_order_content.loadHTML(model.getContent());
                        break;
                }
            }
        }
    }

    //显示过期产品的弹框
    private void showExpiredProductDialog() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = View.inflate(this, R.layout.expired_dialog, null);
        view.findViewById(R.id.btnAlertDialogPositive).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder.create().dismiss();
                finish();
            }
        });
        builder.setView(view);
        builder.create().show();
    }

    private void handlePrice(ProductDetailGoodsModel data) {
        if (data == null) return;
        priceList.add(data.getAmount());
    }

    private void getTypeMap(String resultJson) {
        if (TextUtils.isEmpty(resultJson)) return;
        try {
            JSONObject jsonObject = new JSONObject(resultJson);
            JSONObject dataJsonObject = jsonObject.getJSONObject("data");
            if (dataJsonObject == null) return;
            JSONObject typemap = dataJsonObject.getJSONObject("typeMap");
            String typeMapStr = typemap.toString();
            handleTypeMap(typeMapStr);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private List<Integer> priceList = new ArrayList<>();

    private void setProductTypeData() {
        List<ProductDetailGoodsModel> goodsList = productModel.getGoodsList();
        if (goodsList == null || goodsList.size() == 0) {
            return;
        }

        /**
         * 目前只有两大类型
         * 71 代表的 颜色
         * 72 代表的 款式
         */
        clearColorAndStyleList();
        for (int i = 0; i < goodsList.size(); i++) {
            ProductDetailGoodsModel detailGoodsModel = goodsList.get(i);
            if (detailGoodsModel == null) continue;
            handleGoodDetailList(detailGoodsModel.getGoodsDictDetailModelList());
            handlePrice(detailGoodsModel);
        }
        generatedPriceInfo();
    }

    private void generatedPriceInfo() {
        if (priceList.size() == 0) return;
        Collections.sort(priceList);
        if (priceList.size() > 0) {
            productPrice = priceList.get(0);
            String[] resultStr = StringUtil.getWholeAndDobNum(productPrice);
            if (resultStr != null && resultStr.length >= 2) {
                each_term.setText(resultStr[0]);
                tv_dot_number.setText(String.format(getResources().getString(R.string.product_list_subtitle_2), resultStr[1]));
            }
            double result = (double) productPrice / TAG_TERMS;
            DecimalFormat decimalFormat = new DecimalFormat("0.00");
            termsPrice = decimalFormat.format(result);
            tv_terms_price.setText(termsPrice);
        }
    }

    private void handleTypeMap(String mapStr) {
        if (TextUtils.isEmpty(mapStr)) return;
        Gson gson = new Gson();
        Type type = new TypeToken<Map<String, String>>() {
        }.getType();
        typeMap = gson.fromJson(mapStr, type);
    }

    /**
     * GoodModel
     * 一个business model 主要是用于在产品详情-选择产品类型款式之间操作的对象
     */
    private void generatedData() {
        if (goodModel != null)return;
        if (productModel == null) return;
        List<ProductDetailGoodsModel> goodsList = productModel.getGoodsList();
        if (goodsList == null) return;
        if (styleList.size() == 0 && colorList.size() == 0) return;
        if (goodModel != null) return;
        goodModel = new GoodModel();
        if (goodsList.size() == 1) {
            goodModel.setOnlyOnePrice(true);
        } else {
            goodModel.setOnlyOnePrice(false);
        }
        goodModel.setGoodsList(goodsList);
        goodModel.setProductId(productId);
        goodModel.setColorTitle("颜色");
        goodModel.setStyleTitle("版本");
        //productImageContent 是不同接口返回来的，所以选择在每次选择的时候，生成一下这个对象 GoodModel
        goodModel.setWebView_content(productImageContent);
        if (priceList != null && priceList.size() > 0){
            goodModel.setLowPrice(priceList.get(0));
            goodModel.setHighPrice(priceList.get(priceList.size() - 1));
        }
        goodModel.setTermsPrice(String.valueOf(termsPrice));
        goodModel.setStyleList(styleList);
        goodModel.setColorList(colorList);
    }

    private void clearColorAndStyleList() {
        priceList.clear();
        styleList.clear();
        colorList.clear();
    }

    private List<String> nameSet = new ArrayList<>();


    /**
     * 从每个产品列表里删选出来 颜色、款式 类型
     * @param goodsDictDetailModelList
     */
    private void handleGoodDetailList(List<GoodDetailModel> goodsDictDetailModelList) {
        if (goodsDictDetailModelList == null || goodsDictDetailModelList.size() == 0) return;
        for (int i = 0; i < goodsDictDetailModelList.size(); i++) {
            GoodDetailModel detailModel = goodsDictDetailModelList.get(i);
            if (detailModel == null) continue;
            String subTypeStr = typeMap.get(String.valueOf(detailModel.getSubTypeDictId()));
            if (!TextUtils.isEmpty(subTypeStr)) {
                detailModel.setName(subTypeStr);
            }
            if (nameSet.contains(subTypeStr)) continue;
            nameSet.add(subTypeStr);
            int baseId = detailModel.getBaseTypeDictId();
            if (baseId == TYPE_COLOR) {
                colorList.add(detailModel);
            } else if (baseId == TYPE_STYLE) {
                styleList.add(detailModel);
            }
        }

    }

    private void chooseGoods() {
        generatedData();
        BottomMenuWindow.startForResult(this, goodModel, REQUEST_CODE_GOODS);
    }

    @OnClick(R.id.tv_buy_now)
    public void buyNow(View view) {
        if (colorModel == null) {
            ToastUtils.showShort("请选择颜色");
            return;
        }

        if (styleModel == null) {
            ToastUtils.showShort("请选择版本配置");
            return;
        }

        if (confirmBusinessModel == null) {
            chooseGoods();
            return;
        }
        if (!LoginUtil.checkLoginStatus()) {
            LoginActivity.start(view.getContext());
            return;
        }
        executeBuy(confirmBusinessModel);
    }

    private void executeBuy(ConfirmBusinessModel colorModel) {
        ProductConfirmationActivity.start(this, colorModel);
    }

    @OnClick({R.id.tv_qv_parent, R.id.tv_detail_parent, R.id.tv_explain_parent})
    public void onClickTabs(View view) {
        switch (view.getId()) {
            case R.id.tv_qv_parent:
                tv_explain.setTextColor(getResources().getColor(R.color.text_title_color));
                tv_qv.setTextColor(getResources().getColor(R.color.tab_bg));
                tv_detail.setTextColor(getResources().getColor(R.color.text_title_color));

                tv_qv_line.setVisibility(View.VISIBLE);
                tv_explain_line.setVisibility(View.INVISIBLE);
                tv_detail_line.setVisibility(View.INVISIBLE);

                updateCurrentFragment(2);
                break;
            case R.id.tv_detail_parent:
                tv_explain.setTextColor(getResources().getColor(R.color.text_title_color));
                tv_qv.setTextColor(getResources().getColor(R.color.text_title_color));
                tv_detail.setTextColor(getResources().getColor(R.color.tab_bg));

                tv_qv_line.setVisibility(View.INVISIBLE);
                tv_explain_line.setVisibility(View.INVISIBLE);
                tv_detail_line.setVisibility(View.VISIBLE);

                updateCurrentFragment(0);
                break;
            case R.id.tv_explain_parent:
                tv_explain.setTextColor(getResources().getColor(R.color.tab_bg));
                tv_qv.setTextColor(getResources().getColor(R.color.text_title_color));
                tv_detail.setTextColor(getResources().getColor(R.color.text_title_color));

                tv_qv_line.setVisibility(View.INVISIBLE);
                tv_explain_line.setVisibility(View.VISIBLE);
                tv_detail_line.setVisibility(View.INVISIBLE);

                updateCurrentFragment(1);
                break;
            default:
        }

    }

    @OnClick(R.id.ll_choose_goods)
    public void onClickChooseGoods(View view) {
        chooseGoods();
    }

    private void clearColorSelected(){
        if (goodModel == null)return;
        List<GoodDetailModel> list = goodModel.getColorList();
        if (list != null){
            int num = list.size();
            for (int i = 0; i < num; i++) {
                GoodDetailModel item = list.get(i);
                if (item == null)continue;
                item.setSelected(false);
            }
        }
    }

    private void clearStyleSelected(){
        if (goodModel == null)return;
        List<GoodDetailModel> styleList = goodModel.getStyleList();
        if (styleList != null){
            int num = styleList.size();
            for (int i = 0; i <num ; i++) {
                GoodDetailModel item = styleList.get(i);
                if (item == null)continue;
                item.setSelected(false);
            }
        }
    }

    private void selectedData(List<GoodDetailModel> tagItem, GoodDetailModel item){
        if (tagItem == null)return;
        if (item == null)return;
        int num = tagItem.size();
        for (int i = 0; i < num ; i++) {
            GoodDetailModel goodDetailModel = tagItem.get(i);
            if (goodDetailModel == null)continue;
            if (goodDetailModel.getGoodsId() == item.getGoodsId() && goodDetailModel.getSubTypeDictId() == item.getSubTypeDictId()){
                goodDetailModel.setSelected(true);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) return;
        if (requestCode == REQUEST_CODE_GOODS) {
            if (data == null) return;
            if (productModel == null) return;

            colorModel = (GoodDetailModel) data.getSerializableExtra(ProductDetailActivity.BUNDLE_KEY_COLOR);
            styleModel = (GoodDetailModel) data.getSerializableExtra(ProductDetailActivity.BUNDLE_KEY_STYLE);

             showTypeContent(true);

            clearColorSelected();
            clearStyleSelected();

            if (colorModel == null && styleModel == null){
                showTypeContent(false);
                return;
            }
            if (colorModel == null ){
                if (styleModel != null){
                    tv_type_name.setText(styleModel.getName());
                    selectedData(goodModel.getStyleList(),styleModel);
                    goodModel.setCurrentStyleItem(styleModel);
                    goodModel.setCurrentColorItem(null);
                }
                return;
            }

            if (styleModel == null) {
                if (colorModel != null){
                    tv_type_name.setText(colorModel.getName());
                    selectedData(goodModel.getColorList(),colorModel);
                    goodModel.setCurrentStyleItem(null);
                    goodModel.setCurrentColorItem(colorModel);
                }
                return;
            }

            tv_type_name.setText(colorModel.getName() + "  " + styleModel.getName());

            confirmBusinessModel = new ConfirmBusinessModel();
            confirmBusinessModel.setColorModel(colorModel);
            confirmBusinessModel.setProductId(productId);
            confirmBusinessModel.setStyleModel(styleModel);
            confirmBusinessModel.setProductName(productModel.getProductName());
            ProductDetailGoodsModel model = calculateCurrentPrice(colorModel, styleModel);
            if (model != null){
                confirmBusinessModel.setGoodsId(model.getGoodsId() + "");
                confirmBusinessModel.setTotalPrice(String.valueOf(model.getAmount()));
            }

        }
    }

    /**
     * 这快可能有坑，goodDetailMode没有是不依赖具体的某个产品的，
     * 所以要循环所有的产品就行匹配，看是否有某哥产品都包含colorModel&styleModel
     * @param colorModel
     * @param styleModel
     * @return
     */
    private ProductDetailGoodsModel calculateCurrentPrice(GoodDetailModel colorModel, GoodDetailModel styleModel) {
        if (colorModel != null && styleModel != null) {
            List<ProductDetailGoodsModel> goodsModelList = goodModel.getGoodsList();
            if (goodsModelList == null || goodsModelList.size() == 0) return null;
            int goodsSize = goodsModelList.size();
            for (int i = 0; i < goodsSize; i++) {
                ProductDetailGoodsModel item = goodsModelList.get(i);
                if (item == null) continue;
                boolean isHas = calculateCurrentType(item.getGoodsDictDetailModelList(), colorModel.getSubTypeDictId(), styleModel.getSubTypeDictId());
                if (isHas) {
                    return item;
                }
            }

        }
        return null;
    }

    private boolean calculateCurrentType(List<GoodDetailModel> item, int colorType, int styleType) {
        if (item == null) return false;
        int num = item.size();
        boolean hasColor = false, hasType = false;
        for (int i = 0; i < num; i++) {
            GoodDetailModel model = item.get(i);
            if (model == null) continue;
            if (model.getSubTypeDictId() == colorType) {
                hasColor = true;
                model.setSelected(true);
                goodModel.setCurrentColorItem(model);
            }else if (model.getSubTypeDictId() == styleType){
                hasType = true;
                model.setSelected(true);
                goodModel.setCurrentStyleItem(model);
            }else {
                model.setSelected(false);
            }

        }
        return hasColor && hasType;
    }

    private void showTypeContent(boolean isShow) {
        if (isShow) {
            rr_type_parent.setVisibility(View.VISIBLE);
            tv_type_name_holder.setVisibility(View.GONE);
        } else {
            rr_type_parent.setVisibility(View.GONE);
            tv_type_name_holder.setVisibility(View.VISIBLE);
        }

    }


    @Override
    public void onPause() {
        super.onPause();
        banner_webView.onPause();
        contentWebView.onPause();
        pre_order_content.onPause();
        qa_content.onPause();
    }

    @Override
    public void onResume() {
        banner_webView.onResume();
        contentWebView.onResume();
        qa_content.onResume();
        pre_order_content.onResume();
        super.onResume();
    }

    @Override
    public void onReceivedTitle(String str) {

    }

    @Override
    public void onPageLoadFinished(WebView view, String url) {
        banner_webView.setVisibility(View.VISIBLE);
    }
}
