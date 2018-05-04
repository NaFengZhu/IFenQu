package com.ifenqu.app.view.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.ifenqu.app.model.GoodModel;
import com.ifenqu.app.view.adapter.viewholder.ProductDetailViewHolder;

public class ProductDetailAdapter extends RecyclerView.Adapter<ProductDetailViewHolder> {

    private GoodModel goodModel;

    @Override
    public ProductDetailViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ProductDetailViewHolder(parent);
    }

    @Override
    public void onBindViewHolder(ProductDetailViewHolder holder, int position) {
        holder.setData(goodModel,position);
    }

    @Override
    public int getItemCount() {
        //v1.0 目前就支持两个类型
        return 2;
    }

    public void update(GoodModel goodModel){
        this.goodModel = goodModel;
        notifyDataSetChanged();
    }
}
