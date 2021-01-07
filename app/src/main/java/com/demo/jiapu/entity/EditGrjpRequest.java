package com.demo.jiapu.entity;

import com.demo.jiapu.base.MapParamsRequest;


public class EditGrjpRequest extends MapParamsRequest {

    public String userId;
    public String surName;
    public String names;
    public String seniority;
    public int sex;
    public String birthday;
    public int sort;
    public String phone;
    public int dieStatus;
    public String dieTime;
    public String burialSite;
    public String nativePlace;


    @Override
    protected void putParams() {
        params.put("uid", userId);
        params.put("surname", surName);
        params.put("names", names);
        params.put("seniority", seniority);
        params.put("sex", sex);
        params.put("birthdate", birthday);
        params.put("sort", sort);
        params.put("phone", phone);
        params.put("die_status", dieStatus);
        params.put("dietime", dieTime);
        params.put("burial_site", burialSite);
        params.put("native_place", nativePlace);

    }

}
