package com.ifenqu.app.widget;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ifenqu.app.R;

/**
 * Created by zhunafeng on 26/3/18.
 * 此类为公共头部分的类
 */

public class CommonTitleView extends RelativeLayout implements View.OnClickListener {

    private ImageView iv_back;
    private TextView tv_title;
    private String titleStr;
    private LinearLayout extra_view;

    public CommonTitleView(Context context) {
        this(context, null);
    }

    public CommonTitleView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CommonTitleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CommonTitleView);
        if (typedArray.getText(R.styleable.CommonTitleView_common_title) != null) {
            titleStr = typedArray.getText(R.styleable.CommonTitleView_common_title).toString();
        }
        init();
    }

    private void init() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.layout_common_title, this, false);
        iv_back = view.findViewById(R.id.iv_back);
        iv_back.setOnClickListener(this);
        tv_title = view.findViewById(R.id.tv_title);
        extra_view = view.findViewById(R.id.extra_view);

        setTitle(titleStr);

        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        params.addRule(RelativeLayout.CENTER_VERTICAL);
        view.setLayoutParams(params);
        addView(view);
    }

    public void setTitle(String str) {
        tv_title.setText(str);
    }

    public void setTitle(int resId) {
        tv_title.setText(resId);
    }

    public void setBackBtnVisibility(boolean visibility){
        iv_back.setVisibility(visibility?VISIBLE:INVISIBLE);
    }


    public void setBackBtnListener(OnClickListener listener){
        iv_back.setOnClickListener(listener);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                if (getContext() instanceof Activity) {
                    ((Activity) getContext()).finish();
                }
                break;
            default:
        }
    }

    public void addRightView(View view,OnClickListener listener){
        extra_view.removeAllViews();
        extra_view.addView(view);
        extra_view.setOnClickListener(listener);
    }

    public ViewGroup rightViewParentView(){
        return extra_view;
    }
}
