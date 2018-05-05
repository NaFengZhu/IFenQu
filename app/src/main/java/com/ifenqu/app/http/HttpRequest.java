package com.ifenqu.app.http;

import com.ifenqu.app.http.response.OnHttpResponseListener;
import com.ifenqu.app.util.StringUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * HTTP请求工具类
 *
 * @use 添加请求方法xxxMethod >> HttpRequest.xxxMethod(...)
 * @must 所有请求的url、请求方法(GET, POST等)、请求参数(key-value方式，必要key一定要加，没提供的key不要加，value要符合指定范围)
 * 都要符合后端给的接口文档
 */
public class HttpRequest {
    //	private static final String TAG = "HttpRequest";


    /**
     * 添加请求参数，value为空时不添加
     *
     * @param list
     * @param key
     * @param value
     */
    public static void addExistParameter(List<Parameter> list, String key, Object value) {
        if (list == null) {
            list = new ArrayList<Parameter>();
        }
        if (StringUtil.isNotEmpty(key, true) && StringUtil.isNotEmpty(value, true)) {
            list.add(new Parameter(key, value));
        }
    }


    /**
     * 基础URL，这里服务器设置可切换
     */
    public static final String PAGE_NUM = "pageNum";


    //示例代码<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<


    //user<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

    public static final String RANGE = "range";

    public static final String ID = "id";
    public static final String USER_ID = "userId";
    public static final String CURRENT_USER_ID = "currentUserId";

    public static final String NAME = "name";
    public static final String PHONE = "phone";
    public static final String PASSWORD = "password";
    public static final String AUTH_CODE = "authCode";

    public static final String SEX = "sex";
    public static final int SEX_MAIL = 1;
    public static final int SEX_FEMAIL = 2;
    public static final int SEX_ALL = 3;


    /**
     * 翻译，根据有道翻译API文档请求
     * http://fanyi.youdao.com/openapi?path=data-mode
     * <br > 本Demo中只有这个是真正可用，其它需要自己根据接口文档新增或修改
     *
     * @param word
     * @param requestCode
     * @param listener
     */
    public static void translate(String word, final int requestCode, final OnHttpResponseListener listener) {
        List<Parameter> paramList = new ArrayList<Parameter>();
        addExistParameter(paramList, "q", word);
        addExistParameter(paramList, "keyfrom", "ZBLibrary");
        addExistParameter(paramList, "key", 1430082675);
        addExistParameter(paramList, "type", "data");
        addExistParameter(paramList, "doctype", "json");
        addExistParameter(paramList, "version", "1.1");

        HttpManager.getInstance().get(paramList, "http://fanyi.youdao.com/openapi.do", requestCode, listener);
    }


    //account>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


    /**
     * 发送验证码
     *
     * @param mobike
     * @param requestCode
     * @param listener
     */
    public static void sendVerificationCode(String mobike, final int requestCode, Map<String, String> head, final OnHttpResponseListener listener) {
//        List<Parameter> paramList = new ArrayList<Parameter>();
//        addExistParameter(paramList, "mobike", mobike);
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("mobile", mobike);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        HttpManager.getInstance().postStr(jsonObject.toString(), HttpConstant.URL_VERTIFICATION_CODE, requestCode, head, listener);
    }

    /**
     * 获取用户
     *
     * @param accessToken
     * @param requestCode
     * @param listener
     */
    public static void getUser(final String accessToken,
                               final int requestCode, final OnHttpResponseListener listener) {
        List<Parameter> paramList = new ArrayList<Parameter>();
        addExistParameter(paramList, "ifq_access_token", accessToken);

        HttpManager.getInstance().get(paramList, HttpConstant.URL_USER_INFO, requestCode, listener);
    }

    /**
     * 获取启动页配置信息
     *
     * @param requestCode
     * @param listener
     */
    public static void getSplashInfo(final int requestCode, final OnHttpResponseListener listener) {
        List<Parameter> paramList = new ArrayList<Parameter>();
        HttpManager.getInstance().get(paramList, HttpConstant.URL_SPLASH, requestCode, listener);
    }

    public static final int RESULT_GET_USER_SUCCEED = 100;

    public static final int USER_LIST_RANGE_ALL = 0;
    public static final int USER_LIST_RANGE_RECOMMEND = 1;

    /**
     * 手机号+验证码登录
     *
     * @param phone
     * @param verifyCode
     * @param requestCode
     * @param listener
     */
    public static void loginWithPhone(String phone, String verifyCode, final int requestCode, final OnHttpResponseListener listener) {
        List<Parameter> paramList = new ArrayList<Parameter>();
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("account", phone);
            jsonObject.put("source", "android");
            jsonObject.put("verifyCode", verifyCode);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        HttpManager.getInstance().postStr(jsonObject.toString(), HttpConstant.URL_SIGNIN_PHONE, requestCode, null, listener);
    }


