package com.yskj.daishuguan.response;

import java.util.List;

/**
 * CaoPengFei
 * 2018/11/29
 *
 * @ClassName: AppVersionResponse
 * @Description:
 */

public class CardSmsResponse {

    private String requestno;
    private String mchntssn;


    public String getRequestno() {
        return requestno;
    }

    public void setRequestno(String requestno) {
        this.requestno = requestno;
    }

    public String getMchntssn() {
        return mchntssn;
    }

    public void setMchntssn(String mchntssn) {
        this.mchntssn = mchntssn;
    }
}
