package com.ifenqu.app.view.adapter.viewholder;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.ifenqu.app.R;
import com.ifenqu.app.model.SysOrderDetailModel;
import com.ifenqu.app.model.SysOrderModel;
import com.ifenqu.app.view.activity.OrderDetailActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class OrderListViewHolder extends BaseViewHolder {

    @BindView(R.id.tv_status_line)
    TextView tv_status_line;

    @BindView(R.id.tv_status)
    TextView tv_status;

    @BindView(R.id.tv_pay)
    TextView tv_pay;

    @BindView(R.id.iv_product_img)
    ImageView iv_product_img;

    @BindView(R.id.tv_product_name)
    TextView tv_product_name;

    @BindView(R.id.tv_product_price)
    TextView tv_product_price;

    public OrderListViewHolder(ViewGroup parent) {
        super(parent, R.layout.item_order);
        ButterKnife.bind(this,itemView);
    }

    @Override
    public void bindData(Object o) {
        SysOrderModel sysOrderModel = (SysOrderModel) o;
        if (sysOrderModel == null)return;

        String payStatus = sysOrderModel.getPayStatus();
        updatePayStatus(payStatus);

        RequestOptions options = RequestOptions.placeholderOf(R.drawable.about_bg2);
        Glide.with(itemView.getContext()).load("").apply(options).into(iv_product_img);

        List<SysOrderDetailModel> list = sysOrderModel.getSysOrderDetailList();
        SysOrderDetailModel model = list.get(0);
        tv_product_name.setText(model.getGoodsDesc());

    }

    private void updatePayStatus(String payStatus) {
        if (SysOrderModel.PAY_STATUS_PAID.endsWith(payStatus)){

        }else if (SysOrderModel.PAY_STATUS_UNPAY.endsWith(payStatus)){
        }else {

        }
    }

    @OnClick(R.id.order_list_parent)
    public void onClickItem(View view){
        OrderDetailActivity.start(view.getContext());
    }

}
