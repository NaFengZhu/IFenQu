package com.ifenqu.app.model.eventbusmodel;

import com.ifenqu.app.model.CouponModel;

public class CouponEvent {
    private CouponModel couponModel;

    public CouponEvent(CouponModel couponModel) {
        this.couponModel = couponModel;
    }

    public CouponModel getData(){
        return couponModel;
    }
}
