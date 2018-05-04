package com.ifenqu.app.util;

public class CacheConstant {

    public static final String KEY_TOKEN = "key_token";
    public static final String KEY_CURRENT_USER_INFO = "key_current_user_info";

    //消息列表
    public static final String KEY_PUSH_MESSAGES_LIST = "push_messages_list";
    public static final String KEY_WECHAT_CODE = "KEY_WECHAT_CODE";


    public static final String KEY_CACHE_ADDRESS = "KEY_CACHE_ADDRESS_"+LoginUtil.getLoginToken().getRefreshToken();


}
