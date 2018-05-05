package com.ifenqu.app.view.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.RegexUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.ifenqu.app.R;
import com.ifenqu.app.app.IfenquApplication;
import com.ifenqu.app.http.HttpConstant;
import com.ifenqu.app.http.HttpRequest;
import com.ifenqu.app.http.response.OnHttpResponseListener;
import com.ifenqu.app.model.AddressBusiness;
import com.ifenqu.app.model.CityModel;
import com.ifenqu.app.model.ProvinceModel;
import com.ifenqu.app.model.SuburbModel;
import com.ifenqu.app.util.NetworkUtil;
import com.ifenqu.app.view.BaseActivity;
import com.ifenqu.app.widget.CommonTitleView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 编辑收货人地址
 */
public class AddressModificationActivity extends BaseActivity implements OnHttpResponseListener {

    private Thread thread;
    private ArrayList<ProvinceModel> options1Items;
    private ArrayList<ArrayList<String>> options2Items = new ArrayList<>();
    private ArrayList<ArrayList<ArrayList<String>>> options3Items = new ArrayList<>();

    private static final int MSG_LOAD_DATA = 0x0001;
    private static final int MSG_LOAD_SUCCESS = 0x0002;
    private static final int MSG_LOAD_FAILED = 0x0003;
    private ProvinceModel proviceName;
    private CityModel cityName;
    private SuburbModel suburbName;

    private static String KEY_CACHED_ADDRESS = "KEY_CACHED_ADDRESS";

    private AddressBusiness business;

    @BindView(R.id.et_name)
    EditText et_name;

    @BindView(R.id.et_phone)
    EditText et_phone;

    @BindView(R.id.tv_specific_place)
    EditText tv_specific_place;

    @BindView(R.id.comm_title)
    CommonTitleView comm_title;

    @BindView(R.id.et_area)
    TextView et_area;

    public static void start(Activity context, AddressBusiness business,int requestCde) {
        if (context == null) return;
        Intent intent = new Intent(context, AddressModificationActivity.class);
        intent.putExtra(KEY_CACHED_ADDRESS,business);
        context.startActivityForResult(intent, requestCde);
    }

    public static void start(Activity context,int requestCde) {
        if (context == null) return;
        Intent intent = new Intent(context, AddressModificationActivity.class);
        context.startActivityForResult(intent, requestCde);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address);
        ButterKnife.bind(this);

        parsePlaces();
        comm_title.setTitle(R.string.address_title);

