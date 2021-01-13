package com.demo.jiapu.entity;

import com.demo.jiapu.base.MapParamsRequest;


public class JpsjEditRequest extends MapParamsRequest {

    public String title;
    public String jp_img;
    public long id;


    @Override
    protected void putParams() {
        params.put("title", title);
        params.put("jp_img", jp_img);
        params.put("id", id);

    }

}
