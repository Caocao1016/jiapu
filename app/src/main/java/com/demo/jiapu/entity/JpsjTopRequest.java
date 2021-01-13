package com.demo.jiapu.entity;

import com.demo.jiapu.base.MapParamsRequest;

public class JpsjTopRequest extends MapParamsRequest {
    public long id;

    @Override
    protected void putParams() {
        params.put("id", id);
        params.put("zhi_ding", 1);
    }
}
