package com.ifenqu.app.view.adapter.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.blankj.utilcode.util.LogUtils;
import com.ifenqu.app.R;
import com.ifenqu.app.model.GoodDetailModel;
import com.ifenqu.app.model.GoodModel;
import com.ifenqu.app.model.eventbusmodel.ProductDetailEven;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;


import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProductDetailViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.id_flowlayout)
    TagFlowLayout flowLayout;
    @BindView(R.id.tv_categories)
    TextView tv_categories;

    private TagAdapter adapter;
    private List<String> titleList = new ArrayList<>();
    private int parentPosition = 0;
    private GoodModel goodModel;

    public ProductDetailViewHolder(ViewGroup parent) {
        super(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product_detail, parent, false));
        ButterKnife.bind(this,itemView);

        initViews();
    }

    private void initViews() {
        adapter = new TagAdapter(titleList) {
            @Override
            public View getView(FlowLayout parent, int position, Object o) {
                TextView textView = (TextView) LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product_category,parent,false);
                textView.setText(titleList.get(position));
                return textView;
            }
        };
        flowLayout.setAdapter(adapter);

        flowLayout.setOnSelectListener(new TagFlowLayout.OnSelectListener() {
            @Override
            public void onSelected(Set<Integer> selectPosSet) {
                if (selectPosSet.size() == 0){
                    if (parentPosition == 0){
                        EventBus.getDefault().post(new ProductDetailEven(null,parentPosition));
                    }else {
                        EventBus.getDefault().post(new ProductDetailEven(null,parentPosition));
                    }
                    return;
                }
                Iterator<Integer> iterator = selectPosSet.iterator();
                while (iterator.hasNext()){
                    int index = iterator.next();
                    GoodDetailModel model;
                    if (parentPosition == 0){
                        model = goodModel.getColorList().get(index);
                    }else {
                        model = goodModel.getStyleList().get(index);
                    }
                    EventBus.getDefault().post(new ProductDetailEven(model,parentPosition));
                }
            }
        });

        flowLayout.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
            @Override
            public boolean onTagClick(View view, int position, FlowLayout parent) {
                return false;
            }
        });
    }

    public void setData(GoodModel goodModel,int position){
        if (goodModel == null)return;
        this.parentPosition = position;
        this.goodModel = goodModel;
        titleList.clear();


        if (position == 0){
            tv_categories.setText(goodModel.getColorTitle());
            List<GoodDetailModel> goodDetailModels = goodModel.getColorList();
            for (int i = 0; i < goodDetailModels.size(); i++) {
                GoodDetailModel model = goodDetailModels.get(i);
                if (model == null)continue;
                titleList.add(model.getName());
                if (model.isSelected()){
                    adapter.setSelected(i,model.getName());
                }
            }
        }else {
            //index = 1
            tv_categories.setText(goodModel.getStyleTitle());
            List<GoodDetailModel> goodDetailModels = goodModel.getStyleList();
            for (int i = 0; i < goodDetailModels.size(); i++) {
                GoodDetailModel model = goodDetailModels.get(i);
                if (model == null)continue;
                titleList.add(model.getName());
                if (model.isSelected()){
                    adapter.setSelected(i,model.getName());
                }
            }
        }

        adapter.notifyDataChanged();
    }
}
