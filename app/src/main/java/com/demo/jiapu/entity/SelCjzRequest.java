package com.demo.jiapu.entity;

import android.text.TextUtils;

import com.demo.jiapu.base.MapParamsRequest;

public class SelCjzRequest extends MapParamsRequest {
    public long tx_userid;

    @Override
    protected void putParams() {
        params.put("tx_userid", tx_userid);
    }

}
