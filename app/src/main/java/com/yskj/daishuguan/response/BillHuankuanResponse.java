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
         * 已放款：status=3   待还款status=0
         待还款：
         idDued=1代表逾期     DuedDay逾期天数
         idDued=0代表未逾期   paymentDay剩余还款天数
         finishTime 还款时间
         repayment/bills
         已放款：status=3 待还款status=0
         待还款：
         idDued=1代表逾期 DuedDay逾期天数
         idDued=0代表未逾期
         未逾期：
         extension ：1表示展期
         ascription=‘A’代表君正 extensionType：1成功 2失败 3待君正风控审核 4过风 控待缴费
         ascription=‘B’代表亿数 extensionType：1成功 2失败 5待缴费
         extension ：0表示没有展期 paymentDay剩余还款天数

         finishTime 还款时间
         */


        private String repayOrderNo; //订单号
        private String loanOrderNo; //借款订单
        private String productName; //产品名称
        private String repayTotal; //应还金额
        private String createTime; // 订单生成时间
        private String repayDate; // 订单应还时间
        private int status;  //0：待还款1：还款中 3：已还款
        private String finishTime; //还款时间
        private int duedDay;
        private int idDued;
        private int paymentDay;
        private int loanDate;
        private int interestRate;
        private int isExtension;
        private int extensionType;
        private String ascription;


        public int getDuedDay() {
            return duedDay;
        }

        public void setDuedDay(int duedDay) {
            this.duedDay = duedDay;
        }

        public int getPaymentDay() {
            return paymentDay;
        }

        public void setPaymentDay(int paymentDay) {
            this.paymentDay = paymentDay;
        }

        public int getExtensionType() {
            return extensionType;
        }

        public void setExtensionType(int extensionType) {
            this.extensionType = extensionType;
        }

        public String getRepayDate() {
            return repayDate;
        }

        public void setRepayDate(String repayDate) {
            this.repayDate = repayDate;
        }



        public int getIsExtension() {
            return isExtension;
        }

        public void setIsExtension(int isExtension) {
            this.isExtension = isExtension;
        }



        public String getAscription() {
            return ascription;
        }

        public void setAscription(String ascription) {
            this.ascription = ascription;
        }


        public int getIdDued() {
            return idDued;
        }

        public void setIdDued(int idDued) {
            this.idDued = idDued;
        }




        public int getLoanDate() {
            return loanDate;
        }

        public void setLoanDate(int loanDate) {
            this.loanDate = loanDate;
        }

        public int getInterestRate() {
            return interestRate;
        }

        public void setInterestRate(int interestRate) {
            this.interestRate = interestRate;
        }

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
