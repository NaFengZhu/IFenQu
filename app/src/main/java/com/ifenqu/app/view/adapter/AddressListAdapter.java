package com.ifenqu.app.view.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.ifenqu.app.view.adapter.viewholder.AddressListViewHolder;

public class AddressListAdapter extends RecyclerView.Adapter<AddressListViewHolder> {

    @Override
    public AddressListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new AddressListViewHolder(parent);
    }

    @Override
    public void onBindViewHolder(AddressListViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 10;
    }
}
