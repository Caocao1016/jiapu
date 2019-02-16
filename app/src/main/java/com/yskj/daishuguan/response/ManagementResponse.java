package com.yskj.daishuguan.response;

/**
 * CaoPengFei
 * 2018/11/29
 *
 * @ClassName: AppVersionResponse
 * @Description:
 */

public class ManagementResponse {

    //notUsed未使用数量 alreadyUsed已使用数量 overdueUsed过期数量
    private int notUsed;
    private int alreadyUsed;
    private int overdueUsed;

    public int getNotUsed() {
        return notUsed;
    }

    public void setNotUsed(int notUsed) {
        this.notUsed = notUsed;
    }

    public int getAlreadyUsed() {
        return alreadyUsed;
    }

    public void setAlreadyUsed(int alreadyUsed) {
        this.alreadyUsed = alreadyUsed;
    }

    public int getOverdueUsed() {
        return overdueUsed;
    }

    public void setOverdueUsed(int overdueUsed) {
        this.overdueUsed = overdueUsed;
    }
}
