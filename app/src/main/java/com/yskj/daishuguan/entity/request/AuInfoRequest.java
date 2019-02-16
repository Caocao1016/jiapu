package com.yskj.daishuguan.entity.request;

import com.yskj.daishuguan.base.MapParamsRequest;

/**
 * CaoPengFei
 * 2018/12/27
 *
 * @ClassName: AuInfoRequest
 * @Description:
 */

public class AuInfoRequest  extends MapParamsRequest {

    public String userId;


    @Override
    protected void putParams() {
        params.put("userId", userId);

    }
}
