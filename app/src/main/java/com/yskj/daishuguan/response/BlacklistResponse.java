package com.yskj.daishuguan.response;

import java.util.List;

/**
 * CaoPengFei
 * 2018/11/29
 *
 * @ClassName: AppVersionResponse
 * @Description:
 */

public class BlacklistResponse {


    private String ownerPlace;
    private String phone;
    private String idCard;
    private String name;
    private String invalidTime;
    private String validTime;
    private int age;
    private String reportNo;
    private List<List<BlackListItemResponse>> blackList;


    public String getOwnerPlace() {
        return ownerPlace;
    }

    public void setOwnerPlace(String ownerPlace) {
        this.ownerPlace = ownerPlace;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInvalidTime() {
        return invalidTime;
    }

    public void setInvalidTime(String invalidTime) {
        this.invalidTime = invalidTime;
    }

    public String getValidTime() {
        return validTime;
    }

    public void setValidTime(String validTime) {
        this.validTime = validTime;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getReportNo() {
        return reportNo;
    }

    public void setReportNo(String reportNo) {
        this.reportNo = reportNo;
    }

    public List<List<BlackListItemResponse>> getBlackList() {
        return blackList;
    }

    public void setBlackList(List<List<BlackListItemResponse>> blackList) {
        this.blackList = blackList;
    }
}
