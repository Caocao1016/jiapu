package com.demo.jiapu.entity;

import com.demo.jiapu.base.MapParamsRequest;

/**
 * CaoPengFei
 * 2018/12/27
 *
 * @ClassName: AuInfoRequest
 * @Description:
 */

public class JpsjAddRequest extends MapParamsRequest {

    public String title;
    public String jp_img;
    public long create_time;


    @Override
    protected void putParams() {
        params.put("title", title);
        params.put("jp_img", jp_img);
        params.put("create_time", create_time);

    }

}
