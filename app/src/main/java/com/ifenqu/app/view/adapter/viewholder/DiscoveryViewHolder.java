package com.ifenqu.app.view.adapter.viewholder;

import android.text.TextUtils;
import android.text.format.DateUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ifenqu.app.R;
import com.ifenqu.app.model.PushModel;
import com.ifenqu.app.view.activity.WebViewActivity;
import com.squareup.picasso.Picasso;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DiscoveryViewHolder extends BaseViewHolder {

    @BindView(R.id.iv_product_img)
    ImageView iv_product_img;

    @BindView(R.id.tv_product_name)
    TextView tv_product_name;

    @BindView(R.id.rl_see_more)
    RelativeLayout rl_see_more;

    @BindView(R.id.tv_time)
    TextView tv_time;

    public DiscoveryViewHolder(ViewGroup parent) {
        super(parent, R.layout.fragment_discovery_item);
        ButterKnife.bind(this,itemView);
    }

    @Override
    public void bindData(Object o) {
        PushModel pushModel = (PushModel) o;
        if (pushModel == null)return;
        Picasso.get().load(pushModel.getImageUrl()).into(iv_product_img);
        tv_product_name.setText(pushModel.getName());
        rl_see_more.setTag(pushModel.getWebUrl());
        tv_time.setText(DateUtils.getRelativeTimeSpanString(pushModel.getTime(),
                System.currentTimeMillis(),DateUtils.MINUTE_IN_MILLIS,DateUtils.FORMAT_ABBREV_RELATIVE));

    }

    @OnClick(R.id.rl_see_more)
    public void onClickMore(View view){
        String url = (String) view.getTag();
        if (TextUtils.isEmpty(url))return;
        WebViewActivity.start(itemView.getContext(),url);
    }
}
