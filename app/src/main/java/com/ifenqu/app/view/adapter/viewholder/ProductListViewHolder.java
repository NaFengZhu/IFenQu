package com.ifenqu.app.view.adapter.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
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

public class ProductListViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.iv_product_img)
    ImageView iv_product_img;

    @BindView(R.id.tv_product_name)
    TextView tv_product_name;

    @BindView(R.id.ll_container_labels)
    LinearLayout ll_container_labels;

    @BindView(R.id.tv_term_price)
    TextView tv_term_price;

    @BindView(R.id.tv_terms)
    TextView tv_terms;
    @BindView(R.id.tv_total_price)
    TextView tv_total_price;

    @BindView(R.id.product_item)
    LinearLayout product_item;

    public ProductListViewHolder(View view) {
        super(LayoutInflater.from(view.getContext()).inflate(R.layout.item_product_list, null, false));
        ButterKnife.bind(this, itemView);
    }

    public void setData(ProductModel data) {
        if (data == null) return;
        Picasso.get().load(data.getUrl()).placeholder(R.drawable.placehoder_icon).into(iv_product_img);

        product_item.setTag(data.getProductId());
        tv_product_name.setText(data.getProductName());
        double totalPrice = data.getTotalPrice();

        tv_total_price.setText(String.format(itemView.getResources().getString(R.string.product_list_subtitle1), StringUtil.getPrice(totalPrice)));

        List<Integer> termList = data.getTerms();
        if (termList != null && termList.size() > 0) {
            int longest = termList.get(termList.size() - 1);
            tv_terms.setText(longest + "期");
            tv_term_price.setText(String.format(itemView.getResources().getString(R.string.product_list_subtitle_1),StringUtil.getPrice(totalPrice/longest)));
            handleLabels();
        }
    }

    private void handleLabels() {

    }

    @OnClick(R.id.product_item)
    public void onClickProductListItem(View view) {
        ProductDetailActivity.start(view.getContext(), (Long) view.getTag());
    }
}
