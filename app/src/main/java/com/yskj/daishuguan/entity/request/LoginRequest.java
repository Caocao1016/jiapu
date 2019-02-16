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

public class LoginRequest extends MapParamsRequest {

    public String verifycode;  //类型
    public String mobileno;     //用户手机号
    public String reqtype;     //用户手机号
    public String locaddress;     //用户手机号
    public String locgps;     //用户手机号
    public String invitationcode;     //用户手机号
    public String token;     //用户手机号

    @Override
    protected void putParams() {
        params.put("verifycode", verifycode);
        params.put("mobileno", mobileno);
        params.put("reqtype", reqtype);
        if (!StringUtil.isEmpty(locaddress)) {
            params.put("locaddress", locaddress);
        }
        if (!StringUtil.isEmpty(invitationcode)) {
            params.put("invitationcode", invitationcode);
        }

        if (!StringUtil.isEmpty(locgps)) {
            params.put("locgps", locgps);
        }
        if (!StringUtil.isEmpty(token)) {
            params.put("token", token);
        }

    }
}