    /**
     * 退出登录
     *
     * @param accessToken
     * @param requestCode
     * @param listener
     */
    public static void logout(String accessToken, final int requestCode, final OnHttpResponseListener listener) {
        Map<String, String> head = new HashMap<>();
        head.put("ifq_access_token", accessToken);

        HttpManager.getInstance().postStr("", HttpConstant.URL_SIGNOUT, requestCode, head, listener);
    }

    /**
     * 获取卡圈列表
     *
     * @param accessToken
     * @param requestCode
     * @param listener
     */
    public static void requestCouponList(String accessToken, final int requestCode, final OnHttpResponseListener listener) {
        List<Parameter> paramList = new ArrayList<Parameter>();

        HttpManager.getInstance().get(paramList, HttpConstant.URL_COUPON, requestCode, listener);
    }

    /**
     * GEE验证api1请求
     *
     * @param requestCode
     * @param listener
     */
    public static void geeApi1(final int requestCode, final OnHttpResponseListener listener) {
        List<Parameter> paramList = new ArrayList<Parameter>();

        HttpManager.getInstance().get(paramList, HttpConstant.URL_GEE_CAPTURE, requestCode, listener);
    }


    /**
     * GEE验证api2请求
     *
     * @param requestCode
     * @param listener
     */
    public static void geeApi2(final int requestCode, Map<String, String> header, final OnHttpResponseListener listener) {
        List<Parameter> paramList = new ArrayList<Parameter>();

        HttpManager.getInstance().post(paramList, HttpConstant.URL_GEE_VALUATE, requestCode, header, listener);
    }

    /**
     * 获取产品详情
     *
     * @param productId
     * @param requestCode
     * @param listener
     */
    public static void getProductDetail(String productId, final int requestCode, final OnHttpResponseListener listener) {
        String url = HttpConstant.URL_PRODUCT_DETAIL + productId;
        HttpManager.getInstance().get(null, url, requestCode, listener);
    }

    /**
     * 更换手机号码
     *
     * @param requestCode
     * @param listener
     */
    public static void changePhoneNumber(final int requestCode, Map<String, String> header, final OnHttpResponseListener listener) {
        List<Parameter> paramList = new ArrayList<Parameter>();

        HttpManager.getInstance().post(paramList, HttpConstant.URL_GEE_VALUATE, requestCode, header, listener);
    }

    /**
     * 获取订单列表
     * Get
     *
     * @param requestCode
     * @param listener
     */
    public static void getOrderList(int offset, int limit, final int requestCode, Map<String, String> header, final OnHttpResponseListener listener) {
        String url = HttpConstant.URL_ORDER_LIST;
        HttpManager.getInstance().get(null, url, requestCode, listener);
    }

    /**
     * 获取产品详情相关内容
     *
     * @param productId
     * @param requestCode
     * @param listener
     */
    public static void getProductRelativedContent(String productId, final int requestCode, final OnHttpResponseListener listener) {
        String url = HttpConstant.URL_PRODUCT_REALATIVED_CONTENT + productId + HttpConstant.URL_PRODUCT_REALATIVED_CONTENT_SUFF;
        HttpManager.getInstance().get(null, url, requestCode, listener);
    }

    /**
     * 获取产品列表
     *
     * @param requestCode
     * @param listener
     */
    public static void getProductList(final int requestCode, final OnHttpResponseListener listener) {
        HttpManager.getInstance().get(null, HttpConstant.URL_PRODUCT_LIST, requestCode, listener);
    }

    /**
     * 获取产品列表
     *
     * @param requestCode
     * @param listener
     */
    public static void getShopProductList(final int requestCode, final OnHttpResponseListener listener) {
        String url = HttpConstant.URL_PRODUCT_LIST + "/selection";
        HttpManager.getInstance().get(null, url, requestCode, listener);
    }

    /**
     * 推荐产品列表
     *
     * @param requestCode
     * @param listener
     */
    public static void getReconmendProductList(final int requestCode, final OnHttpResponseListener listener) {
        String url = HttpConstant.URL_PRODUCT_LIST + "/recommend";
        HttpManager.getInstance().get(null, url, requestCode, listener);
    }


