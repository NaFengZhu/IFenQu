package com.ifenqu.app.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.ifenqu.app.R;
import com.ifenqu.app.http.response.OnHttpResponseListener;
import com.ifenqu.app.view.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 订单详情
 */
public class OrderDetailActivity extends BaseActivity implements OnHttpResponseListener{


    @BindView(R.id.tv_pay)
    View tv_pay;

    public static void start(Context context){
        if (context == null)return;
        Intent intent = new Intent(context,OrderDetailActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);
        tv_pay.setVisibility(View.GONE);
    }

    @Override
    public void onHttpResponse(int requestCode, String resultJson, Exception e) {

    }
}
