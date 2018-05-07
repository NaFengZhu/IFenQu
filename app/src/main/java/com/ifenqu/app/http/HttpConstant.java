package com.ifenqu.app.http;

import com.ifenqu.app.BuildConfig;
import com.ifenqu.app.util.SettingUtil;

/**
 * Created by zhunafeng on 16/3/18.
 */

public class HttpConstant {
    /**
     * 1.	所有Request Content-Type 为 application/json
     * 2.	所有Response Content-Type为 application/json;charset=utf8
     */

    /**
     * 由于接口分为陈二飞和胡勇两个人写，所以部分接口的地址要加 {@link #BASE_POLITICS_CHEN};
     */
    public static final String BASE_POLITICS_CHEN = "/account";
    public static final String BASE_POLITICS_HU = "";

    public static final int RESPONDE_SUCCESS = 0;

    private static String BASE_URL = BuildConfig.BASE_URL;
    ;
    private static String BASE_GEE_URL = BuildConfig.BASE_GEE_URL;
    private static String BASE_WECHAT_URL = BuildConfig.BASE_WECHAT_URL;

    public static final String CAPTURE_URL = BASE_GEE_URL + "/v2/captcha";
    public static final String VALUATE_URL = BASE_GEE_URL + "/v2/validate";
    /**
     * 获取当前用户信息
     * GET
     */
    public static String URL_USER_INFO = BASE_URL+BASE_POLITICS_CHEN + "/v1/info";
    public static int URL_USER_INFO_INDEX = 0;
    /**
     * 发送手机验证
     * Gee 验证
     * POST
     */
    public static String URL_VERTIFICATION_CODE = BASE_URL +BASE_POLITICS_CHEN+ "/v1/send-login-verify-code";
    public static int URL_VERTIFICATION_CODE_INDEX = 1;


    /**
     * 手机号+验证码登录
     * POST
     */
    public static String URL_SIGNIN_PHONE = BASE_URL + BASE_POLITICS_CHEN+"/v1/login-verify";
    public static int URL_SIGNIN_PHONE_INDEX = 2;
    /**
     * 退出登录
     * POST
     */
    public static String URL_SIGNOUT = BASE_URL + "/account/v1/logout";
    public static int URL_SIGNOUT_INDEX = 3;

    /**
     * 获取卡圈列表
     * get
     */
    public static String URL_COUPON = BASE_URL + "/cms/v1/cards";
    public static int URL_COUPON_INDEX = 4;

    /**
     * 获取订单列表
     * post
     */
    public static String URL_ORDER_LIST = BASE_URL + "/personal/v1/order/list";
    public static int URL_ORDER_LIST_INDEX = 5;

    /**
     * 查询订单详情
     * get
     */
    public static String URL_ORDER_DETAIL = BASE_URL + "/info/{orderId}";
    public static int URL_ORDER_DETAIL_INDEX = 6;

    /**
     * 地址保存接口
     * post
     */
    public static String URL_SAVE_ADDRESS = BASE_URL + "/mall/v1/address";
    public static int URL_SAVE_ADDRESS_INDEX = 7;

    /**
     * 自定义Gee验证api1
     * get
     */
    public static String URL_GEE_CAPTURE = BASE_GEE_URL + "/v2/captcha";
    public static int URL_GEE_CAPTURE_INDEX = 8;

    /**
     * 自定义Gee验证api2
     * post
     */
    public static String URL_GEE_VALUATE = BASE_GEE_URL + "/v2/validate";
    public static int URL_GEE_VALUATE_INDEX = 9;

    /**
     * 微信联合登陆
     * post
     */
    public static String URL_WECHAT = BASE_GEE_URL +BASE_POLITICS_CHEN+"/v1/login-wechat";
    public static int URL_WECHAT_INDEX = 10;

