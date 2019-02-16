package com.yskj.daishuguan.entity.request;

import com.yskj.daishuguan.base.MapParamsRequest;
import com.yskj.daishuguan.util.StringUtil;

/**
 * CaoPengFei
 * 2018/12/7
 *
 * @ClassName: AuthorRequest
 * @Description:
 */

public class ManagementListRequest extends MapParamsRequest {

    public String type;
    public String token;
    public String userid;
    public int page;
    public int limit;
    public String mobileno;



    @Override
    protected void putParams() {
        if (!StringUtil.isEmpty(type)){
            params.put("type", type);
        }
        if (!StringUtil.isEmpty(mobileno)){
            params.put("mobileno", mobileno);
        }
         if (!StringUtil.isEmpty(token)){
            params.put("token", token);
        }
         if (!StringUtil.isEmpty(userid)){
            params.put("userid", userid);
        }

        params.put("page", page);
        params.put("limit", limit);


    }
}
