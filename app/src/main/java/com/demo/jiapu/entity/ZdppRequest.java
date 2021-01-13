package com.demo.jiapu.entity;

import com.demo.jiapu.base.MapParamsRequest;

public class ZdppRequest extends MapParamsRequest {

    public long list_id;

    @Override
    protected void putParams() {
        params.put("list_id", list_id);
    }
}