package com.yskj.daishuguan.modle;

import com.yskj.daishuguan.base.BaseResponse;
import com.yskj.daishuguan.response.HomeInfoResponse;

/**
 * CaoPengFei
 * 2018/11/29
 *
 * @ClassName: CommonDataView
 * @Description:
 */

public interface DetailView {

    void onSuccess(String response);
    void onFailure(BaseResponse response);
    void onSMsFailure(BaseResponse response);
    void onError();
}
