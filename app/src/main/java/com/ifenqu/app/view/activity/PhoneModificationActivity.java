package com.ifenqu.app.view.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.ifenqu.app.R;
import com.ifenqu.app.view.BaseStatusActivity;

/**
 * 手机号码修改
 */
public class PhoneModificationActivity extends BaseStatusActivity {


    public static void start(Activity context) {
        if (context == null) return;
        Intent intent = new Intent(context, PhoneModificationActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_modifiy);
    }
}
