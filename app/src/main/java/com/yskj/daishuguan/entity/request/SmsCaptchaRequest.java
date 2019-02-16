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

public class SmsCaptchaRequest extends MapParamsRequest {

    public String mobileno;   //客户端设备ID
    public String captcha;   //客户端设备ID
    public String token;   //客户端设备ID
    public String reqType;  //请求发送的短信类型。register：注册；reset：重置密码；login：登陆；


    @Override
    protected void putParams() {

        if (!StringUtil.isEmpty(mobileno)){
            params.put("mobileno", mobileno);
        }

        if (!StringUtil.isEmpty(captcha)){
            params.put("captcha", captcha);
        } if (!StringUtil.isEmpty(token)){
            params.put("token", token);
        }

        params.put("reqtype", reqType);

    }
}
