package com.demo.jiapu.entity;

import android.text.TextUtils;

import com.demo.jiapu.base.MapParamsRequest;


public class ReportRequest extends MapParamsRequest {

    public long jubao_id;
    public int jubao_type;
    public String imgs;
    public String content;



    @Override
    protected void putParams() {
        params.put("jubao_id", jubao_id);
        params.put("jubao_type", jubao_type);
        if (!TextUtils.isEmpty(imgs)){
            params.put("imgs", imgs);
        }
        params.put("content", content);

    }

}
