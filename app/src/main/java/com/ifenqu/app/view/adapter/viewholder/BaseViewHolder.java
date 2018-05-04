package com.ifenqu.app.view.adapter.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;


public abstract class BaseViewHolder<T> extends RecyclerView.ViewHolder {

    public BaseViewHolder(ViewGroup parent,int resId) {
        super(LayoutInflater.from(parent.getContext()).inflate(resId,parent,false));
    }

    /**
     * 实现数据绑定
     * @param t
     */
    public abstract void bindData(T t);


}
