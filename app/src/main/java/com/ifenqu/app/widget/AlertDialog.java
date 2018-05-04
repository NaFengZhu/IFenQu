/*Copyright ©2015 TommyLemon(https://github.com/TommyLemon)

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.*/

package com.ifenqu.app.widget;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.ifenqu.app.R;

/**
 * 通用对话框类
 *
 * @use new AlertDialog(...).show();
 */
public class AlertDialog extends Dialog implements android.view.View.OnClickListener {
    //	private static final String TAG = "AlertDialog";

    /**
     * 自定义Dialog监听器
     */
    public interface OnDialogButtonClickListener {

        /**
         * 点击按钮事件的回调方法
         *
         * @param requestCode 传入的用于区分某种情况下的showDialog
         * @param isPositive
         */
        void onDialogButtonClick(int requestCode, boolean isPositive);
    }


    @SuppressWarnings("unused")
    private Context context;
    private String title;
    private String message;
    private String strPositive;
    private String strNegative;
    private boolean showNegativeButton = true;
    private int requestCode;
    private OnDialogButtonClickListener listener;

    /**
     * 带监听器参数的构造函数
     */
    public AlertDialog(Context context, String title, String message, boolean showNegativeButton,
                       int requestCode, OnDialogButtonClickListener listener) {
        super(context, R.style.MyDialog);

        this.context = context;
        this.title = title;
        this.message = message;
        this.showNegativeButton = showNegativeButton;
        this.requestCode = requestCode;
        this.listener = listener;
    }

    public AlertDialog(Context context, String title, String message, boolean showNegativeButton,
                       String strPositive, int requestCode, OnDialogButtonClickListener listener) {
        super(context, R.style.MyDialog);

        this.context = context;
        this.title = title;
        this.message = message;
        this.showNegativeButton = showNegativeButton;
        this.strPositive = strPositive;
        this.requestCode = requestCode;
        this.listener = listener;
    }

    public AlertDialog(Context context, String title, String message,
                       String strPositive, String strNegative, int requestCode, OnDialogButtonClickListener listener) {
        super(context, R.style.MyDialog);

        this.context = context;
        this.title = title;
        this.message = message;
        this.strPositive = strPositive;
        this.strNegative = strNegative;
        this.requestCode = requestCode;
        this.listener = listener;
    }

    private TextView tvTitle;
    private TextView tvMessage;
    private Button btnPositive;
    private Button btnNegative;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alert_dialog);
        setCanceledOnTouchOutside(true);

        tvTitle = (TextView) findViewById(R.id.tvAlertDialogTitle);
        tvMessage = (TextView) findViewById(R.id.tvAlertDialogMessage);
        btnPositive = (Button) findViewById(R.id.btnAlertDialogPositive);
        btnNegative = (Button) findViewById(R.id.btnAlertDialogNegative);

        tvTitle.setVisibility(TextUtils.isEmpty(title) == false ? View.VISIBLE : View.GONE);
        tvTitle.setText(title);

        if (TextUtils.isEmpty(strPositive)) {
            btnPositive.setText("");
        }
        btnPositive.setOnClickListener(this);

        if (showNegativeButton) {
            if (TextUtils.isEmpty(strNegative)) {
                btnNegative.setText("");
            }
            btnNegative.setOnClickListener(this);
        } else {
            btnNegative.setVisibility(View.GONE);
        }

        tvMessage.setText(message);
    }

    @Override
    public void onClick(final View v) {
        if (v.getId() == R.id.btnAlertDialogPositive) {
            listener.onDialogButtonClick(requestCode, true);
        } else if (v.getId() == R.id.btnAlertDialogNegative) {
            listener.onDialogButtonClick(requestCode, false);
        }

        dismiss();
    }

}

