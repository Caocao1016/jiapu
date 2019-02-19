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

public class CreditStartRequest extends MapParamsRequest {

    public String userid;
    public String token;
    public String productNo;
    public String locaddress;
    public String locgps;
    public String autographPicture;


    @Override
    protected void putParams() {
        params.put("token", token);
        params.put("userid", userid);
        params.put("productNo", productNo);

        if (!StringUtil.isEmpty(locaddress)){
            params.put("locaddress", locaddress);
        } if (!StringUtil.isEmpty(locgps)){
            params.put("locgps", locgps);
        }
if (!StringUtil.isEmpty(autographPicture)){
            params.put("autographPicture", autographPicture);
        }


    }
}
