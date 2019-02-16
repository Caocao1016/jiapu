package com.yskj.daishuguan.entity.request;

import com.yskj.daishuguan.base.MapParamsRequest;

import java.io.File;

/**
 * CaoPengFei
 * 2018/12/7
 *
 * @ClassName: AuthorRequest
 * @Description:
 */

public class FileRequest extends MapParamsRequest {

    public File fileFront;
    public File fileBack;


    @Override
    protected void putParams() {
        params.put("fileFront", fileFront);
        params.put("fileBack", fileBack);

    }
}
