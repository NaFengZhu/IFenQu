package com.ifenqu.app.view.receiver;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;

import com.blankj.utilcode.util.CacheUtils;
import com.blankj.utilcode.util.LogUtils;
import com.google.gson.Gson;
import com.ifenqu.app.R;
import com.ifenqu.app.app.Constants;
import com.ifenqu.app.app.IfenquApplication;
import com.ifenqu.app.model.PushModel;
import com.ifenqu.app.util.CacheConstant;
import com.ifenqu.app.view.activity.MainActivity;

import java.util.ArrayList;

import cn.jpush.android.api.JPushInterface;


/**
 * 接受自定义消息的推送
 */
public class JiguangPushReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        Bundle bundle = intent.getExtras();

        if (JPushInterface.ACTION_REGISTRATION_ID.equals(intent.getAction())) {
            String regId = bundle.getString(JPushInterface.EXTRA_REGISTRATION_ID);
        } else if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent.getAction())) {
            // 自定义消息不会展示在通知栏，完全要开发者写代码去处理
            String title = bundle.getString(JPushInterface.EXTRA_TITLE);
            String message = bundle.getString(JPushInterface.EXTRA_MESSAGE);
            String extras = bundle.getString(JPushInterface.EXTRA_EXTRA);
            String file = bundle.getString(JPushInterface.EXTRA_MSG_ID);

            handleExtrasContent(extras);
            createNotification(context, title, message);
            context.sendBroadcast(new Intent(Constants.IFENQU_PUSH_NOTIFICATION));
        } else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent.getAction())) {
            // 接受到推送下来的通知
            LogUtils.d("接受到推送下来的通知");
        } else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {
            // 在这里可以自己写代码去定义用户点击后的行为
            MainActivity.start(IfenquApplication.getInstance(), 1);
        } else {
        }
    }

    private void handleExtrasContent(String extras) {
        //imageUrl,productName,webUrl,time
        Gson gson = new Gson();
        PushModel model = gson.fromJson(extras, PushModel.class);
        if (model == null)return;
        model.setTime(System.currentTimeMillis());
        ArrayList<PushModel> list = (ArrayList<PushModel>) CacheUtils.getInstance().getSerializable(CacheConstant.KEY_PUSH_MESSAGES_LIST);
        if (list == null) {
            list = new ArrayList<>();
        }
        list.add(model);
        CacheUtils.getInstance().put(CacheConstant.KEY_PUSH_MESSAGES_LIST, list);
    }

    private void createNotification(Context context, String title, String content) {
        final int NOTIFY_ID = 1002;

        // There are hardcoding only for show it's just strings
        String name = "my_package_channel";
        String id = "my_package_channel_1"; // The user-visible name of the channel.
        String description = "my_package_first_channel"; // The user-visible description of the channel.

        Intent intent;
        PendingIntent pendingIntent;
        NotificationCompat.Builder builder;

        NotificationManager notifManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Bitmap bigIcon = BitmapFactory.decodeResource(context.getResources(),
                R.mipmap.app_icon);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel mChannel = notifManager.getNotificationChannel(id);
            if (mChannel == null) {
                mChannel = new NotificationChannel(id, name, NotificationManager.IMPORTANCE_HIGH);
                mChannel.setDescription(description);
                notifManager.createNotificationChannel(mChannel);
            }
            builder = new NotificationCompat.Builder(context, id);

            intent = MainActivity.getIntent(1);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
            builder.setContentTitle(title)  // required
                    .setSmallIcon(R.mipmap.app_icon) // required
                    .setLargeIcon(bigIcon)
                    .setContentText(content)  // required
                    .setAutoCancel(true)
                    .setContentIntent(pendingIntent);
        } else {
            builder = new NotificationCompat.Builder(context);

            intent = MainActivity.getIntent(1);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);

            builder.setContentTitle(title)                           // required
                    .setSmallIcon(R.mipmap.app_icon) // required
                    .setContentText(content)  // required
                    .setLargeIcon(bigIcon)
                    .setAutoCancel(true)
                    .setContentIntent(pendingIntent)
                    .setPriority(Notification.PRIORITY_HIGH);
        } // else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

        Notification notification = builder.build();
        notifManager.notify(NOTIFY_ID, notification);
    }
}
