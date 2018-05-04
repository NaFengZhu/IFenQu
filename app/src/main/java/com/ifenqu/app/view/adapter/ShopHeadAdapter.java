package com.ifenqu.app.view.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.ifenqu.app.model.ProductModel;
import com.ifenqu.app.view.adapter.viewholder.BaseViewHolder;
import com.ifenqu.app.view.adapter.viewholder.ShopHeadBrandViewHolder;
import com.ifenqu.app.view.adapter.viewholder.ShopHeadHotViewHolder;
import com.ifenqu.app.view.adapter.viewholder.ShopHeadNewViewHolder;
import com.ifenqu.app.widget.ShopHeadView;

import java.util.List;

public class ShopHeadAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    private int itemType;
    private List<ProductModel> dataList;
    public ShopHeadAdapter(int itemType) {
        this.itemType = itemType;
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType){
            case ShopHeadView.SHOP_HEAD_ITEM_HOT_TYPE:
                return new ShopHeadHotViewHolder(parent);
            case ShopHeadView.SHOP_HEAD_ITEM_NEW_TYPE:
                return new ShopHeadNewViewHolder(parent);
            case ShopHeadView.SHOP_HEAD_ITEM_BRAND_TYPE:
                return new ShopHeadBrandViewHolder(parent);
                default:
        }
        return null;
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        holder.bindData(dataList.get(position));
    }

    @Override
    public int getItemViewType(int position) {
        return itemType;
    }

    /**
     * 最多两屏，每平两个半
     * @return
     */
    @Override
    public int getItemCount() {
        return dataList == null?0:dataList.size();
    }

    public void update(List<ProductModel> list){
        this.dataList = list;
        notifyDataSetChanged();
    }
}
