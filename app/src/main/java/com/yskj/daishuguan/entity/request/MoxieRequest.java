package com.yskj.daishuguan.entity.request;

import com.yskj.daishuguan.base.MapParamsRequest;

/**
 * CaoPengFei
 * 2018/12/7
 *
 * @ClassName: AuthorRequest
 * @Description:
 */

public class MoxieRequest extends MapParamsRequest {

    public String taskId;
    public String userid;



    @Override
    protected void putParams() {
        params.put("taskId", taskId);
        params.put("userid", userid);

    }
}
