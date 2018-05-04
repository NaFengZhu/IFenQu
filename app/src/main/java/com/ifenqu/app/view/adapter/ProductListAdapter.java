package com.ifenqu.app.view.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.ifenqu.app.model.ProductModel;
import com.ifenqu.app.view.adapter.viewholder.ProductListViewHolder;

import java.util.List;

public class ProductListAdapter extends RecyclerView.Adapter<ProductListViewHolder> {


    private List<ProductModel> list;

    @Override
    public ProductListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ProductListViewHolder(parent);
    }

    @Override
    public void onBindViewHolder(ProductListViewHolder holder, int position) {
        holder.setData(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list == null?0:list.size();
    }

    public void update(List<ProductModel> list){
        this.list = list;
        notifyDataSetChanged();
    }
}
