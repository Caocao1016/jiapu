package com.yskj.daishuguan.response;

/**
 * CaoPengFei
 * 2019/2/16
 *
 * @ClassName: ShareResponse
 * @Description:
 */

public class ShareResponse {
    private String content ;
    private String remark ;
    private String bonusRemark ;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getBonusRemark() {
        return bonusRemark;
    }

    public void setBonusRemark(String bonusRemark) {
        this.bonusRemark = bonusRemark;
    }
}
