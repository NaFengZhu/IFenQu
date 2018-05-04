package com.ifenqu.app.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

import com.ifenqu.app.R;
import com.ifenqu.app.app.IfenquApplication;

/**
 * 网络状态 工具
 */
public class NetworkUtil {

    private static Toast mToast;

    /**
     * 判断网络是否连通
     */
    public static boolean isNetworkConnected(Context context) {
        try {
            if(context!=null){
                @SuppressWarnings("static-access")
                ConnectivityManager cm = (ConnectivityManager) context
                        .getSystemService(context.CONNECTIVITY_SERVICE);
                NetworkInfo info = cm.getActiveNetworkInfo();
                return info != null && info.isConnected();
            }else{
                /**如果context为空，就返回false，表示网络未连接*/
                return false;
            }
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }


    }

    public static boolean isWifiConnected(Context context) {
        if (context != null) {
            ConnectivityManager cm = (ConnectivityManager) context
                    .getSystemService(context.CONNECTIVITY_SERVICE);
            NetworkInfo info = cm.getActiveNetworkInfo();
            return info != null && (info.getType() == ConnectivityManager.TYPE_WIFI);
        } else {
            /**如果context为null就表示为未连接*/
            return false;
        }

    }

    @SuppressLint("ShowToast")
    public static boolean checkoutInternet() {
        if (NetworkUtil.isNetworkConnected(IfenquApplication.getInstance()))return true;
        if (mToast == null) {
            mToast = Toast.makeText(IfenquApplication.getInstance(), IfenquApplication.getInstance().getResources().getString(R.string.toast_no_internet), Toast.LENGTH_SHORT);
        }
        mToast.show();
        return false;
    }
}
