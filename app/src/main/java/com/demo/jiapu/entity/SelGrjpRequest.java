package com.demo.jiapu.entity;

import com.demo.jiapu.base.MapParamsRequest;



public class SelGrjpRequest extends MapParamsRequest {

    public String userId;



    @Override
    protected void putParams() {
        params.put("uid", userId);

    }

}
