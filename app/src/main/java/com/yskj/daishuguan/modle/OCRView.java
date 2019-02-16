package com.yskj.daishuguan.modle;

import com.lzy.okgo.request.BaseRequest;
import com.yskj.daishuguan.base.BaseResponse;
import com.yskj.daishuguan.response.AppVersionResponse;
import com.yskj.daishuguan.response.OCRResponse;

/**
 * CaoPengFei
 * 2018/11/29
 *
 * @ClassName: CommonDataView
 * @Description:
 */

public interface OCRView {

    void onIDCardSuccess(BaseRequest response);
    void onFanceSuccess(BaseRequest response);
    void onFailure(BaseResponse response);

    void onError();
}
