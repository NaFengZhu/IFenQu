package com.ifenqu.app.view.adapter.viewholder;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;
import com.ifenqu.app.R;
import com.ifenqu.app.model.CardsModel;
import com.ifenqu.app.model.CouponModel;
import com.ifenqu.app.model.TicketTagsModel;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class ValidateCouponViewHolder extends BaseViewHolder {

    @BindView(R.id.tv_validate_amount)
    TextView tv_validate_amount;

    @BindView(R.id.tv_validate_content)
    TextView tv_validate_content;

    @BindView(R.id.tv_validate_use)
    TextView tv_validate_use;

    @BindView(R.id.tv_validate_time)
    TextView tv_validate_time;

    public ValidateCouponViewHolder(ViewGroup parent) {
        super(parent, R.layout.item_validate);//???
        ButterKnife.bind(this, itemView);
    }

    @Override
    public void bindData(Object o) {
        CouponModel couponModel = (CouponModel) o;
        if (couponModel == null) return;
        CardsModel cardsModel = couponModel.getCards();
        if (cardsModel != null) {
            tv_validate_content.setText(cardsModel.getTitle());
            tv_validate_time.setText(String.format("有效期 %s 至 %s", cardsModel.getBeginTimestamp(), cardsModel.getEndTimestamp()));
        }

        ArrayList<TicketTagsModel> ticketTags = couponModel.getTicketTags();
        if (ticketTags != null) {
            TicketTagsModel model = ticketTags.get(0);
            if (model != null) {
                tv_validate_amount.setText(model.getTicketAmount());
            }
        }

    }

    @OnClick(R.id.tv_validate_use)
    public void onClickUse(View view) {
        ToastUtils.showShort("要使用嘛");
    }

}
