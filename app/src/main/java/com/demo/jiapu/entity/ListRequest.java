package com.demo.jiapu.entity;

import android.text.TextUtils;

import com.demo.jiapu.base.MapParamsRequest;

public class ListRequest extends MapParamsRequest {

    public int page;
    public String title;

    @Override
    protected void putParams() {
        params.put("page", page);

        if (!TextUtils.isEmpty(title)) {
            params.put("title", title);
        }
    }
}