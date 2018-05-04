package com.ifenqu.app.view.activity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.TextView;

import com.blankj.utilcode.util.AppUtils;
import com.ifenqu.app.R;
import com.ifenqu.app.app.Constants;
import com.ifenqu.app.view.BaseActivity;
import com.ifenqu.app.widget.CommonTitleView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 关于我们
 */
public class AboutActivity extends BaseActivity {

    @BindView(R.id.rl_current_version)
    TextView rl_current_version;
    @BindView(R.id.comm_title)
    CommonTitleView comm_title;

    public static void start(Context context){
        if (context == null)return;
        Intent intent = new Intent(context,AboutActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        ButterKnife.bind(this);
        initTitle();
        setVersionData();
    }

    private void setVersionData() {
        String name = AppUtils.getAppVersionName();
        if (!TextUtils.isEmpty(name)){
            rl_current_version.setText(String.format(getResources().getString(R.string.about_current_version),name));
        }
    }

    private void initTitle() {
        comm_title.setTitle(getResources().getString(R.string.about_title));
    }

    @OnClick(R.id.rl_privacy)
    public void clickPrivacy(){
        WebViewActivity.start(this, Constants.IFENQU_PRIVACY);
    }

    @OnClick(R.id.rl_announce)
    public void clickAnnounce(){
        WebViewActivity.start(this, Constants.IFENQU_ANNOUNCE);
    }

    @OnClick(R.id.rl_safety_announce)
    public void clickAafetyAnnounce(){
        WebViewActivity.start(this, Constants.IFENQU_SAFETY_ANNOUNCE);
    }

    @OnClick(R.id.rl_return_privacy)
    public void clickReturnPrivacy(){
        WebViewActivity.start(this, Constants.IFENQU_RETURN_PRIVACY);
    }

    @OnClick(R.id.tv_call)
    public void onClickCall(){
        String service_phone_number = "400-605-5188";

        String uri = "tel:" + service_phone_number.trim() ;
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse(uri));
        startActivity(intent);
    }

}
