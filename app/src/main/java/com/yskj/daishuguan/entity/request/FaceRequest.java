package com.yskj.daishuguan.entity.request;

import com.yskj.daishuguan.base.MapParamsRequest;

/**
 * CaoPengFei
 * 2018/12/7
 *
 * @ClassName: AuthorRequest
 * @Description:
 */

public class FaceRequest extends MapParamsRequest {

    public String userId;
    public String deviceCode;
    public String livenessId;


    @Override
    protected void putParams() {
        params.put("userId", userId);
        params.put("livenessId", livenessId);
        params.put("deviceCode", deviceCode);

    }
}
