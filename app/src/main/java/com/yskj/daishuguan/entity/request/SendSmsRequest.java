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

public class SendSmsRequest extends MapParamsRequest {

    public String token;
    public String userid;
    public String mobileno;
    public String realname;
    public String idcardno;
    public String cardno;
    public String bankname;
    public String bankcode;
    public String validatecode;
    public String requestno;
    public String mchntssn;


    @Override
    protected void putParams() {
        if (!StringUtil.isEmpty(token)) {
            params.put("token", token);
        }
        if (!StringUtil.isEmpty(validatecode)) {
            params.put("validatecode", validatecode);
        }
        if (!StringUtil.isEmpty(requestno)) {
            params.put("requestno", requestno);
        }
        if (!StringUtil.isEmpty(userid)) {
            params.put("userid", userid);
        }
        if (!StringUtil.isEmpty(mchntssn)) {
            params.put("mchntssn", mchntssn);
        }
        if (!StringUtil.isEmpty(mobileno)) {
            params.put("mobileno", mobileno);
        }
        if (!StringUtil.isEmpty(realname)) {
            params.put("realname", realname);
        }
        if (!StringUtil.isEmpty(idcardno)) {
            params.put("idcardno", idcardno);
        }
        if (!StringUtil.isEmpty(bankcode)) {
            params.put("bankcode", bankcode);
        }
        if (!StringUtil.isEmpty(bankname)) {
            params.put("bankname", bankname);
        }
        if (!StringUtil.isEmpty(cardno)) {
            params.put("cardno", cardno);
        }

    }
}
