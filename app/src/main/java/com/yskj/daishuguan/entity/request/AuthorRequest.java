package com.yskj.daishuguan.entity.request;

import com.yskj.daishuguan.base.MapParamsRequest;

/**
 * CaoPengFei
 * 2018/12/7
 *
 * @ClassName: AuthorRequest
 * @Description:
 */

public class AuthorRequest extends MapParamsRequest {

    public String userId;
    public int page;
    public int limit;
    public String status;   //授权状态;0:未授权;1:已经授权


    @Override
    protected void putParams() {
        params.put("userId", userId);
        params.put("page", page);
        params.put("limit", limit);
        params.put("status", status);

    }
}
