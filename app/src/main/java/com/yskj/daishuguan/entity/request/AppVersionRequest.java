package com.yskj.daishuguan.entity.request;

import com.yskj.daishuguan.base.MapParamsRequest;

public class AppVersionRequest extends MapParamsRequest {

    public String appType;
    public String disVersion;

    @Override
    protected void putParams() {
        params.put("apptype", appType);
        params.put("disversion", disVersion);


    }
}
