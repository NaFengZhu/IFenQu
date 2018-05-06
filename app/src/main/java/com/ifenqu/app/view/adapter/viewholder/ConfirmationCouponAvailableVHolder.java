package com.ifenqu.app.view.adapter.viewholder;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;
import com.ifenqu.app.R;
import com.ifenqu.app.model.CardsModel;
import com.ifenqu.app.model.CouponModel;
import com.ifenqu.app.model.TicketTagsModel;
import com.ifenqu.app.model.eventbusmodel.CouponEvent;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ConfirmationCouponAvailableVHolder extends BaseViewHolder{

    @BindView(R.id.tv_validate_amount)
    TextView tv_validate_amount;

    @BindView(R.id.tv_validate_content)
    TextView tv_validate_content;

    @BindView(R.id.tv_validate_use)
    TextView tv_validate_use;

    @BindView(R.id.tv_validate_time)
    TextView tv_validate_time;

    @BindView(R.id.ll_item_parent)
    LinearLayout ll_item_parent;

    public ConfirmationCouponAvailableVHolder(ViewGroup parent) {
        super(parent, R.layout.vh_confirmation_coupon_available);
        ButterKnife.bind(this, itemView);
    }

    @Override
    public void bindData(Object o) {
        CouponModel couponModel = (CouponModel) o;
        if (couponModel == null) return;
        ll_item_parent.setTag(couponModel);

        tv_validate_time.setText(String.format("有效期 %s 至 %s", couponModel.getCreateTimestamp(), couponModel.getExpireTimestamp()));

        CardsModel cardsModel = couponModel.getCards();
        if (cardsModel != null) {
            tv_validate_content.setText(cardsModel.getTitle());
        }

        ArrayList<TicketTagsModel> ticketTags = couponModel.getTicketTags();
        if (ticketTags != null) {
            TicketTagsModel model = ticketTags.get(0);
            if (model != null) {
                tv_validate_amount.setText(model.getTicketAmount() +"");
            }
        }

    }

    @OnClick(R.id.ll_item_parent)
    public void onClickUse(View view) {
        CouponModel couponModel = (CouponModel) ll_item_parent.getTag();
        if (couponModel == null)return;
        EventBus.getDefault().post(new CouponEvent(couponModel));
    }
}
