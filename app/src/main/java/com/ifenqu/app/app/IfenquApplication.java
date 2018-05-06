package com.ifenqu.app.app;

import android.app.Application;
import android.app.Notification;

import com.androidnetworking.AndroidNetworking;
import com.blankj.utilcode.BuildConfig;
import com.blankj.utilcode.util.Utils;
import com.growingio.android.sdk.collection.Configuration;
import com.growingio.android.sdk.collection.GrowingIO;
import com.ifenqu.app.R;
import com.squareup.leakcanary.LeakCanary;
import cn.jpush.android.api.BasicPushNotificationBuilder;
import cn.jpush.android.api.JPushInterface;

/**
 * Created by zhunafeng on 15/3/18.
 */

public class IfenquApplication extends Application {
    private static IfenquApplication ifenquApplication;

    public static IfenquApplication getInstance() {
        return ifenquApplication;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        ifenquApplication = this;

        //初始化 androidNetworking(http library)
        AndroidNetworking.initialize(getApplicationContext());

        initLeakUtil();

        Utils.init(this);

        initGrowingio();

        configJPush();
    }

    /**
     * 极光推送的配置
     */
    private void configJPush() {
        JPushInterface.setDebugMode(BuildConfig.DEBUG);
        JPushInterface.init(this);

        BasicPushNotificationBuilder builder = new BasicPushNotificationBuilder(this);
        builder.statusBarDrawable = R.mipmap.app_icon;
        builder.notificationFlags = Notification.FLAG_AUTO_CANCEL
                | Notification.FLAG_SHOW_LIGHTS;  //设置为自动消失和呼吸灯闪烁
        builder.notificationDefaults = Notification.DEFAULT_SOUND
                | Notification.DEFAULT_VIBRATE
                | Notification.DEFAULT_LIGHTS;  // 设置为铃声、震动、呼吸灯闪烁都要
        JPushInterface.setPushNotificationBuilder(1, builder);
    }

    /**
     * 初始化growingio
     */
    private void initGrowingio() {
        GrowingIO.startWithConfiguration(this, new Configuration()
                .useID()
                .trackAllFragments()
                .setChannel("xiaomi").setDebugMode(!BuildConfig.DEBUG));
    }

    /**
     * @hide 检测内存泄漏
     */
    private void initLeakUtil() {
        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        LeakCanary.install(this);
    }

    /**
     * 获取当前用户id
     *
     * @return
     */
    public long getCurrentUserId() {
        return -1;
    }
}
