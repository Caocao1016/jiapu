package com.yskj.daishuguan.entity;

import com.yskj.daishuguan.base.MapParamsRequest;

/**
 * CaoPengFei
 * 2018/12/27
 *
 * @ClassName: AuInfoRequest
 * @Description:
 */

public class MemberSmsRequest extends MapParamsRequest {

    public String paramCode;
    public String userid;
    public String token;


    @Override
    protected void putParams() {
        params.put("paramCode", paramCode);
        params.put("userid", userid);
        params.put("token", token);

    }
}
