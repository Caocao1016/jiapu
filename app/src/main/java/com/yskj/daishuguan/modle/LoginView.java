package com.yskj.daishuguan.modle;

import com.yskj.daishuguan.base.BaseResponse;
import com.yskj.daishuguan.response.CommonDataResponse;
import com.yskj.daishuguan.response.LoginResponse;

/**
 * CaoPengFei
 * 2018/11/29
 *
 * @ClassName: CommonDataView
 * @Description:
 */

public interface LoginView {

    void onSuccess(LoginResponse response);

    void onCodeSuccess(BaseResponse response);

    void onError();
    void onFailure(BaseResponse response);
}
