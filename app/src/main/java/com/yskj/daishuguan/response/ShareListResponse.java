package com.yskj.daishuguan.response;

import java.util.List;

/**
 * CaoPengFei
 * 2018/11/29
 *
 * @ClassName: AppVersionResponse
 * @Description:
 */

public class ShareListResponse {
    private int totalCount;
    private int pageSize;
    private int totalPage;
    private int currPage;
    private List<ListBean> list;

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public int getCurrPage() {
        return currPage;
    }

    public void setCurrPage(int currPage) {
        this.currPage = currPage;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean {

//"id": 8,
//        "userId": "5230B75ECEE44DE3B47274F3ACDFDD6F",
//        "merchantCode": "yxTOxX",
//        "loginName": "13508239713",被邀请人
//        "loginPasswd": "",
//                "tradePasswd": "",
//                "mobileNo": "13508239713",
//                "realName": "朱积华",
//                "idcardNo": "510422199608226616",
//                "isCertified": 1,
//                "isValid": 1,
//                "regTime": "2018-10-24 20:24:57",邀请时间
//        "regSource": "WAP",
//                "terminalType": "android",
//                "appStore": "005",
//                "referralCode": "WIa217xY",
//                "invitationCode": "byxXAV8Y",
//                "regProtocolNo": "ZCXY81540383896816",
//                "infoProtocolNo": "XXSQXY81540383896816",
//                "promotionChannel": "005",
//                "isFirstLoan": 0,
//                "lastLoginTime": "2018-10-25 15:48:45",
//                "headImg": "",
//                "esignId": "",
//                "sealData": "",
//                "loanTime": 0,
//                "firstLoginTime": "2018-10-24 22:25:31",
//                "inviteStatus": 0   邀请记录状态，o:注册，1：已借款


        private String userId;
        private String merchantCode;
        private String loginName;
        private String loginPasswd;
        private String tradePasswd;
        private String mobileNo;
        private String realName;
        private String idcardNo;
        private int isCertified;
        private int isValid;
        private String regTime;
        private String regSource;
        private String terminalType;
        private String appStore;
        private String referralCode;
        private String invitationCode;
        private String regProtocolNo;
        private String infoProtocolNo;
        private String promotionChannel;
        private String lastLoginTime;
        private int isFirstLoan;
        private String headImg;
        private String esignId;
        private String sealData;
        private String loanTime;
        private String firstLoginTime;
        private String regTimeStr;
        private int inviteStatus;

        public String getRegTimeStr() {
            return regTimeStr;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getMerchantCode() {
            return merchantCode;
        }

        public void setMerchantCode(String merchantCode) {
            this.merchantCode = merchantCode;
        }

        public String getLoginName() {
            return loginName;
        }

        public void setLoginName(String loginName) {
            this.loginName = loginName;
        }

        public String getLoginPasswd() {
            return loginPasswd;
        }

        public void setLoginPasswd(String loginPasswd) {
            this.loginPasswd = loginPasswd;
        }

        public String getTradePasswd() {
            return tradePasswd;
        }

        public void setTradePasswd(String tradePasswd) {
            this.tradePasswd = tradePasswd;
        }

        public String getMobileNo() {
            return mobileNo;
        }

        public void setMobileNo(String mobileNo) {
            this.mobileNo = mobileNo;
        }

        public String getRealName() {
            return realName;
        }

        public void setRealName(String realName) {
            this.realName = realName;
        }

        public String getIdcardNo() {
            return idcardNo;
        }

        public void setIdcardNo(String idcardNo) {
            this.idcardNo = idcardNo;
        }

        public int getIsCertified() {
            return isCertified;
        }

        public void setIsCertified(int isCertified) {
            this.isCertified = isCertified;
        }

        public int getIsValid() {
            return isValid;
        }

        public void setIsValid(int isValid) {
            this.isValid = isValid;
        }

        public String getRegTime() {
            return regTime;
        }

        public void setRegTime(String regTime) {
            this.regTime = regTime;
        }

        public String getRegSource() {
            return regSource;
        }

        public void setRegSource(String regSource) {
            this.regSource = regSource;
        }

        public String getTerminalType() {
            return terminalType;
        }

        public void setTerminalType(String terminalType) {
            this.terminalType = terminalType;
        }

        public String getAppStore() {
            return appStore;
        }

        public void setAppStore(String appStore) {
            this.appStore = appStore;
        }

        public String getReferralCode() {
            return referralCode;
        }

        public void setReferralCode(String referralCode) {
            this.referralCode = referralCode;
        }

        public String getInvitationCode() {
            return invitationCode;
        }

        public void setInvitationCode(String invitationCode) {
            this.invitationCode = invitationCode;
        }

        public String getRegProtocolNo() {
            return regProtocolNo;
        }

        public void setRegProtocolNo(String regProtocolNo) {
            this.regProtocolNo = regProtocolNo;
        }

        public String getInfoProtocolNo() {
            return infoProtocolNo;
        }

        public void setInfoProtocolNo(String infoProtocolNo) {
            this.infoProtocolNo = infoProtocolNo;
        }

        public String getPromotionChannel() {
            return promotionChannel;
        }

        public void setPromotionChannel(String promotionChannel) {
            this.promotionChannel = promotionChannel;
        }

        public String getLastLoginTime() {
            return lastLoginTime;
        }

        public void setLastLoginTime(String lastLoginTime) {
            this.lastLoginTime = lastLoginTime;
        }

        public int getIsFirstLoan() {
            return isFirstLoan;
        }

        public void setIsFirstLoan(int isFirstLoan) {
            this.isFirstLoan = isFirstLoan;
        }

        public String getHeadImg() {
            return headImg;
        }

        public void setHeadImg(String headImg) {
            this.headImg = headImg;
        }

        public String getEsignId() {
            return esignId;
        }

        public void setEsignId(String esignId) {
            this.esignId = esignId;
        }

        public String getSealData() {
            return sealData;
        }

        public void setSealData(String sealData) {
            this.sealData = sealData;
        }

        public String getLoanTime() {
            return loanTime;
        }

        public void setLoanTime(String loanTime) {
            this.loanTime = loanTime;
        }

        public String getFirstLoginTime() {
            return firstLoginTime;
        }

        public void setFirstLoginTime(String firstLoginTime) {
            this.firstLoginTime = firstLoginTime;
        }

        public void setRegTimeStr(String regTimeStr) {
            this.regTimeStr = regTimeStr;
        }

        public int getInviteStatus() {
            return inviteStatus;
        }

        public void setInviteStatus(int inviteStatus) {
            this.inviteStatus = inviteStatus;
        }
    }
}
