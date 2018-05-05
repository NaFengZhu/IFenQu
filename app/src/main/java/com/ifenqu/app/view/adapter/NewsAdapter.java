package com.ifenqu.app.view.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.ifenqu.app.model.PushModel;
import com.ifenqu.app.view.adapter.viewholder.BaseViewHolder;
import com.ifenqu.app.view.adapter.viewholder.DiscoveryViewHolder;

import java.util.ArrayList;
import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    private List<PushModel> objectList = new ArrayList<>();

    public NewsAdapter() {
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new DiscoveryViewHolder(parent);
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        holder.bindData(objectList.get(position));
    }

    @Override
    public int getItemCount() {
        return objectList == null ? 0 : objectList.size();
    }

    /**
     * 更新数据
     *
     * @param objectList
     */
    public void updateData(List<PushModel> objectList) {
        this.objectList = objectList;
        notifyDataSetChanged();
    }
}
