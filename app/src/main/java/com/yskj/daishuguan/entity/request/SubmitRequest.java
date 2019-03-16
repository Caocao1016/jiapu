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

public class SubmitRequest extends MapParamsRequest {

    public String userId;
    public String token;
    public String mobileno;
    public String cycle;
    public int loanAmount;
    public String productNo;
    public String osType;
    public String locgps;
    public String locaddress;
    public String loanPurpose;
    public String isSign;

    @Override
    protected void putParams() {
        if (!StringUtil.isEmpty(userId)) {
            params.put("userid", userId);
        } if (!StringUtil.isEmpty(isSign)) {
            params.put("isSign", isSign);
        }


        if (!StringUtil.isEmpty(token)) {
            params.put("token", token);
        }


        if (!StringUtil.isEmpty(mobileno)) {
            params.put("mobileno", mobileno);
        }

        if (!StringUtil.isEmpty(cycle)) {
            params.put("cycle", cycle);
        }

            params.put("loanAmount", loanAmount);


        if (!StringUtil.isEmpty(productNo)) {
            params.put("productNo", productNo);
        }


        if (!StringUtil.isEmpty(osType)) {
            params.put("osType", osType);
        }


        if (!StringUtil.isEmpty(locgps)) {
            params.put("locgps", locgps);
        }

        if (!StringUtil.isEmpty(loanPurpose)) {
            params.put("loanPurpose", loanPurpose);
        }


        if (!StringUtil.isEmpty(locaddress)) {
            params.put("locaddress", locaddress);
        }


    }
}