    /**
     * 地址保存接口
     *
     * @param requestCode
     * @param listener
     */
    public static void saveAddress(String provinceName, String cityName, String areaName, String address, String posterName, String mobile, int requestCode, final OnHttpResponseListener listener) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("provinceName", provinceName);
            jsonObject.put("cityName", cityName);
            jsonObject.put("areaName", areaName);
            jsonObject.put("address", address);
            jsonObject.put("addressee", posterName);
            jsonObject.put("mobile", mobile);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        HttpManager.getInstance().postStr(jsonObject.toString(), HttpConstant.URL_SAVE_ADDRESS, requestCode, null, listener);
    }


    /**
     * 获取指定商品的优惠券信息
     *
     * @param productId
     * @param requestCode
     * @param listener
     */
    public static void getProductCouponInfo(String productId, final int requestCode, final OnHttpResponseListener listener) {
        String url = HttpConstant.URL_PRODUCT_COUNPON + productId;
        HttpManager.getInstance().get(null, url, requestCode, listener);
    }

    /**
     * 获取用户所有的优惠券信息
     *
     * @param requestCode
     * @param listener
     */
    public static void getUserCoupons(final int requestCode, final OnHttpResponseListener listener) {
        String url = HttpConstant.URL_USER_COUNPONS;
        HttpManager.getInstance().get(null, url, requestCode, listener);
    }


    /**
     * 下单
     *
     * @param requestCode
     * @param listener
     */
    public static void makeOrder(String amount, String settlementAmount, String ticketTagId, String cardCode, String productId, String goodsId, String addressId, int requestCode, final OnHttpResponseListener listener) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("amount", amount);
            jsonObject.put("settlementAmount", settlementAmount);
            jsonObject.put("ticketTagId", ticketTagId);
            jsonObject.put("cardCode", cardCode);
            jsonObject.put("productId", productId);
            jsonObject.put("goodsId", goodsId);
            jsonObject.put("addressId", addressId);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        HttpManager.getInstance().postStr(jsonObject.toString(), HttpConstant.URL_MALL_ORDER, requestCode, null, listener);
    }

    /**
     * 获取指定商品的优惠券信息
     *
     * @param listener
     */
    public static void getHotProducts(final int requestCode, final OnHttpResponseListener listener) {
        String url = HttpConstant.URL_PRODUCT_V1_PRODUCTS_HOT_SALE;
        HttpManager.getInstance().get(null, url, requestCode, listener);
    }

    /**
     * 获取指定商品的优惠券信息
     *
     * @param listener
     */
    public static void getNewProducts(final int requestCode, final OnHttpResponseListener listener) {
        String url = HttpConstant.URL_PRODUCT_V1_PRODUCTS_NEW_PRODUCT;
        HttpManager.getInstance().get(null, url, requestCode, listener);
    }

    /**
     * 更改手机号码
     *
     * @param oldphone
     * @param newPhoneNum
     * @param verifyCode
     * @param requestCode
     * @param listener
     */
    public static void modifyPhoneNum(String oldphone, String newPhoneNum, String verifyCode, final int requestCode, final OnHttpResponseListener listener) {
        List<Parameter> paramList = new ArrayList<Parameter>();
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("account", oldphone);
            jsonObject.put("source", "android");
            jsonObject.put("verifyCode", verifyCode);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        HttpManager.getInstance().postStr(jsonObject.toString(), HttpConstant.URL_SIGNIN_PHONE, requestCode, null, listener);
    }

    /**
     * 微信联合登陆
     *
     * @param requestCode
     * @param listener
     */
    public static void loginWeChat(String appKey, String code, final int requestCode, final OnHttpResponseListener listener) {
        List<Parameter> paramList = new ArrayList<Parameter>();
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("appId", appKey);
            jsonObject.put("code", code);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        HttpManager.getInstance().postStr(jsonObject.toString(), HttpConstant.URL_LOGIN_WECHAT, requestCode, null, listener);
    }

    /**
     * 绑定手机号
     *
     * @param listener
     */
    public static void bingPhoneFromWechat(String phone, String code, final int requestCode, final OnHttpResponseListener listener) {
        String url = HttpConstant.URL_BING_PHONE + "phone=" + phone + "&code=" + code;
        HttpManager.getInstance().get(null, url, requestCode, listener);
    }

    /**
     * 更新手机号
     * oldPhone=18039672339&newPhone=18811055567&code=9016
     *
     * @param listener
     */
    public static void updatePhone(String oldPhone, String newPhone, String code, final int requestCode, final OnHttpResponseListener listener) {
        String url = HttpConstant.URL_UPDATE_PHONE + "oldPhone=" + oldPhone + "&newPhone=" + newPhone + "&code=" + code;
        HttpManager.getInstance().get(null, url, requestCode, listener);
    }

    /**
     * 更新手机号
     * oldPhone=18039672339&newPhone=18811055567&code=9016
     *
     * @param listener
     */
    public static void getBanner(final int requestCode, final OnHttpResponseListener listener) {
        String url = HttpConstant.URL_BANNER;
        HttpManager.getInstance().get(null, url, requestCode, listener);
    }

}