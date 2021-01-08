package com.demo.jiapu.entity;

import android.text.TextUtils;

import com.demo.jiapu.base.MapParamsRequest;

public class AddGrjpRequest extends MapParamsRequest {

    public String userId;
    public String typeId;
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
    public int isHave;
    public String nativePlace;
    public long create_time;

    @Override
    protected void putParams() {
        params.put("uid", userId);
        params.put("type_id", typeId);
        params.put("surname", surName);
        params.put("names", names);

        if (!TextUtils.isEmpty(seniority)) {
            params.put("seniority", seniority);
        }

        params.put("sex", sex);

        if (!TextUtils.isEmpty(birthday)) {
            params.put("birthdate", birthday);
        }
        params.put("sort", sort);
        if (!TextUtils.isEmpty(phone)) {
            params.put("phone", phone);
        }
        params.put("die_status", dieStatus);
        params.put("dietime", dieTime);
        if (!TextUtils.isEmpty(burialSite)) {
            params.put("burial_site", burialSite);
        }
        params.put("is_have", isHave);
        if (!TextUtils.isEmpty(nativePlace)) {
            params.put("native_place", nativePlace);
        }
        params.put("create_time", create_time);

    }

}
