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

public class OCRRequest extends MapParamsRequest {

    public String userid;
    public String token;
    public String merchantCode;


    @Override
    protected void putParams() {
        params.put("userid", userid);

       if (!StringUtil.isEmpty(merchantCode)){
           params.put("merchantCode", merchantCode);
       }
   if (!StringUtil.isEmpty(token)){
       params.put("token", token);
       }

    }
}
