package com.ifenqu.app.view.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ifenqu.app.R;
import com.ifenqu.app.model.ProductModel;
import com.ifenqu.app.util.StringUtil;
import com.ifenqu.app.view.activity.ProductDetailActivity;
import com.ifenqu.app.view.adapter.viewholder.BaseViewHolder;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by zhunafeng on 17/3/18.
 */

public class ShopRecyclerViewAdapter extends RecyclerView.Adapter<ShopRecyclerViewAdapter.ShopViewHolder> {

    private List<ProductModel> productModelList;

    @Override
    public ShopRecyclerViewAdapter.ShopViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ShopViewHolder(parent);
    }

    @Override
    public void onBindViewHolder(ShopRecyclerViewAdapter.ShopViewHolder holder, int position) {
        holder.bindData(productModelList.get(position));
    }

    @Override
    public int getItemCount() {
        return productModelList == null ? 0 : productModelList.size();
    }

    public static class ShopViewHolder extends BaseViewHolder {

        @BindView(R.id.iv_product_img)
        ImageView iv_product_img;

        @BindView(R.id.tv_product_name)
        TextView tv_product_name;

        @BindView(R.id.tv_term_price)
        TextView tv_term_price;

        @BindView(R.id.tv_terms)
        TextView tv_terms;

        @BindView(R.id.tv_total_price)
        TextView tv_total_price;

        public ShopViewHolder(ViewGroup parent) {
            super(parent, R.layout.recyclerview_item);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ProductModel data = (ProductModel) v.getTag();
                    if (data != null){
                        ProductDetailActivity.start(v.getContext(),data.getProductId());
                    }
                }
            });
        }

        @Override
        public void bindData(Object o) {
            ProductModel data = (ProductModel) o;
            if (data == null) return;

            itemView.setTag(data);
            Picasso.get().load(data.getUrl()).placeholder(R.drawable.placehoder_icon).into(iv_product_img);

            tv_product_name.setText(data.getProductName());
            double totalPrice = data.getTotalPrice();
            tv_total_price.setText(String.format(itemView.getResources().getString(R.string.product_list_subtitle2),StringUtil.getPrice((totalPrice))));
            List<Integer> termList = data.getTerms();
            if (termList != null && termList.size() > 0) {
                int longest = termList.get(termList.size() - 1);
                tv_terms.setText(longest + "期");
                tv_term_price.setText(StringUtil.getPrice(totalPrice/longest));
//                handleLabels();
            }
        }
    }


    public void update(List<ProductModel> productModelList) {
        List<ProductModel> templeList = productModelList;
        if (productModelList != null && productModelList.size() > 6){
            templeList = productModelList.subList(0,6);
        }
        this.productModelList = templeList;
        notifyDataSetChanged();
    }
}
