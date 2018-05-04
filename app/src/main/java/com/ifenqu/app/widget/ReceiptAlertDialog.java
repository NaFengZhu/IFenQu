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
import android.view.View;
import android.widget.TextView;

import com.ifenqu.app.R;

/**
 * 通用对话框类
 *
 * @use new AlertDialog(...).show();
 */
public class ReceiptAlertDialog extends Dialog implements View.OnClickListener {

    public ReceiptAlertDialog(Context context) {
        super(context, R.style.MyDialog);

    }


    private TextView tv_know;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.receipt_alert_dialog);
        setCanceledOnTouchOutside(true);

        tv_know = (TextView) findViewById(R.id.tv_know);
        tv_know.setOnClickListener(this);
    }

    @Override
    public void onClick(final View v) {
        dismiss();
    }

}

