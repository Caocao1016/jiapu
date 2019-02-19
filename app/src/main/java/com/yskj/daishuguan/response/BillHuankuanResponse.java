package com.yskj.daishuguan.response;

import java.util.List;

/**
 * CaoPengFei
 * 2019/2/19
 *
 * @ClassName: BillHuankuanResponse
 * @Description:
 */

public class BillHuankuanResponse {

    /**
     * totalCount : 1
     * pageSize : 20
     * totalPage : 1
     * currPage : 1
     * list : [{"repayOrderNo":"HK1517986164","loanOrderNo":"JK2501517902681506","productName":"单笔产品","repayTotal":"","createTime":"2018-02-07","status":0,"finishTime":""}]
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
         * repayOrderNo : HK1517986164
         * loanOrderNo : JK2501517902681506
         * productName : 单笔产品
         * repayTotal :
         * createTime : 2018-02-07
         * status : 0
         * finishTime :
         */

        private String repayOrderNo;
        private String loanOrderNo;
        private String productName;
        private String repayTotal;
        private String createTime;
        private int status;
        private String finishTime;

        public String getRepayOrderNo() {
            return repayOrderNo;
        }

        public void setRepayOrderNo(String repayOrderNo) {
            this.repayOrderNo = repayOrderNo;
        }

        public String getLoanOrderNo() {
            return loanOrderNo;
        }

        public void setLoanOrderNo(String loanOrderNo) {
            this.loanOrderNo = loanOrderNo;
        }

        public String getProductName() {
            return productName;
        }

        public void setProductName(String productName) {
            this.productName = productName;
        }

        public String getRepayTotal() {
            return repayTotal;
        }

        public void setRepayTotal(String repayTotal) {
            this.repayTotal = repayTotal;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getFinishTime() {
            return finishTime;
        }

        public void setFinishTime(String finishTime) {
            this.finishTime = finishTime;
        }
    }
}
