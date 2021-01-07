package com.demo.jiapu.entity;

import com.demo.jiapu.base.MapParamsRequest;

public class ListRequest extends MapParamsRequest {

    public int page;

    @Override
    protected void putParams() {
        params.put("page", page);
    }
}