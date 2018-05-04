package com.ifenqu.app.view.adapter.viewholder;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.ifenqu.app.R;
import com.ifenqu.app.model.ProductModel;
import com.ifenqu.app.util.StringUtil;
import com.ifenqu.app.view.activity.ProductDetailActivity;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ShopHeadHotViewHolder extends BaseViewHolder {

    @BindView(R.id.iv_product_img)
    ImageView iv_product_img;

    @BindView(R.id.tv_product_name)
    TextView tv_product_name;

    @BindView(R.id.iv_text_subtitle)
    TextView iv_text_subtitle;

    @BindView(R.id.tv_terms)
    TextView tv_terms;

    @BindView(R.id.tv_term_price)
    TextView tv_term_price;
    private ProductModel data;

    public ShopHeadHotViewHolder(ViewGroup parent) {
        super(parent,R.layout.shop_head_item);
        ButterKnife.bind(this,itemView);
    }

    @OnClick(R.id.product_item)
    public void onClickItem(View view){
        if (data != null){
            ProductDetailActivity.start(view.getContext(),data.getProductId());
        }
    }

    @Override
    public void bindData(Object o) {
        data = (ProductModel) o;
        if (data == null) return;
        Picasso.get().load(data.getUrl()).placeholder(R.drawable.placehoder_icon).into(iv_product_img);

        tv_product_name.setText(data.getProductName());
        double totalPrice = data.getTotalPrice();
        iv_text_subtitle.setText(String.format(itemView.getResources().getString(R.string.product_list_subtitle2), StringUtil.getPrice(Double.valueOf(totalPrice))));
        List<Integer> termList = data.getTerms();
        if (termList != null && termList.size() > 0) {
            int longest = termList.get(termList.size() - 1);
            tv_terms.setText(longest + "期");
            tv_term_price.setText(StringUtil.getPrice(totalPrice/longest));
        }
    }
}
