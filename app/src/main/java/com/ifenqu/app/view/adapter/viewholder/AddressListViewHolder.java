package com.ifenqu.app.view.adapter.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.ifenqu.app.R;

import butterknife.ButterKnife;

public class AddressListViewHolder extends RecyclerView.ViewHolder {

    public AddressListViewHolder(ViewGroup view) {
        super(LayoutInflater.from(view.getContext()).inflate(R.layout.item_address_list,view,false));
        ButterKnife.bind(this,itemView);

    }
}
