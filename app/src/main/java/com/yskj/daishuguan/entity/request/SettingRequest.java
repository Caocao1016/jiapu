package com.yskj.daishuguan.entity.request;

import com.yskj.daishuguan.base.MapParamsRequest;
import com.yskj.daishuguan.util.StringUtil;

/**
 * CaoPengFei
 * 2018/11/29
 *
 * @ClassName: CommonDataRequest
 * @Description:
 */

public class SettingRequest extends MapParamsRequest {

    public String token;  //客户端设备编号
    public String mobileno;    //用户手机号
    public String userid;    //用户手机号


    @Override
    protected void putParams() {
        params.put("userid", userid);
        params.put("token", token);
        params.put("mobileno", mobileno);

    }
}
