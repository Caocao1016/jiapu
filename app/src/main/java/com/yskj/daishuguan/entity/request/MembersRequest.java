package com.yskj.daishuguan.entity.request;

import com.yskj.daishuguan.base.MapParamsRequest;
import com.yskj.daishuguan.util.StringUtil;

/**
 * CaoPengFei
 * 2018/12/27
 *
 * @ClassName: AuInfoRequest
 * @Description:
 */

public class MembersRequest extends MapParamsRequest {

    public String userId;
    public String menberPrice;  // 借款金额
    public String menberRate;   //费率
    public String couponIds;    //优惠劵  使用逗号拼接

    @Override
    protected void putParams() {
        params.put("userid", userId);
        params.put("menberRate", menberRate);
        params.put("menberPrice", menberPrice);
        if (!StringUtil.isEmpty(couponIds)){
            params.put("couponIds", couponIds);
        }


    }
}
