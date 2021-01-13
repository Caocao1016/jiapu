package com.demo.jiapu.entity;

import com.demo.jiapu.base.MapParamsRequest;

public class ZdppRequest extends MapParamsRequest {

    public String list_id;

    @Override
    protected void putParams() {
        params.put("list_id", list_id);
    }
}