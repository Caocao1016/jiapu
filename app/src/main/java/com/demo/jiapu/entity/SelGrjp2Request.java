package com.demo.jiapu.entity;

import com.demo.jiapu.base.MapParamsRequest;


public class SelGrjp2Request extends MapParamsRequest {

    public long list_id;

    @Override
    protected void putParams() {
        params.put("list_id", list_id);

    }

}
