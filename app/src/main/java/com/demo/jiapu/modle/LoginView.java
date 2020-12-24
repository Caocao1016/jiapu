package com.demo.jiapu.modle;

import com.demo.jiapu.base.BaseResponse;
import com.demo.jiapu.response.LoginResponse;

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
