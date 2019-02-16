package com.yskj.daishuguan.modle;

import com.yskj.daishuguan.base.BasePresenter;
import com.yskj.daishuguan.base.BaseResponse;
import com.yskj.daishuguan.response.CaptchaCodeResponse;
import com.yskj.daishuguan.response.RegisterResponse;

/**
 * CaoPengFei
 * 2018/11/29
 *
 * @ClassName: CommonDataView
 * @Description:
 */

public interface RegisterView {

    void onSuccess(RegisterResponse response);
    void onSmsSuccess(BaseResponse response);
    void onFailure(BaseResponse response);
    void onImgCodeSuccess(CaptchaCodeResponse response);
    void onSmsError( );
    void onError();
}
