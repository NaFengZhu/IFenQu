package com.ifenqu.app.http.response;

import com.ifenqu.app.http.Response;
import com.ifenqu.app.model.SysOrderAgentModel;

public class OrderListResponse extends Response {
    private SysOrderAgentModel data;

    public SysOrderAgentModel getData() {
        return data;
    }

    public void setData(SysOrderAgentModel data) {

        this.data = data;
    }

    @Override
    public boolean checkDataValidate() {
        if (!super.checkDataValidate())return false;
        if (data == null)return false;
        return true;
    }
}
