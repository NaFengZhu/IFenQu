package com.ifenqu.app.util;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

/**
 * Created by zhunafeng on 26/3/18.
 * 处理初始化recyclerView 的东西
 */

public class RecyclerManager {

    private RecyclerManager() {
    }

    private static class RecyclerManagerHelper{
        private static final RecyclerManager INSTANCE = new RecyclerManager();
    }

    public static RecyclerManager getInstance(){
        return RecyclerManagerHelper.INSTANCE;
    }

    public void initWithGridLayoutmanager(RecyclerView recyclerView,int column){
        GridLayoutManager gridLayoutManager = new GridLayoutManager(recyclerView.getContext(), column);
        recyclerView.setLayoutManager(gridLayoutManager);
    }


    public void initWithLinearLayoutmanager(RecyclerView recyclerView){
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(recyclerView.getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
    }

    public void initWithLinearLayoutmanagerHorizontal(RecyclerView recyclerView){
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(recyclerView.getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(linearLayoutManager);
    }


}
