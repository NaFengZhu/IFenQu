package com.ifenqu.app.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.TextView;

import com.blankj.utilcode.util.CacheUtils;
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
import com.ifenqu.app.util.CacheConstant;
import com.ifenqu.app.util.LoginUtil;
import com.ifenqu.app.util.NetworkUtil;
import com.ifenqu.app.util.StringUtil;
import com.ifenqu.app.view.BaseActivity;
import com.ifenqu.app.widget.BottomMenuWindow;
import com.ifenqu.app.widget.CommonTitleView;
import com.ifenqu.app.widget.IFenQuWebView;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

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

    @BindView(R.id.banner_webView)
    IFenQuWebView banner_webView;

    @BindView(R.id.tv_terms_price)
    TextView tv_terms_price;

    @BindView(R.id.each_term)
    TextView each_term;

    @BindView(R.id.ll_scroll_title)
    TabLayout ll_scroll_title;

    @BindView(R.id.ifenqu_webView1)
    IFenQuWebView ifenqu_webView1;

    @BindView(R.id.ifenqu_webView2)
    IFenQuWebView ifenqu_webView2;

    @BindView(R.id.ifenqu_webView3)
    IFenQuWebView ifenqu_webView3;

    private static final int REQUEST_CODE_GOODS = 10;

    private String productId;
    private List<GoodDetailModel> colorList = new ArrayList<>();
    private List<GoodDetailModel> styleList = new ArrayList<>();
    private GoodModel goodModel;
    private Map<String, String> typeMap;
    private int productPrice; //商品价格值
    private GoodDetailModel colorModel;
    private GoodDetailModel styleModel;
    private String productName;
    private List<Integer> priceList = new ArrayList<>();
    private Set<Integer> priceRang = new HashSet<>();

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

    private void initView() {
        banner_webView.setWebViewListener(this);

        ll_scroll_title.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 0:
                        ifenqu_webView1.setVisibility(View.VISIBLE);
                        ifenqu_webView2.setVisibility(View.GONE);
                        ifenqu_webView3.setVisibility(View.GONE);
                        break;
                    case 1:
                        ifenqu_webView1.setVisibility(View.GONE);
                        ifenqu_webView2.setVisibility(View.VISIBLE);
                        ifenqu_webView3.setVisibility(View.GONE);
                        break;
                    case 2:
                        ifenqu_webView1.setVisibility(View.GONE);
                        ifenqu_webView2.setVisibility(View.GONE);
                        ifenqu_webView3.setVisibility(View.VISIBLE);
                        break;
                    default:
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        goodModel = (GoodModel) CacheUtils.getInstance().getSerializable(CacheConstant.KEY_PRODUCT_PROFILE + productId);
        if (goodModel != null) {
            colorModel = goodModel.getCurrentColorItem();
            styleModel = goodModel.getCurrentStyleItem();

            updateGoodsModel();
        } else {
            goodModel = new GoodModel();
        }
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

            ProductDetailModel productModel = response.getData();
            if (productModel == null) return;
            if (productModel.getProductStatus() == 3) {//3代表已过期
                showExpiredProductDialog();
            }
            productName = productModel.getProductName();
            tv_product_name.setText(productModel.getProductName());
            comm_title.setTitle(productModel.getProductName());

            setProductTypeData(productModel.getGoodsList());
            tv_terms_price.setText(generatedPriceInfo());
            generatedData(productModel.getGoodsList());

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
                        banner_webView.loadHTML(model.getContent());
                        goodModel.setWebView_content(model.getContent());
                        break;
                    case "DETAILS":
                        ifenqu_webView1.loadHTML(model.getContent());
                        break;
                    case "BUY_EXPLAIN":
                        ifenqu_webView2.loadHTML(model.getContent());
                        break;
                    case "QA":
                        ifenqu_webView3.loadHTML(model.getContent());
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
        priceRang.add(data.getAmount());
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

    private void setProductTypeData(List<ProductDetailGoodsModel> goodsList) {
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
    }

    private String generatedPriceInfo() {
        if (priceList.size() == 0) return "";
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
            return decimalFormat.format(result);
        }
        return "";
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
    private void generatedData(List<ProductDetailGoodsModel> goodsList) {
        if (goodsList == null) return;

        if (goodsList.size() == 1) {
            goodModel.setOnlyOnePrice(true);
        } else {
            goodModel.setOnlyOnePrice(false);
        }

        goodModel.setGoodsList(goodsList);
        goodModel.setProductId(productId);
        goodModel.setColorTitle("颜色");
        goodModel.setStyleTitle("版本");

        if (priceRang != null) {
            if (priceRang.size() == 1) {
                goodModel.setOnlyOnePrice(true);
                goodModel.setLowPrice(priceList.get(0));
            } else {
                goodModel.setOnlyOnePrice(false);
                if (priceList != null && priceList.size() > 0) {
                    goodModel.setLowPrice(priceList.get(0));
                    goodModel.setHighPrice(priceList.get(priceList.size() - 1));
                }
            }
        }

        goodModel.setTermsPrice(String.valueOf(generatedPriceInfo()));

        exchangeStatus(goodModel.getStyleList(), styleList);
        exchangeStatus(goodModel.getColorList(), colorList);

        goodModel.setStyleList(styleList);
        goodModel.setColorList(colorList);
    }

    private void exchangeStatus(List<GoodDetailModel> originalList, List<GoodDetailModel> newList) {
        if (originalList == null) return;
        if (newList == null) return;
        int num = newList.size();
        for (int i = 0; i < num; i++) {
            GoodDetailModel item = newList.get(i);
            if (item == null) continue;

            int subNum = originalList.size();

            int newGoodsId = item.getGoodsId();
            int newBaseId = item.getBaseTypeDictId();
            int newSubTypeId = item.getSubTypeDictId();

            for (int j = 0; j < subNum; j++) {
                GoodDetailModel subItem = originalList.get(j);
                if (subItem == null) continue;
                int originalGoodsId = subItem.getGoodsId();
                int originalBaseId = subItem.getBaseTypeDictId();
                int originalSubTypeId = subItem.getSubTypeDictId();


                if (originalBaseId == newBaseId && originalGoodsId == newGoodsId && originalSubTypeId == newSubTypeId) {
                    item.setSelected(subItem.isSelected());
                }

            }
        }
    }

    private void clearColorAndStyleList() {
        priceRang.clear();
        priceList.clear();
        styleList.clear();
        colorList.clear();
    }

    private List<String> nameSet = new ArrayList<>();


    /**
     * 从每个产品列表里删选出来 颜色、款式 类型
     *
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
        BottomMenuWindow.startForResult(this, goodModel, REQUEST_CODE_GOODS);
    }

    @OnClick(R.id.tv_buy_now)
    public void buyNow(View view) {
        if (colorModel == null || styleModel == null) {
            chooseGoods();
            return;
        }

        if (!LoginUtil.checkLoginStatus()) {
            LoginActivity.start(this);
            return;
        }

        ConfirmBusinessModel confirmBusinessModel = new ConfirmBusinessModel();
        confirmBusinessModel.setColorModel(colorModel);
        confirmBusinessModel.setStyleModel(styleModel);
        confirmBusinessModel.setProductId(productId);
        confirmBusinessModel.setProductName(productName);
        ProductDetailGoodsModel model = calculateCurrentPrice(colorModel, styleModel);
        if (model != null) {
            confirmBusinessModel.setGoodsId(model.getGoodsId() + "");
            confirmBusinessModel.setTotalPrice(String.valueOf(model.getAmount()));
        }

        executeBuy(confirmBusinessModel);
    }

    private void executeBuy(ConfirmBusinessModel colorModel) {
        ProductConfirmationActivity.start(this, colorModel);
    }


    @OnClick(R.id.ll_choose_goods)
    public void onClickChooseGoods(View view) {
        chooseGoods();
    }

    private void clearSelectedStatus(List<GoodDetailModel> list) {
        if (list != null) {
            int num = list.size();
            for (int i = 0; i < num; i++) {
                GoodDetailModel item = list.get(i);
                if (item == null) continue;
                item.setSelected(false);
            }
        }
    }

    private void selectedData(List<GoodDetailModel> tagItem, GoodDetailModel item) {
        if (tagItem == null) return;
        if (item == null) return;
        int num = tagItem.size();
        for (int i = 0; i < num; i++) {
            GoodDetailModel goodDetailModel = tagItem.get(i);
            if (goodDetailModel == null) continue;
            if (goodDetailModel.getGoodsId() == item.getGoodsId() && goodDetailModel.getSubTypeDictId() == item.getSubTypeDictId()) {
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

            colorModel = (GoodDetailModel) data.getSerializableExtra(ProductDetailActivity.BUNDLE_KEY_COLOR);
            styleModel = (GoodDetailModel) data.getSerializableExtra(ProductDetailActivity.BUNDLE_KEY_STYLE);

            updateGoodsModel();

            //保存当前用户选择的内容
            CacheUtils.getInstance().put(CacheConstant.KEY_PRODUCT_PROFILE + productId, goodModel);
        }
    }

    //更新goodModel
    private void updateGoodsModel() {
        if (goodModel != null) {
            goodModel.setCurrentColorItem(colorModel);
            goodModel.setCurrentStyleItem(styleModel);
        }

        //判断是展示默认的"请选择商品款型" 还是展示其中的一个属性（颜色/款式）
        showTypeContent(colorModel == null && styleModel == null ? false : true);

        //设置goodModel里的所有的 GoodDetailModel 未被选中
        if (goodModel != null) {
            clearSelectedStatus(goodModel.getColorList());
            clearSelectedStatus(goodModel.getStyleList());
        }

        // --- start 处理不同条件，颜色/版本，选中的的内容 -----
        StringBuilder configStr = new StringBuilder();
        if (colorModel != null) {
            configStr.append(colorModel.getName()).append(" ");
            selectedData(goodModel.getColorList(), colorModel);
        }

        if (styleModel != null) {
            configStr.append(styleModel.getName());
            selectedData(goodModel.getStyleList(), styleModel);
        }

        // --- end -----

        tv_type_name.setText(configStr.toString());
    }

    /**
     * 这快可能有坑，goodDetailMode没有是不依赖具体的某个产品的，
     * 所以要循环所有的产品就行匹配，看是否有某哥产品都包含colorModel&styleModel
     *
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
            } else if (model.getSubTypeDictId() == styleType) {
                hasType = true;
                model.setSelected(true);
            } else {
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
    }

    @Override
    public void onResume() {
        banner_webView.onResume();
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