    /**
     * 获取启动页配置信息
     * @param indexId 约定 1
     * GET
     */
    public static String URL_SPLASH = BASE_URL + "/cms/v1/content/1";
    public static int URL_SPLASH_INDEX = 11;
    /**
     * 获取产品详情
     *
     * @param productId
     * GET
     */
    public static String URL_PRODUCT_DETAIL = BASE_URL + "/mall/v1/product/";
    public static int URL_PRODUCT_DETAIL_INDEX = 13;
    /**
     * 查询关联产品详情
     *
     * @param productId
     * /products/{productId}/contents
     * GET
     */
    public static String URL_PRODUCT_REALATIVED_CONTENT_SUFF = "/contents";
    public static String URL_PRODUCT_REALATIVED_CONTENT = BASE_URL + "/product/v1/products/";
    public static int URL_PRODUCT_REALATIVED_CONTENT_INDEX = 14;

    /**
     * 产品列表
     *
     * /product/v1/products/selection
     * GET
     */
    public static String URL_PRODUCT_LIST = BASE_URL + "/product/v1/products";
    public static int URL_PRODUCT_LIST_INDEX = 15;

    /**
     * 1.返回指定商品的优惠券信息 
     *
     * /v1/mall/goods_ticket/{goodsId}
     * GET
     */
    public static String URL_PRODUCT_COUNPON = BASE_URL + "/mall/v1/goods_ticket/";
    public static int URL_PRODUCT_COUNPON_INDEX = 16;

    /**
     * 下单
     * <p>
     * /v1/mall/order
     * POST
     */
    public static String URL_MALL_ORDER = BASE_URL + "/mall/v1/order";
    public static int URL_MALL_ORDER_INDEX = 17;

    /**
     * 获取商品首页热销榜
     * <p>
     * /product/v1/products/hot_sale
     * Get
     */
    public static String URL_PRODUCT_V1_PRODUCTS_HOT_SALE = BASE_URL + "/product/v1/products/hot_sale";
    public static int URL_PRODUCT_V1_PRODUCTS_HOT_SALE_INDEX = 18;

    /**
     * 获取商品列表商品首发
     * <p>
     * /product/v1/products/new_product
     * Get
     */
    public static String URL_PRODUCT_V1_PRODUCTS_NEW_PRODUCT = BASE_URL + "/product/v1/products/new_product";
    public static int URL_PRODUCT_V1_PRODUCTS_NEW_PRODUCT_INDEX = 19;

    /**
     * 更新手机号码
     *
     */
    public static String URL_MODIFY_PHONE_NUM = BASE_URL + BASE_POLITICS_CHEN+"/v1/login-verify";
    public static int URL_MODIFY_PHONE_NUM_INDEX = 20;/**


     /*
     * 微信联合登陆
     *
     */
    public static String URL_LOGIN_WECHAT = BASE_URL + BASE_POLITICS_CHEN+"/v1/login-wechat";
    public static int URL_LOGIN_WECHAT_INDEX = 21;


    /**
     * 获取指定商品优惠券信息
     *
     * /v1/mall/goods_ticket/{goodsId}
     * GET
     */
    public static String URL_USER_COUNPONS = BASE_URL + "/personal/v1/card?type=1";
    public static int URL_USER_COUNPONS_INDEX = 23;

    /**
     * 绑定手机号
     * <p>
     * https://wechat.ifenqu.com/personal/rest/relate?phone=18039672339&code=2235
     * GET
     */
    public static String URL_BING_PHONE = BASE_WECHAT_URL + "/personal/rest/relate?";
    public static int URL_BING_PHONE_INDEX = 24;

    /**
     * 更新手机号
     * <p>
     * https://wechat.ifenqu.com/personal/rest/update?oldPhone=18039672339&newPhone=18811055567&code=9016
     * GET
     */
    public static String URL_UPDATE_PHONE = BASE_WECHAT_URL + "/personal/rest/update?";
    public static int URL_UPDATE_PHONE_INDEX = 25;

    /**
     * banner
     * <p>
     * https://rest.ifenqu.com/sys/v1/banner/SH_BANNER
     * GET
     */
    public static String URL_BANNER = BASE_URL + "/sys/v1/banner/home_banner";
    public static int URL_BANNER_INDEX = 26;

}