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

public class BannerRequest extends MapParamsRequest {

    public String userid;
    public String token;
    public String showType;
    public String cycle;
    public String mobileno;
    public String type;


    @Override
    protected void putParams() {


        if (!StringUtil.isEmpty(showType)) {
            params.put("showType", showType);
        }
        if (!StringUtil.isEmpty(mobileno)) {
            params.put("mobileno", mobileno);
        }

        if (!StringUtil.isEmpty(token)) {
            params.put("token", token);
        }if (!StringUtil.isEmpty(type)) {
            params.put("type", type);
        }
        if (!StringUtil.isEmpty(userid)) {
            params.put("userid", userid);
        }
        if (!StringUtil.isEmpty(cycle)) {
            params.put("cycle", cycle);
        }
    }
}
