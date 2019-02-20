package com.yskj.daishuguan.entity.request;

import com.yskj.daishuguan.base.MapParamsRequest;

/**
 * CaoPengFei
 * 2018/12/27
 *
 * @ClassName: AuInfoRequest
 * @Description:
 */

public class DetailRequest extends MapParamsRequest {

    public String token;
    public String mobileno;
    public String userid;
    public String repaymentOrderNo;
    public String stageNum;
    public String extendFees;
    public String extendDays;
    public String extendOrderNo;
    public String extendTimes;



    @Override
    protected void putParams() {
        params.put("token", token);
        params.put("mobileno", mobileno);
        params.put("userid", userid);
        params.put("repaymentOrderNo", repaymentOrderNo);
        params.put("stageNum", stageNum);
        params.put("extendFees", extendFees);
        params.put("extendDays", extendDays);
        params.put("extendOrderNo", extendOrderNo);
        params.put("extendTimes", extendTimes);

    }
}
