package com.yskj.daishuguan.entity.request;

import com.yskj.daishuguan.base.MapParamsRequest;

/**
 * CaoPengFei
 * 2018/12/7
 *
 * @ClassName: AuthorRequest
 * @Description:
 */

public class ShareRequest extends MapParamsRequest {

    public String userid;
    public int page;
    public int limit;
    public String merchantCode;


    @Override
    protected void putParams() {
        params.put("userid", userid);
        params.put("page", page);
        params.put("limit", limit);
        params.put("merchantCode", merchantCode);

    }
}
