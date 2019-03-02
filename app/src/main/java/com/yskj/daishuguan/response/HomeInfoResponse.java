package com.yskj.daishuguan.response;

/**
 * CaoPengFei
 * 2019/2/13
 *
 * @ClassName: HomeInfoResponse
 * @Description:
 */

public class HomeInfoResponse {

    private String assessValue ;
    private int auditCreditLimit ;
    private String productintroduce ;
    private boolean authJudge ;
    private boolean creditJudge ;
    private boolean loanJudge ;
    private boolean pwd ;
    private boolean isReloan ;
    private int isMember ;

    public boolean isReloan() {
        return isReloan;
    }

    public void setReloan(boolean reloan) {
        isReloan = reloan;
    }

    public int getIsMember() {
        return isMember;
    }

    public void setIsMember(int isMember) {
        this.isMember = isMember;
    }

    public String getAssessValue() {
        return assessValue;
    }

    public void setAssessValue(String assessValue) {
        this.assessValue = assessValue;
    }

    public int getAuditCreditLimit() {
        return auditCreditLimit;
    }

    public void setAuditCreditLimit(int auditCreditLimit) {
        this.auditCreditLimit = auditCreditLimit;
    }

    public String getProductintroduce() {
        return productintroduce;
    }

    public void setProductintroduce(String productintroduce) {
        this.productintroduce = productintroduce;
    }

    public boolean isAuthJudge() {
        return authJudge;
    }

    public void setAuthJudge(boolean authJudge) {
        this.authJudge = authJudge;
    }

    public boolean isCreditJudge() {
        return creditJudge;
    }

    public void setCreditJudge(boolean creditJudge) {
        this.creditJudge = creditJudge;
    }

    public boolean isLoanJudge() {
        return loanJudge;
    }

    public void setLoanJudge(boolean loanJudge) {
        this.loanJudge = loanJudge;
    }

    public boolean isPwd() {
        return pwd;
    }

    public void setPwd(boolean pwd) {
        this.pwd = pwd;
    }
}
