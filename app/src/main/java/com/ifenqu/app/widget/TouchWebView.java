package com.ifenqu.app.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.webkit.WebView;

public class TouchWebView extends WebView {

    public TouchWebView(Context context) {
        this(context,null);
    }

    public TouchWebView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public TouchWebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event){
        requestDisallowInterceptTouchEvent(true);
        return super.onTouchEvent(event);
    }
}
