package com.demo.jiapu.entity;

import com.demo.jiapu.base.MapParamsRequest;

public class SelSjjpRequest extends MapParamsRequest {
    private String list_id;

    @Override
    protected void putParams() {
        params.put("list_id", list_id);
    }
}
