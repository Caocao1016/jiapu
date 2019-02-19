package com.yskj.daishuguan.modle;

import com.yskj.daishuguan.base.BaseResponse;
import com.yskj.daishuguan.response.AppVersionResponse;

/**
 * CaoPengFei
 * 2018/11/29
 *
 * @ClassName: CommonDataView
 * @Description:
 */

public interface AuthoriztionView {

    void onSuccess(BaseResponse response);
    void onFailure(BaseResponse response);

    void onError();
}
