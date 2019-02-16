package com.yskj.daishuguan.response;

/**
 * CaoPengFei
 * 2018/12/28
 *
 * @ClassName: BlackListItemResponse
 * @Description:
 */

public class BlackListItemResponse {

    private String UNIT_TYPE ;
    private String OUT_UNIT_CODE ;
    private String CONTENT ;
    private String TYPE ;

    public String getUNIT_TYPE() {
        return UNIT_TYPE;
    }

    public void setUNIT_TYPE(String UNIT_TYPE) {
        this.UNIT_TYPE = UNIT_TYPE;
    }

    public String getOUT_UNIT_CODE() {
        return OUT_UNIT_CODE;
    }

    public void setOUT_UNIT_CODE(String OUT_UNIT_CODE) {
        this.OUT_UNIT_CODE = OUT_UNIT_CODE;
    }

    public String getCONTENT() {
        return CONTENT;
    }

    public void setCONTENT(String CONTENT) {
        this.CONTENT = CONTENT;
    }

    public String getTYPE() {
        return TYPE;
    }

    public void setTYPE(String TYPE) {
        this.TYPE = TYPE;
    }
}
