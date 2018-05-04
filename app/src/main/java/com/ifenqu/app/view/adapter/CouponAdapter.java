package com.ifenqu.app.view.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.ifenqu.app.model.IBaseModel;
import com.ifenqu.app.model.PushModel;
import com.ifenqu.app.view.adapter.viewholder.BaseViewHolder;
import com.ifenqu.app.view.adapter.viewholder.ConfirmationCouponAvailableVHolder;
import com.ifenqu.app.view.adapter.viewholder.ConfirmationCouponUnAvailableVHolder;
import com.ifenqu.app.view.adapter.viewholder.DiscoveryViewHolder;
import com.ifenqu.app.view.adapter.viewholder.ExpiredCouponViewHolder;
import com.ifenqu.app.view.adapter.viewholder.GiftViewHolder;
import com.ifenqu.app.view.adapter.viewholder.OrderListViewHolder;
import com.ifenqu.app.view.adapter.viewholder.UsedCouponViewHolder;
import com.ifenqu.app.view.adapter.viewholder.ValidateCouponViewHolder;
import com.ifenqu.app.view.adapter.viewholder.ViewHolderConstant;

import java.util.ArrayList;
import java.util.List;

public class CouponAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    private int viewType;
    private List<PushModel> objectList = new ArrayList<>();

    public CouponAdapter(int viewType) {
        this.viewType = viewType;
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case ViewHolderConstant.TYPE_VALIDATE:
                return new ValidateCouponViewHolder(parent);
            case ViewHolderConstant.TYPE_USED:
                return new UsedCouponViewHolder(parent);
            case ViewHolderConstant.TYPE_EXPIRED:
                return new ExpiredCouponViewHolder(parent);
            case ViewHolderConstant.TYPE_GIFT:
                return new GiftViewHolder(parent);
            case ViewHolderConstant.TYPE_DISCOVERY:
                return new DiscoveryViewHolder(parent);
            case ViewHolderConstant.TYPE_ORDER_LIST:
                return new OrderListViewHolder(parent);
            case ViewHolderConstant.TYPE_CONFIRMATION_COUPON_AVALIABLE:
                return new ConfirmationCouponAvailableVHolder(parent);
            case ViewHolderConstant.TYPE_CONFIRMATION_COUPON_UNAVAILABLE:
                return new ConfirmationCouponUnAvailableVHolder(parent);
            default:
        }
        return null;
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        holder.bindData(objectList.get(position));
    }

    @Override
    public int getItemCount() {
        return objectList == null?0:objectList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return viewType;
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