        setDefaultAddress();
    }

    private void setDefaultAddress() {
        business = (AddressBusiness) getIntent().getSerializableExtra(KEY_CACHED_ADDRESS);
        if (business != null){
            et_area.setText(String.format(getResources().getString(R.string.address_specific),business.getProvince().getName(),business.getCity().getName(),business.getArea().getName()));
            et_name.setText(business.getName());
            et_phone.setText(business.getPhone());
            tv_specific_place.setText(business.getSpecificAddress());

            proviceName = business.getProvince();
            cityName = business.getCity();
            suburbName = business.getArea();
        }
    }

    private void parsePlaces() {
        if (thread == null) {//如果已创建就不再重新创建子线程了

            thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    // 子线程中解析省市区数据
                    initJsonData();
                }
            });
            thread.start();
        }
    }

    @OnClick(R.id.tv_save_and_use)
    public void saveAndUse(View view) {
        if (!NetworkUtil.checkoutInternet()) return;
        String posterName = et_name.getText().toString();
        if (TextUtils.isEmpty(posterName)) {
            ToastUtils.showShort("请填写收件姓名");
            return;
        }
        String phoneStr = et_phone.getText().toString();
        if (!RegexUtils.isMobileExact(phoneStr)) {
            ToastUtils.showShort(IfenquApplication.getInstance().getResources().getString(R.string.toast_check_phone_number));
            return;
        }

        String areaStr = et_area.getText().toString();
        if (TextUtils.isEmpty(areaStr)) {
            ToastUtils.showShort("请选择城市");
            return;
        }

        String specificStr = tv_specific_place.getText().toString();
        if (TextUtils.isEmpty(specificStr)) {
            ToastUtils.showShort("请填写详细地址");
            return;
        }
        HttpRequest.saveAddress(proviceName.getName(), cityName.getName(), suburbName.getName(), specificStr,posterName, phoneStr, HttpConstant.URL_SAVE_ADDRESS_INDEX, this);
    }

    @OnClick(R.id.rl_choose_location)
    public void onClickChooseLocation(View view) {
        if (isLoaded) {
            hideKeyboard();
            showPickerView();
        }
    }

    private void hideKeyboard(){
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(et_area.getWindowToken(), 0);
    }

    private OnOptionsSelectListener listener = new OnOptionsSelectListener() {
        @Override
        public void onOptionsSelect(int options1, int options2, int options3, View v) {
//            返回的分别是三个级别的选中位置
            proviceName = options1Items.get(options1);
            cityName = proviceName.getSub().get(options2);
            suburbName = cityName.getSub().get(options3);

            et_area.setText(String.format(getResources().getString(R.string.address_specific),proviceName.getName(),cityName.getName(),suburbName.getName()));

        }
    };

    private void showPickerView() {// 弹出选择器

        OptionsPickerView pvOptions = new OptionsPickerBuilder(AddressModificationActivity.this, listener).setTitleText("城市选择")
                .setDividerColor(Color.BLACK)
                .setTextColorCenter(Color.BLACK) //设置选中项文字颜色
                .setContentTextSize(20)
                .build();

        /*pvOptions.setPicker(options1Items);//一级选择器
        pvOptions.setPicker(options1Items, options2Items);//二级选择器*/
        pvOptions.setPicker(options1Items, options2Items, options3Items);//三级选择器
        pvOptions.show();
    }

    private void initJsonData() {//解析数据

        /**
         * 注意：assets 目录下的Json文件仅供参考，实际使用可自行替换文件
         * 关键逻辑在于循环体
         *
         * */
        String JsonData = getJson(this, "openArea.json");//获取assets目录下的json文件数据

        ArrayList<ProvinceModel> jsonBean = parseData(JsonData);//用Gson 转成实体

        /**
         * 添加省份数据
         *
         * 注意：如果是添加的JavaBean实体，则实体类需要实现 IPickerViewData 接口，
         * PickerView会通过getPickerViewText方法获取字符串显示出来。
         */
        options1Items = jsonBean;

        for (int i = 0; i < jsonBean.size(); i++) {//遍历省份
            ArrayList<String> CityList = new ArrayList<>();//该省的城市列表（第二级）
            ArrayList<ArrayList<String>> Province_AreaList = new ArrayList<>();//该省的所有地区列表（第三极）

            for (int c = 0; c < jsonBean.get(i).getSub().size(); c++) {//遍历该省份的所有城市
                String CityName = jsonBean.get(i).getSub().get(c).getName();
                CityList.add(CityName);//添加城市
                ArrayList<String> City_AreaList = new ArrayList<>();//该城市的所有地区列表

                //如果无地区数据，建议添加空字符串，防止数据为null 导致三个选项长度不匹配造成崩溃
                if (jsonBean.get(i).getSub().get(c).getSub() == null
                        || jsonBean.get(i).getSub().get(c).getSub().size() == 0) {
                    City_AreaList.add("");
                } else {
                    List<SuburbModel> suburbModelList = jsonBean.get(i).getSub().get(c).getSub();
                    int count = suburbModelList.size();
                    for (int q = 0; q < count; q++) {
                        City_AreaList.add(suburbModelList.get(q).getName());
                    }
                }


                Province_AreaList.add(City_AreaList);//添加该省所有地区数据
            }

            /**
             * 添加城市数据
             */
            options2Items.add(CityList);

            /**
             * 添加地区数据
             */
            options3Items.add(Province_AreaList);
        }

        mHandler.sendEmptyMessage(MSG_LOAD_SUCCESS);

    }

    public String getJson(Context context, String fileName) {

        StringBuilder stringBuilder = new StringBuilder();
        try {
            AssetManager assetManager = context.getAssets();
            BufferedReader bf = new BufferedReader(new InputStreamReader(
                    assetManager.open(fileName)));
            String line;
            while ((line = bf.readLine()) != null) {
                stringBuilder.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }

    public ArrayList<ProvinceModel> parseData(String result) {//Gson 解析
        ArrayList<ProvinceModel> detail = new ArrayList<>();
        try {
            JSONArray data = new JSONArray(result);
            Gson gson = new Gson();
            for (int i = 0; i < data.length(); i++) {
                ProvinceModel entity = gson.fromJson(data.optJSONObject(i).toString(), ProvinceModel.class);
                detail.add(entity);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return detail;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mHandler != null) {
            mHandler.removeCallbacksAndMessages(null);
        }
    }


    private boolean isLoaded;
    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_LOAD_DATA:
                    if (thread == null) {//如果已创建就不再重新创建子线程了

                        thread = new Thread(new Runnable() {
                            @Override
                            public void run() {
                                // 子线程中解析省市区数据
                                initJsonData();
                            }
                        });
                        thread.start();
                    }
                    break;

                case MSG_LOAD_SUCCESS:
                    isLoaded = true;
                    break;

                case MSG_LOAD_FAILED:
                    break;
            }
        }
    };

    @Override
    public void onHttpResponse(int requestCode, String resultJson, Exception e) {
        if (requestCode == HttpConstant.URL_SAVE_ADDRESS_INDEX) {
            if (TextUtils.isEmpty(resultJson)) return;
            Intent intent = new Intent();
            AddressBusiness addressBusiness = new AddressBusiness();
            addressBusiness.setProvince(proviceName);
            addressBusiness.setArea(suburbName);
            addressBusiness.setCity(cityName);
            addressBusiness.setName(et_name.getText().toString());
            addressBusiness.setPhone(et_phone.getText().toString());
            int addressCode = 0;
            try {
                JSONObject object = new JSONObject(resultJson);
                addressCode = (int) object.get("data");
            } catch (JSONException e1) {
                e1.printStackTrace();
            }
            addressBusiness.setAddressCode(addressCode+"");
            LogUtils.d("addressCode - "+addressCode);
            addressBusiness.setSpecificAddress(tv_specific_place.getText().toString());
            intent.putExtra("zhunafeng",addressBusiness);
            setResult(RESULT_OK, intent);
            finish();
        }
    }
}
