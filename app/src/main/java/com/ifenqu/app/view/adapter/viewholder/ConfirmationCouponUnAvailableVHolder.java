package com.ifenqu.app.view.adapter.viewholder;

import android.view.ViewGroup;
import android.widget.TextView;

import com.ifenqu.app.R;
import com.ifenqu.app.model.CardsModel;
import com.ifenqu.app.model.CouponModel;
import com.ifenqu.app.model.TicketTagsModel;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ConfirmationCouponUnAvailableVHolder extends BaseViewHolder {

    @BindView(R.id.tv_discount_time)
    TextView tv_discount_time;

    @BindView(R.id.tv_discount_content)
    TextView tv_discount_content;

    @BindView(R.id.tv_discount_amount)
    TextView tv_discount_amount;

    public ConfirmationCouponUnAvailableVHolder(ViewGroup parent) {
        super(parent, R.layout.vh_confirmation_coupon_un_available);
        ButterKnife.bind(this,itemView);
    }

    @Override
    public void bindData(Object o) {
        CouponModel couponModel = (CouponModel) o;
        if (couponModel == null)return;

        tv_discount_time.setText(String.format("有效期 %s 至 %s",couponModel.getCreateTimestamp(),couponModel.getExpireTimestamp()));

        CardsModel cardsModel = couponModel.getCards();
        if (cardsModel != null) {
            tv_discount_content.setText(cardsModel.getTitle());
        }

        ArrayList<TicketTagsModel> ticketTags = couponModel.getTicketTags();
        if (ticketTags != null){
            TicketTagsModel model = ticketTags.get(0);
            if (model != null){
                tv_discount_amount.setText(model.getTicketAmount() +"");
            }
        }
    }
}
