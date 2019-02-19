package com.yskj.daishuguan.entity.request;

import com.yskj.daishuguan.base.MapParamsRequest;

/**
 * CaoPengFei
 * 2018/12/27
 *
 * @ClassName: AuInfoRequest
 * @Description:
 */

public class HuanKuanRequest extends MapParamsRequest {

    public String type;
    public String token;
    public String userid;
    public  int page ;
    public  int limit ;


    @Override
    protected void putParams() {
        params.put("type", type);
        params.put("token", token);
        params.put("userid", userid);
        params.put("page", page);
        params.put("limit", limit);

    }
}
