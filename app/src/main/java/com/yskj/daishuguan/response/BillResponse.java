package com.yskj.daishuguan.response;

import java.util.List;

/**
 * CaoPengFei
 * 2018/11/29
 *
 * @ClassName: AppVersionResponse
 * @Description:
 */

public class BillResponse {

    /**
     * totalCount : 1
     * pageSize : 200
     * totalPage : 1
     * currPage : 1
     * list : [{"creditNo":"f2ec4f01340b5d81c1fa0f04e5c24193","productName":"ONEPLUS A5000","status":1,"statusString":"审核通过","auditCreditLimit":10,"createTime":"2018-04-17"}]
     */

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
        /**
         * creditNo : f2ec4f01340b5d81c1fa0f04e5c24193
         * productName : ONEPLUS A5000
         * status : 1
         * statusString : 审核通过
         * auditCreditLimit : 10
         * createTime : 2018-04-17
         */

        private String creditNo;
        private String productName;
        private int status;
        private String statusString;
        private int auditCreditLimit;
        private String createTime;
        private List<Bean> recoveryList;
        private String pictureUrl;//图片地址
        private String merchantRealname;//商户名称
        private String h5Url;//h5地址

        public static class Bean {
            private String pictureUrl;//图片地址
            private String merchantRealname;//商户名称
            private String h5Url;//h5地址

            public String getPictureUrl() {
                return pictureUrl;
            }

            public void setPictureUrl(String pictureUrl) {
                this.pictureUrl = pictureUrl;
            }

            public String getMerchantRealname() {
                return merchantRealname;
            }

            public void setMerchantRealname(String merchantRealname) {
                this.merchantRealname = merchantRealname;
            }

            public String getH5Url() {
                return h5Url;
            }

            public void setH5Url(String h5Url) {
                this.h5Url = h5Url;
            }
        }


        public String getPictureUrl() {
            return pictureUrl;
        }

        public void setPictureUrl(String pictureUrl) {
            this.pictureUrl = pictureUrl;
        }

        public String getMerchantRealname() {
            return merchantRealname;
        }

        public void setMerchantRealname(String merchantRealname) {
            this.merchantRealname = merchantRealname;
        }

        public String getH5Url() {
            return h5Url;
        }

        public void setH5Url(String h5Url) {
            this.h5Url = h5Url;
        }

        public List<Bean> getRecoveryList() {
            return recoveryList;
        }

        public void setRecoveryList(List<Bean> recoveryList) {
            this.recoveryList = recoveryList;
        }

        public String getCreditNo() {
            return creditNo;
        }

        public void setCreditNo(String creditNo) {
            this.creditNo = creditNo;
        }

        public String getProductName() {
            return productName;
        }

        public void setProductName(String productName) {
            this.productName = productName;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getStatusString() {
            return statusString;
        }

        public void setStatusString(String statusString) {
            this.statusString = statusString;
        }

        public int getAuditCreditLimit() {
            return auditCreditLimit;
        }

        public void setAuditCreditLimit(int auditCreditLimit) {
            this.auditCreditLimit = auditCreditLimit;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }
    }

}
