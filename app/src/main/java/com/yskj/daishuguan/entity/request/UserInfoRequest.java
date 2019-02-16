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

public class UserInfoRequest extends MapParamsRequest {

    public String token;   //客户端设备ID
    public String mobileno;   //客户端设备ID
    public String userid;   //客户端设备ID

    @Override
    protected void putParams() {
        if (!StringUtil.isEmpty(token)){
            params.put("token", token);
        }
       if (!StringUtil.isEmpty(mobileno)){
            params.put("mobileno", mobileno);
        }
       if (!StringUtil.isEmpty(userid)){
            params.put("userid", userid);
        }


    }
}
