package com.demo.jiapu.entity;

import com.demo.jiapu.base.MapParamsRequest;

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
    public String type;


    @Override
    protected void putParams() {
        params.put("paramCode", paramCode);
        params.put("userid", userid);
        params.put("token", token);
        params.put("type", type);

    }
}
