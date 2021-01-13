package com.demo.jiapu.response;

import com.demo.jiapu.bean.FamilyBean;

import java.util.List;

public class ZdppResponse {
    private int id;
    private List<FamilyBean> data;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<FamilyBean> getData() {
        return data;
    }

    public void setData(List<FamilyBean> data) {
        this.data = data;
    }
}
