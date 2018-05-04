package com.ifenqu.app.widget;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.TextView;

import com.ifenqu.app.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SignOutDialog extends Dialog {
    private View.OnClickListener listener;
    @BindView(R.id.tv_confirm)
    TextView tv_confirm;

    public SignOutDialog(@NonNull Context context) {
        this(context, R.style.logout_style);
    }

    public SignOutDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_sign_out);
        ButterKnife.bind(this);
        setCanceledOnTouchOutside(false);

    }

    @OnClick(R.id.tv_cancel)
    public void onClickCancel() {
        dismiss();
    }

    @OnClick(R.id.tv_confirm)
    public void onClickConfirm(View view) {
        if (listener != null) {
            listener.onClick(view);
        }
    }

    public void setOnClickConfirmListener(View.OnClickListener listener) {
        this.listener = listener;
    }


    public View getConfirmButton() {
        return tv_confirm;
    }


}
