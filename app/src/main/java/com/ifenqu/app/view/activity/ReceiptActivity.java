package com.ifenqu.app.view.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import com.blankj.utilcode.util.ToastUtils;
import com.ifenqu.app.R;
import com.ifenqu.app.model.ReceiptBusiness;
import com.ifenqu.app.view.BaseActivity;
import com.ifenqu.app.widget.CommonTitleView;
import com.ifenqu.app.widget.ReceiptAlertDialog;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ReceiptActivity extends BaseActivity {

    public static final String KEY_RECEIPTT = "KEY_RECEIPTT";
    @BindView(R.id.ll_company_parent)
    ViewGroup ll_company_parent;
    @BindView(R.id.iv_company_img)
    ImageView iv_company_img;

    @BindView(R.id.iv_individual_img)
    ImageView iv_individual_img;

    @BindView(R.id.iv_clear)
    ImageView iv_clear;

    @BindView(R.id.et_receipt_company_name)
    EditText et_receipt_company_name;

    @BindView(R.id.et_receipt_company_tax_num)
    EditText et_receipt_company_tax_num;

    @BindView(R.id.comm_title)
    CommonTitleView comm_title;

    private boolean isIndividual = true;
    public static void start(Activity context, int requestCde) {
        if (context == null) return;
        Intent intent = new Intent(context, ReceiptActivity.class);
        context.startActivityForResult(intent,requestCde);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receipt);
        ButterKnife.bind(this);
        comm_title.setTitle(R.string.receipt_info);
        addRightViewTitle();
    }

    private void addRightViewTitle() {
        View view = LayoutInflater.from(this).inflate(R.layout.right_view_item1,comm_title.rightViewParentView(),false);
        comm_title.addRightView(view,new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                new ReceiptAlertDialog(ReceiptActivity.this).show();
            }
        });
    }

    @OnClick(R.id.tv_receipt_ok)
    public void onClickReceiptOk(View view){
        String companyName = "",taxName = "";
        if (!isIndividual){
            companyName = et_receipt_company_name.getText().toString();
            if (TextUtils.isEmpty(companyName)){
                ToastUtils.showShort("请填写单位名称");
                return;
            }
            taxName = et_receipt_company_tax_num.getText().toString();
            if (TextUtils.isEmpty(taxName)){
                ToastUtils.showShort("请填写纳税人单号");
                return;
            }
        }
        ReceiptBusiness receiptBusiness = new ReceiptBusiness();
        receiptBusiness.setIndividual(isIndividual);
        receiptBusiness.setCompanyTitle(companyName);
        receiptBusiness.setTax(taxName);
        Intent intent = new Intent();
        intent.putExtra(KEY_RECEIPTT,receiptBusiness);
        setResult(RESULT_OK,intent);
        finish();
    }

    @OnClick(R.id.ll_company)
    public void onClickCompanyReceipt(View view){
        isIndividual = false;
        ll_company_parent.setVisibility(View.VISIBLE);
        iv_company_img.setImageResource(R.drawable.tick_icon);
        iv_individual_img.setImageResource(R.drawable.receipt_bg);
    }

    @OnClick(R.id.ll_individual)
    public void onClickIndividualReceipt(View view){
        isIndividual = true;
        ll_company_parent.setVisibility(View.GONE);
        iv_company_img.setImageResource(R.drawable.receipt_bg);
        iv_individual_img.setImageResource(R.drawable.tick_icon);

    }

    @OnClick(R.id.iv_clear)
    public void onClickClear(View view){
        String companyName = et_receipt_company_name.getText().toString();
        if (TextUtils.isEmpty(companyName)){
            return;
        }
        et_receipt_company_name.setText("");
    }
}
