package com.demo.jiapu.response;

import com.demo.jiapu.bean.FamilyBean;

import java.util.List;

public class ZdppResponse {
    private String id;
    private List<FamilyBean> data;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<FamilyBean> getData() {
        return data;
    }

    public void setData(List<FamilyBean> data) {
        this.data = data;
    }
}
