package com.ifenqu.app.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.CacheUtils;
import com.ifenqu.app.R;
import com.ifenqu.app.http.HttpConstant;
import com.ifenqu.app.http.HttpRequest;
import com.ifenqu.app.http.response.OnHttpResponseListener;
import com.ifenqu.app.model.TokenModel;
import com.ifenqu.app.model.UserModel;
import com.ifenqu.app.util.CacheConstant;
import com.ifenqu.app.util.LoginUtil;
import com.ifenqu.app.util.NetworkUtil;
import com.ifenqu.app.view.activity.AboutActivity;
import com.ifenqu.app.view.activity.ChangePasswordActivity;
import com.ifenqu.app.view.activity.LoginActivity;
import com.ifenqu.app.view.activity.WebViewActivity;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 处理"我的"{@link com.ifenqu.app.view.fragment.MeFragment}
 * 头部分的内容
 */
public class HeadView extends LinearLayout {

    private SignOutDialog signOutDialog;
    private OnHttpResponseListener listener;

    @BindView(R.id.rl_logout_status)
    ViewGroup rl_logout_status;
    @BindView(R.id.rl_login_status)
    ViewGroup rl_login_status;

    @BindView(R.id.tv_login_name)
    TextView tv_login_name;
    @BindView(R.id.tv_login_phone)
    TextView tv_login_phone;
    @BindView(R.id.iv_avator)
    ImageView iv_avator;

    public HeadView(Context context) {
        this(context, null);
    }

    public HeadView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HeadView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init();
    }

    public void setHttpResponseListener(OnHttpResponseListener listener) {
        this.listener = listener;
    }

    private void init() {
        View headView = LayoutInflater.from(getContext()).inflate(R.layout.head_view_me, this, false);
        ButterKnife.bind(this, headView);
        addView(headView);

        checkCurrentStatus();

    }

    public void checkCurrentStatus() {
        boolean isLogin = LoginUtil.checkLoginStatus();
        rl_logout_status.setVisibility(isLogin ? GONE : VISIBLE);
        rl_login_status.setVisibility(isLogin ? VISIBLE : GONE);
        if (!isLogin){
            iv_avator.setImageResource(R.drawable.my_avator_default_icon);
        }
    }

    private boolean loginFirst(){
        if (!LoginUtil.checkLoginStatus()) {
            LoginActivity.start(getContext());
        }

        return LoginUtil.checkLoginStatus();
    }

    @OnClick({R.id.iv_more_info, R.id.rl_sign_in, R.id.iv_sign_out, R.id.rl_orders, R.id.rl_my_coupon, R.id.rl_my_gift})
    public void onClickView(View view) {
        switch (view.getId()) {
            case R.id.iv_more_info:
                AboutActivity.start(getContext());
                break;
            case R.id.iv_sign_out:
                if (!loginFirst())return;
                showLogOutDialog();
                break;
            case R.id.rl_sign_in:
                if (!LoginUtil.checkLoginStatus()){
                    LoginActivity.start(getContext());
                }else {
                    ChangePasswordActivity.start(getContext());
                }
                break;
            case R.id.rl_orders:
                if (!loginFirst())return;
                WebViewActivity.start(getContext(),"我的订单","https://account.ifenqu.com/personal/policy");
                break;
            case R.id.rl_my_coupon:
                if (!loginFirst())return;
                WebViewActivity.start(getContext(),"优惠券","https://account.ifenqu.com/personal/unused/");
                break;
            case R.id.rl_my_gift:
                if (!loginFirst())return;
                WebViewActivity.start(getContext(),"权益券","https://account.ifenqu.com/personal/equity");
                break;
            default:
        }
    }


    private void showLogOutDialog() {
        if (signOutDialog == null) {
            signOutDialog = new SignOutDialog(getContext());
            signOutDialog.setOnClickConfirmListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    //点击退出弹出框的确认按钮
                    if (!NetworkUtil.checkoutInternet()) {
                        signOutDialog.dismiss();
                        return;
                    }
                    signOutDialog.dismiss();
                    if (listener != null) {
                        TokenModel tokenModel = (TokenModel) CacheUtils.getInstance().getSerializable(CacheConstant.KEY_TOKEN);
                        HttpRequest.logout(tokenModel.getAccessToken(), HttpConstant.URL_SIGNOUT_INDEX, listener);
                    }
                }
            });
        }


//        signOutDialog.getConfirmButton().setTag(model);
        if (signOutDialog.isShowing()) return;
        signOutDialog.show();
    }

    public void updateData(UserModel userModel) {
        if (userModel == null) return;
        checkCurrentStatus();
        tv_login_phone.setText(userModel.getMobile());

        tv_login_name.setText(userModel.getNickName());

        String headUrl = userModel.getHeadImage();
        if (!TextUtils.isEmpty(headUrl)){
            Picasso.get().load(headUrl).placeholder(R.drawable.my_avator_default_icon).transform(new CircleTransform()).into(iv_avator);
        }
    }

}
