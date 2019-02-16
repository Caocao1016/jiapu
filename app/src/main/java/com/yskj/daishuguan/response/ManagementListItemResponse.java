package com.yskj.daishuguan.response;

import java.math.BigDecimal;

/**
 * CaoPengFei
 * 2019/2/15
 *
 * @ClassName: ManagementListItemResponse
 * @Description:
 */

public class ManagementListItemResponse {

    private int id;
    //商户号
    private String merchantCode;
    //用户id
    private String userId;
    //优惠券名称
    private String couponName;
    //优惠券类型 1:减息
    private int couponType;
    //减免百分比
    private int percentage;
    //开始时间
    private String startTime;
    //结束时间
    private String endTime;
    //是否使用 1：未使用   0：已使用
    private int isUse;
    //创建时间
    private String createTime;
    //使用时间
    private String updateTime;
    //备注
    private String remark;
    //金额
    private BigDecimal amount;
    //被邀请人
    private String inviteUser;
    private String date;
    private String isUseType;
    private String useType;

    private String notUsed;
    private String alreadyUsed;
    private String overdueUsed;

    private String page;
    private String limit;

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMerchantCode() {
        return merchantCode;
    }

    public void setMerchantCode(String merchantCode) {
        this.merchantCode = merchantCode;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getCouponName() {
        return couponName;
    }

    public void setCouponName(String couponName) {
        this.couponName = couponName;
    }

    public Integer getCouponType() {
        return couponType;
    }

    public void setCouponType(Integer couponType) {
        this.couponType = couponType;
    }

    public Integer getPercentage() {
        return percentage;
    }

    public void setPercentage(Integer percentage) {
        this.percentage = percentage;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public Integer getIsUse() {
        return isUse;
    }

    public void setIsUse(Integer isUse) {
        this.isUse = isUse;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }


    public String getInviteUser() {
        return inviteUser;
    }

    public void setInviteUser(String inviteUser) {
        this.inviteUser = inviteUser;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getIsUseType() {
        return isUseType;
    }

    public void setIsUseType(String isUseType) {
        this.isUseType = isUseType;
    }

    public String getUseType() {
        return useType;
    }

    public void setUseType(String useType) {
        this.useType = useType;
    }

    public String getNotUsed() {
        return notUsed;
    }

    public void setNotUsed(String notUsed) {
        this.notUsed = notUsed;
    }

    public String getAlreadyUsed() {
        return alreadyUsed;
    }

    public void setAlreadyUsed(String alreadyUsed) {
        this.alreadyUsed = alreadyUsed;
    }

    public String getOverdueUsed() {
        return overdueUsed;
    }

    public void setOverdueUsed(String overdueUsed) {
        this.overdueUsed = overdueUsed;
    }

    public void setCouponType(int couponType) {
        this.couponType = couponType;
    }

    public void setPercentage(int percentage) {
        this.percentage = percentage;
    }

    public void setIsUse(int isUse) {
        this.isUse = isUse;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public String getLimit() {
        return limit;
    }

    public void setLimit(String limit) {
        this.limit = limit;
    }
}
