package com.ifenqu.app.view.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.github.nuptboyzhb.lib.SuperSwipeRefreshLayout;
import com.ifenqu.app.R;
import com.ifenqu.app.util.RecyclerManager;
import com.ifenqu.app.view.BaseStatusActivity;
import com.ifenqu.app.view.adapter.AddressListAdapter;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddressListActivity extends BaseStatusActivity {

    public static final int REQUEST_CODE_ADD_NEW = 100;

    @BindView(R.id.srl_refresh)
    SuperSwipeRefreshLayout srl_refresh;

    @BindView(R.id.rv_recyclerView)
    SwipeMenuRecyclerView rv_recyclerView;

    protected AddressListAdapter addressListAdapter;

    public static void start(Activity context, int requestCde) {
        if (context == null) return;
        Intent intent = new Intent(context, AddressListActivity.class);
        context.startActivityForResult(intent, requestCde);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address_list);
        ButterKnife.bind(this);
        comm_title.setTitle(R.string.address_info);
        initRecyclerView();
        showContentView();
    }

    private void initRecyclerView() {
        RecyclerManager.getInstance().initWithLinearLayoutmanager(rv_recyclerView);
        addressListAdapter = new AddressListAdapter();
        rv_recyclerView.setAdapter(addressListAdapter);
    }

    @OnClick(R.id.tv_add_address)
    public void onClickAddAddress(View view){
        AddressModificationActivity.start(this,REQUEST_CODE_ADD_NEW);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK)return;
        if (requestCode == REQUEST_CODE_ADD_NEW){
            // TODO: 17/4/18 更新地址列表
        }
    }
}
