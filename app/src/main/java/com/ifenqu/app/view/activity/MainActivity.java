package com.ifenqu.app.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;
import com.ifenqu.app.R;
import com.ifenqu.app.app.IfenquApplication;
import com.ifenqu.app.util.LoginUtil;
import com.ifenqu.app.view.BaseActivity;
import com.ifenqu.app.view.adapter.BanSlideVPAdapter;
import com.ifenqu.app.widget.BanSlideViewPager;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 主页
 */
public class MainActivity extends BaseActivity {

    @BindView(R.id.bvp_viewPager)
    BanSlideViewPager bvp_viewPager;
    @BindView(R.id.tab_shop_img)
    ImageView tab_shop_img;
    @BindView(R.id.tab_discovery_img)
    ImageView tab_discovery_img;
    @BindView(R.id.tab_mine_img)
    ImageView tab_mine_img;
    @BindView(R.id.tab_shopt_text)
    TextView tab_shopt_text;
    @BindView(R.id.tab_discovery_text)
    TextView tab_discovery_text;
    @BindView(R.id.tab_mine_text)
    TextView tab_mine_text;

    private static final int INDEX_SHOP = 0;
    private static final int INDEX_DISCOVERY = 1;
    private static final int INDEX_ME = 2;
    private BanSlideVPAdapter mBanSlideVPAdapter;
    private boolean isExit;
    private Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            isExit = false;
        }
    };

    public static void start(Context context) {
        if (context == null) return;
        context.startActivity(new Intent(context, MainActivity.class));
    }

    public static void start(Context context,int index) {
        if (context == null) return;
        Intent intent = new Intent(context, MainActivity.class);
        intent.putExtra("index",index);
        context.startActivity(intent);
    }

    public static Intent getIntent(int index) {
        Intent intent = new Intent(IfenquApplication.getInstance(), MainActivity.class);
        intent.putExtra("index",index);
        return intent;
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setViewPageSmoothScrollDisEnable(intent.getIntExtra("index",bvp_viewPager.getCurrentItem()));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        initViewPager();

    }

    private void initViewPager() {
        mBanSlideVPAdapter = new BanSlideVPAdapter(getSupportFragmentManager());
        bvp_viewPager.setAdapter(mBanSlideVPAdapter);
        setViewPageSmoothScrollDisEnable(getIntent().getIntExtra("index",INDEX_SHOP));
    }

    @OnClick({R.id.rl_discovery, R.id.rl_shop, R.id.rl_me})
    public void clickTab(View view) {
        switchTabs(view.getId());
    }

    private void switchTabs(int id) {
        changeTabStyle(id);

        changeFragment(id);
    }

    private void changeFragment(int id) {
        switch (id) {
            case R.id.rl_discovery:
                setViewPageSmoothScrollDisEnable(INDEX_DISCOVERY);
                break;
            case R.id.rl_me:
                setViewPageSmoothScrollDisEnable(INDEX_ME);
                break;
            default:
                setViewPageSmoothScrollDisEnable(INDEX_SHOP);
        }
    }

    private void setViewPageSmoothScrollDisEnable(int index) {
        bvp_viewPager.setCurrentItem(index, false);

        switch (index) {
            case INDEX_SHOP:
                tab_shop_img.setImageResource(R.drawable.tab_shop_selected_icon);
                tab_shopt_text.setTextColor(getResources().getColor(R.color.tab_color_selected));

                tab_discovery_img.setImageResource(R.drawable.tab_news_icon);
                tab_discovery_text.setTextColor(getResources().getColor(R.color.tab_color));

                tab_mine_img.setImageResource(R.drawable.tab_mine_icon);
                tab_mine_text.setTextColor(getResources().getColor(R.color.tab_color));
                break;
            case INDEX_DISCOVERY:
                tab_shop_img.setImageResource(R.drawable.tab_shop_icon);
                tab_shopt_text.setTextColor(getResources().getColor(R.color.tab_color));

                tab_discovery_img.setImageResource(R.drawable.tab_news_noti_icon);
                tab_discovery_text.setTextColor(getResources().getColor(R.color.tab_color_selected));

                tab_mine_img.setImageResource(R.drawable.tab_mine_icon);
                tab_mine_text.setTextColor(getResources().getColor(R.color.tab_color));
                break;
            case INDEX_ME:
                tab_shop_img.setImageResource(R.drawable.tab_shop_icon);
                tab_shopt_text.setTextColor(getResources().getColor(R.color.tab_color));

                tab_discovery_img.setImageResource(R.drawable.tab_news_icon);
                tab_discovery_text.setTextColor(getResources().getColor(R.color.tab_color));

                tab_mine_img.setImageResource(R.drawable.tab_mine_selected_icon);
                tab_mine_text.setTextColor(getResources().getColor(R.color.tab_color_selected));
                break;
            default:
        }
    }

    private void changeTabStyle(int id) {

    }

    @Override
    public void onBackPressed() {
        exit();
    }

    /**
     * 退出应用
     */
    private void exit() {
        if (!isExit) {
            isExit = true;
            ToastUtils.showShort(R.string.shut_down_app);
            mHandler.sendEmptyMessageDelayed(0, 1000);  // 利用handler延迟发送更改状态信息
        } else {
            this.finish();
        }
    }
}

