package com.yskj.daishuguan.modle;

import com.yskj.daishuguan.base.BaseResponse;
import com.yskj.daishuguan.response.AppVersionResponse;
import com.yskj.daishuguan.response.HomeInfoResponse;

/**
 * CaoPengFei
 * 2018/11/29
 *
 * @ClassName: CommonDataView
 * @Description:
 */

public interface AuthoriztionView {

    void onSuccess(BaseResponse response);
    void onSubmitSuccess(BaseResponse response);
    void onFailure(BaseResponse response);
    void onHomeInfoSuccess(HomeInfoResponse response);
    void onError();
}
