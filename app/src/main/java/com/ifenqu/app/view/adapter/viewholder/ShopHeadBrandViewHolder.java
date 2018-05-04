package com.ifenqu.app.view.adapter.viewholder;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.ifenqu.app.R;
import com.ifenqu.app.model.ProductModel;
import com.ifenqu.app.view.activity.ProductListActivity;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ShopHeadBrandViewHolder extends BaseViewHolder {

    @BindView(R.id.iv_brand)
    ImageView iv_brand;


    public ShopHeadBrandViewHolder(ViewGroup parent) {
        super(parent, R.layout.shop_head_brand);
        ButterKnife.bind(this, itemView);
    }

    @OnClick(R.id.iv_brand)
    public void onClickItem(View view) {
        ProductListActivity.start(view.getContext());
    }

    @Override
    public void bindData(Object o) {
        ProductModel model = (ProductModel) o;
        if (o == null) return;
        int index = (int) model.getGoodsId();
        int resId;
        switch (index) {
            case 0:
                resId = R.drawable.philips_icon;
                break;
            case 1:
                resId = R.drawable.apple_logo;
                break;
            case 2:
                resId = R.drawable.beats_logo;
                break;
            case 3:
                resId = R.drawable.bose_logo;
                break;
            default:
                resId = R.drawable.bose_logo;

        }

        Picasso.get().load(resId).placeholder(R.drawable.placehoder_icon).into(iv_brand);

    }
}
