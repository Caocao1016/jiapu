package com.yskj.daishuguan.bean;

/**
 * CaoPengFei
 * 2018/11/22
 *
 * @ClassName: PhoneBean
 * @Description: 通讯录
 */

public class PhoneBean {
    private String name;        //联系人姓名
    private String telPhone;    //电话号码


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTelPhone() {
        return telPhone;
    }

    public void setTelPhone(String telPhone) {
        this.telPhone = telPhone;
    }

    public PhoneBean() {
    }

    public PhoneBean(String name, String telPhone) {
        this.name = name;
        this.telPhone = telPhone;
    }
}
